import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryService, CategoryTreeItem} from "@/ts/services/CategoryService";

@Component({
    template: `
      <el-col :span="4">
      <el-menu default-active="2" class="mainMenu">
        <el-button icon="el-icon-plus" class="addCat" type="primary" circle size="mini" @click="onAddCategory"></el-button>
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-s-grid"></i>
            <span slot="title">Категории</span>
          </template>
          <el-tree :data="catTree"
                   @node-click="onMenuClick"
                   :expand-on-click-node="false"
                   :props="treeProps"></el-tree>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-s-goods"></i>
          <span slot="title">Заказы</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-user-solid"></i>
          <span slot="title">Пользователи</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-delete-solid"></i>
          <span slot="title">Корзина товаров</span>
        </el-menu-item>
        <el-menu-item index="5">
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
    /** Данные дерева категорий */
    private catTree: CategoryTreeItem[] = [];
    /** Настройка дерева категорий */
    private treeProps = {
        children: "childs",
        label: "title"
    };

    async created() {
        this.catTree = await this.categoryService.getCategoriesTree();
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
}
