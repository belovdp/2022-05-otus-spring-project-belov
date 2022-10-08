import "@/style/img/logo.png";
import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryItem, CategoryService, CategoryTreeItem} from "@/ts/services/CategoryService";
import {Route} from "vue-router";
import {ElTree, TreeData} from "element-ui/types/tree";
import {Notification} from "element-ui";
import store from "@/ts/config/store";
import Toolbar from "@/ts/components/common/Toolbar";

@Component({
    template: `
      <div class="categoryView">
      <toolbar :title="category.title">
        <el-button-group slot="buttons">
          <el-button v-if="!category.deleted"
                     type="primary"
                     size="mini"
                     icon="el-icon-check"
                     round
                     @click="onSave">Сохранить</el-button>
          <el-button v-if="!isNewCategory && category.deleted"
                     type="warning"
                     size="mini"
                     icon="el-icon-upload2"
                     round
                     @click="onRestore">Востановить</el-button>
          <el-button v-if="!isNewCategory && !category.deleted" type="danger" size="mini" icon="el-icon-delete-solid" round @click="onDelete"></el-button>
        </el-button-group>
      </toolbar>
      <el-tabs :value="activeTab">
        <el-tab-pane v-if="!isNewCategory" label="Продукты" name="products">
          // TODO
        </el-tab-pane>
        <el-tab-pane label="Редактор" name="editor">
          <el-form label-position="top" label-width="100px" :model="category">
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
                       @check="onCheckChange"
                       :props="treeProps"></el-tree>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      </div>
    `,
    components: {
        Toolbar
    }
})
export default class CategoryView extends Vue {

    $refs: {
        tree: ElTree<unknown, TreeData>
    };
    $route: Route & {
        params: {
            id: number
        }
    };

    /** Сервис по работе с категориями */
    @Inject private readonly categoryService: CategoryService;
    /** Категория */
    private category: CategoryItem = {
        id: null,
        title: "",
        deleted: false,
        hideMenu: false,
        published: false,
        sortIndex: 0,
        parent: null
    };
    /** Настройка дерева категорий */
    private treeProps = {
        children: "childs",
        label: "title"
    };

    async created() {
        if (!this.isNewCategory) {
            await this.loadCategory();
        }
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
     * Обработчик переноса в корзину
     */
    private async onDelete() {
        await this.categoryService.moveToTrash([this.$route.params.id]);
        await this.refreshOnChange();
        Notification.warning("Категория перемещена в корзину");
    }

    /**
     * Обработчик востановления
     */
    private async onRestore() {
        this.category = await this.categoryService.saveCategory({
            ...this.category,
            deleted: false
        });
        await this.refreshOnChange();
        Notification.warning("Категория востановлена");
    }

    /**
     * Загрузка категории
     */
    private async loadCategory() {
        this.category = await this.categoryService.getCategory(this.$route.params.id);
    }

    /**
     * Обновление категории и меню категорий
     */
    private async refreshOnChange() {
        await this.loadCategory();
        store.state.categories = await this.categoryService.getCategoriesTree();
    }

    /**
     * Возвращает активный таб
     */
    private get activeTab(): string {
        if (this.isNewCategory) {
            return "editor";
        }
        return "products";
    }

    /**
     * Обработчик сохранения
     */
    private async onSave() {
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

    /** Признак новой категории */
    private get isNewCategory(): boolean {
        return !this.$route.params.id;
    }

    /** Данные дерева категорий */
    private get catTree(): CategoryTreeItem[] {
        return store.state.categories;
    }
}
