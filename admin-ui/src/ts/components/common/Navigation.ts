import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryService, CategoryTreeItem} from "@/ts/services/CategoryService";
import store from "@/ts/config/store";

@Component({
    template: `
      <el-col :span="4">
      <el-menu default-active="2" class="mainMenu" router>
        <el-button icon="el-icon-plus"
                   class="addCat"
                   type="primary"
                   circle
                   size="mini"
                   :disabled="!$store.getters.hasEditRights"
                   @click="onAddCategory"></el-button>
        <el-submenu index="/category">
          <template slot="title">
            <i class="el-icon-s-grid"></i>
            <span slot="title">Категории</span>
          </template>
          <el-tree :data="catTree"
                   @node-click="onMenuClick"
                   :expand-on-click-node="false"
                   :props="treeProps">
            <span class="custom-tree-node" slot-scope=" { node, data }">
                <span :class="getCategoryClass(data)">{{data.title}} ({{data.id}})</span>
            </span>
          </el-tree>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-s-goods"></i>
          <span slot="title">Заказы</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-user-solid"></i>
          <span slot="title">Пользователи</span>
        </el-menu-item>
        <el-menu-item index="/product/trash">
          <i class="el-icon-delete-solid"></i>
          <span slot="title">Корзина товаров</span>
        </el-menu-item>
        <el-menu-item index="/category/trash">
          <i class="el-icon-delete-solid"></i>
          <span slot="title">Корзина категорий</span>
        </el-menu-item>
      </el-menu>
      </el-col>
    `
})
export default class Navigation extends Vue {

    /** Сервис по работе с книгами */
    @Inject private readonly categoryService: CategoryService;
    /** Настройка дерева категорий */
    private treeProps = {
        children: "childs",
        label: "title"
    };

    async created() {
        store.state.categories = await this.categoryService.getCategoriesTree();
    }

    private onMenuClick(category: CategoryTreeItem) {
        this.$router.push({
            name: "CategoryView", params: {
                id: String(category.id)
            }
        });
    }

    private onAddCategory() {
        this.$router.push({
            name: "CategoryView"
        });
    }

    private getCategoryClass(category: CategoryTreeItem) {
        const classes: string[] = [];
        if (category.deleted) {
            classes.push("deleted");
        }
        if (category.hideMenu) {
            classes.push("hiddenMenu");
        }
        if (!category.published) {
            classes.push("notPublished");
        }
        return classes.join(" ");
    }

    /** Данные дерева категорий */
    private get catTree(): CategoryTreeItem[] {
        return store.state.categories;
    }
}
