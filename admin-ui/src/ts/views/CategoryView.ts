import "@/style/img/logo.png";
import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryItem, CategoryService} from "@/ts/services/CategoryService";
import {Route} from "vue-router";
import Toolbar from "@/ts/components/common/Toolbar";
import CategoryForm from "@/ts/components/CategoryForm";
import ProductList from "@/ts/components/ProductList";

@Component({
    template: `
      <div class="categoryView">
      <toolbar :title="category.title">
        <el-button-group v-if="activeTab === 'editor'" slot="buttons">
          <el-button v-if="!category.deleted"
                     type="primary"
                     size="mini"
                     icon="el-icon-check"
                     round
                     @click="onSave">Сохранить
          </el-button>
          <el-button v-if="!isNewCategory && category.deleted"
                     type="warning"
                     size="mini"
                     icon="el-icon-upload2"
                     round
                     @click="onRestore">Востановить
          </el-button>
          <el-button v-if="!isNewCategory && !category.deleted" type="danger" size="mini" icon="el-icon-delete-solid" round @click="onDelete"></el-button>
        </el-button-group>
      </toolbar>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="Редактор" name="editor">
          <category-form ref="form" :category="category" @changed="onChanged"/>
        </el-tab-pane>
        <el-tab-pane v-if="category.id" label="Продукты" name="products">
          <product-list :category-id="category.id"/>
        </el-tab-pane>
      </el-tabs>
      </div>
    `,
    components: {
        Toolbar,
        CategoryForm,
        ProductList
    }
})
export default class CategoryView extends Vue {

    $refs: {
        form: CategoryForm
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
    private activeTab = "editor";

    async created() {
        if (!this.isNewCategory) {
            this.category = await this.categoryService.getCategory(this.$route.params.id);
        }
    }

    /**
     * Обработчик переноса в корзину
     */
    private async onDelete() {
        await this.$refs.form.delete();
    }

    /**
     * Обработчик востановления
     */
    private async onRestore() {
        await this.$refs.form.restore();
    }

    /**
     * Обработчик сохранения
     */
    private async onSave() {
        await this.$refs.form.save();
    }

    private onChanged(category: CategoryItem) {
        this.category = category;
    }

    // /**
    //  * Возвращает активный таб
    //  */
    // private get activeTab(): string {
    //     if (this.isNewCategory) {
    //         return "editor";
    //     }
    //     return "products";
    // }

    /** Признак новой категории */
    private get isNewCategory(): boolean {
        return !this.$route.params.id;
    }
}
