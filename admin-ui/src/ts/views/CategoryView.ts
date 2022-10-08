import "@/style/img/logo.png";
import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryItem, CategoryService, CategoryTreeItem} from "@/ts/services/CategoryService";
import {Route} from "vue-router";
import {ElTree, TreeData} from "element-ui/types/tree";
import {Notification} from "element-ui";

@Component({
    template: `
      <div class="categoryView">
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
              <el-checkbox label="Удалён" v-model="category.deleted" name="type"></el-checkbox>
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
            <el-form-item>
              <el-button type="primary" @click="onSave">Сохранить</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      </div>
    `
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
    /** Данные дерева категорий */
    private catTree: CategoryTreeItem[] = [];
    /** Настройка дерева категорий */
    private treeProps = {
        children: "childs",
        label: "title"
    };

    async created() {
        this.catTree = await this.categoryService.getCategoriesTree();
        if (!this.isNewCategory) {
            this.category = await this.categoryService.getCategory(this.$route.params.id);
        }
    }

    private onCheckChange(checkedItem: CategoryTreeItem) {
        this.$refs.tree.setCheckedKeys([]);
        this.$refs.tree.setCheckedKeys([checkedItem.id]);
    }

    private get activeTab(): string {
        if (this.isNewCategory) {
            return "editor";
        }
        return "products";
    }

    private async onSave() {
        const [parent] = this.$refs.tree.getCheckedKeys() as number[];
        const savedCat = await this.categoryService.saveCategory({
            ...this.category,
            parent
        });
        Notification.success("Категория сохранена");
        await this.$router.push({
            name: "CategoryView",
            params: {
                id: String(savedCat.id)
            }
        });
    }

    private get isNewCategory(): boolean {
        return !this.$route.params.id;
    }
}
