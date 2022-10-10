import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {Route} from "vue-router";
import Toolbar from "@/ts/components/common/Toolbar";
import {ProductItemFull, ProductService} from "@/ts/services/ProductService";
import {Notification} from "element-ui";
import {ElTree, TreeData} from "element-ui/types/tree";
import {CategoryTreeItem} from "@/ts/services/CategoryService";
import store from "@/ts/config/store";
import {TreeUtils} from "@/ts/utils/TreeUtils";

@Component({
    template: `
      <div class="categoryView">
      <toolbar :title="product.title">
        <el-button-group slot="buttons">
          <el-button v-if="!product.deleted"
                     type="primary"
                     size="mini"
                     icon="el-icon-check"
                     round
                     @click="onSave">Сохранить
          </el-button>
          <el-button v-if="!isNewProduct && product.deleted"
                     type="warning"
                     size="mini"
                     icon="el-icon-upload2"
                     round
                     @click="onRestore">Востановить
          </el-button>
          <el-button v-if="!isNewProduct && !product.deleted"
                     type="danger"
                     size="mini"
                     icon="el-icon-delete-solid"
                     round
                     @click="onDelete"></el-button>
        </el-button-group>
      </toolbar>
      <el-form v-if="product" label-position="top" label-width="100px" :model="product">
        <el-form-item label="Заголовок">
          <el-input v-model="product.title"></el-input>
        </el-form-item>
        <el-form-item label="Описание">
          <el-input type="textarea"
              :autosize="{ minRows: 8, maxRows: 16 }"
              v-model="product.description">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox label="Опубликован" v-model="product.published" name="type"></el-checkbox>
        </el-form-item>
        <el-form-item label="Порядок сортировки">
          <el-input-number v-model="product.sortIndex"></el-input-number>
        </el-form-item>
        <el-form-item label="Категории">
          <el-tree ref="tree"
                   :data="catTree"
                   show-checkbox
                   node-key="id"
                   :render-after-expand="false"
                   :default-checked-keys="selectedLeafs"
                   :default-expanded-keys="product.categories"
                   :props="treeProps"></el-tree>
        </el-form-item>
      </el-form>
      </div>
    `,
    components: {
        Toolbar
    }
})
export default class ProductView extends Vue {

    $refs: {
        /** Дерево выбора категорий */
        tree: ElTree<unknown, TreeData>
    };
    $route: Route & {
        params: {
            id: number
        }
    };

    /** Сервис по работе с продуктами */
    @Inject private readonly productService: ProductService;
    /** Категория */
    private product: ProductItemFull = {
        id: null,
        title: "",
        deleted: false,
        published: false,
        sortIndex: 0,
        description: "",
        categories: []
    };
    /** Настройка дерева категорий */
    private treeProps = {
        children: "childs",
        label: "title"
    };

    async mounted() {
        if (!this.isNewProduct) {
            await this.loadProduct();
        }
    }

    /**
     * Обработчик переноса в корзину
     */
    private async onDelete() {
        if (this.product.id) {
            await this.productService.moveToTrash([this.product.id]);
            Notification.success("Продукт перемещен в корзину");
        }
    }

    /**
     * Обработчик востановления
     */
    private async onRestore() {
        if (this.product.id) {
            await this.productService.trashRestore([this.product.id]);
            Notification.success("Продукт востановлен из корзины");
        }
    }

    /**
     * Обработчик сохранения
     */
    private async onSave() {
        const categories = this.$refs.tree.getCheckedNodes(false, true).map(item => item.id);
        const savedProduct = await this.productService.saveProduct({...this.product, categories});
        await this.loadProduct();
        Notification.success("Продукт сохранен");
        await this.$router.push({
            name: "ProductView",
            params: { id: String(savedProduct.id) }
        });
    }

    /**
     * Загружает продукт
     */
    private async loadProduct() {
        this.product = await this.productService.getProduct(this.$route.params.id);
        this.$refs.tree.setCheckedKeys(this.selectedLeafs);
    }

    /** Конечные выбранные категории */
    private get selectedLeafs() {
        return this.product.categories.filter(key => !TreeUtils.findNodeById<CategoryTreeItem>(this.catTree, key)?.childs);
    }

    /** Признак новой категории */
    private get isNewProduct(): boolean {
        return !this.$route.params.id;
    }

    /** Данные дерева категорий */
    private get catTree(): CategoryTreeItem[] {
        return store.state.categories;
    }
}
