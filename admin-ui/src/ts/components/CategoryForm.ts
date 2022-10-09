import "@/style/img/logo.png";
import {Component, Prop, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryItem, CategoryService, CategoryTreeItem} from "@/ts/services/CategoryService";
import {ElTree, TreeData} from "element-ui/types/tree";
import {Notification} from "element-ui";
import store from "@/ts/config/store";
import Toolbar from "@/ts/components/common/Toolbar";

@Component({
    template: `
          <el-form v-if="category" label-position="top" label-width="100px" :model="category">
            <el-form-item label="Заголовок">
              <el-input v-model="category.title"></el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox label="Опубликован" v-model="category.published" name="type"></el-checkbox>
              <el-checkbox label="Скрыть из меню" v-model="category.hideMenu" name="type"></el-checkbox>
            </el-form-item>
            <el-form-item label="Порядок сортировки">
              <el-input-number v-model="category.sortIndex"></el-input-number>
            </el-form-item>
            <el-form-item label="Родитель">
              <el-tree ref="tree"
                       :data="catTree"
                       show-checkbox
                       node-key="id"
                       check-strictly
                       :default-checked-keys="[category.parent]"
                       @check="onCheckChange"
                       :props="treeProps"></el-tree>
            </el-form-item>
          </el-form>
    `,
    components: {
        Toolbar
    }
})
export default class CategoryForm extends Vue {

    $refs: {
        tree: ElTree<unknown, TreeData>
    };

    /** Сервис по работе с категориями */
    @Inject private readonly categoryService: CategoryService;
    @Prop({required: true})
    private category: CategoryItem;
    /** Настройка дерева категорий */
    private treeProps = {
        children: "childs",
        label: "title"
    };

    /**
     * Обработчик переноса в корзину
     */
    async delete() {
        if (!this.category.id) {
            return;
        }
        await this.categoryService.moveToTrash([this.category.id]);
        await this.refreshOnChange();
        Notification.warning("Категория перемещена в корзину");
    }

    /**
     * Обработчик востановления
     */
    async restore() {
        this.category = await this.categoryService.saveCategory({
            ...this.category,
            deleted: false
        });
        await this.refreshOnChange();
        Notification.warning("Категория востановлена");
    }

    /**
     * Обработчик сохранения
     */
    async save() {
        const [parent] = this.$refs.tree.getCheckedKeys() as number[];
        const savedCat = await this.categoryService.saveCategory({
            ...this.category,
            parent
        });
        await this.refreshOnChange();
        Notification.success("Категория сохранена");
        await this.$router.push({
            name: "CategoryView",
            params: {
                id: String(savedCat.id)
            }
        });
    }

    /**
     * Обработчик изменения чекбокса
     * @param checkedItem выбранный чекбокс
     */
    private onCheckChange(checkedItem: CategoryTreeItem) {
        this.$refs.tree.setCheckedKeys([]);
        this.$refs.tree.setCheckedKeys([checkedItem.id]);
    }

    /**
     * Обновление категории и меню категорий
     */
    private async refreshOnChange() {
        store.state.categories = await this.categoryService.getCategoriesTree();
        if (this.category.id) {
            this.$emit("changed", await this.categoryService.getCategory(this.category.id));
        }
    }

    /** Данные дерева категорий */
    private get catTree(): CategoryTreeItem[] {
        const categories = JSON.parse(JSON.stringify(store.state.categories));
        if (this.category.id) {
            const nodeToDisable = this.findNodeById(categories, this.category.id);
            if (nodeToDisable) {
                this.disableNode([nodeToDisable]);
            }
        }
        return categories;
    }

    private findNodeById(categories: CategoryTreeItem[], id: number): CategoryTreeItem | null {
        for (const category of categories) {
            if (String(category.id) === String(id)) {
                return category;
            }
            if (category.childs) {
                const resultFromChilds = this.findNodeById(category.childs, id);
                if (resultFromChilds) {
                    return resultFromChilds;
                }
            }
        }
        return null;
    }

    private disableNode(categories: (CategoryTreeItem & {disabled?: boolean})[]) {
        categories.forEach(category => {
            category.disabled = true;
            if (category.childs) {
                this.disableNode(category.childs);
            }
        });
    }
}
