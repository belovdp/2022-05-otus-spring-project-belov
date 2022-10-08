import {Component, Vue} from "vue-property-decorator";
import axios from "axios";

@Component({
    template: `
      <el-col :span="4">
        <el-menu default-active="2" class="el-menu-vertical-demo">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-grid"></i>
              <span slot="title">Категории</span>
            </template>
            <el-tree :data="data" @node-click="onMenuClick" :expand-on-click-node="false"></el-tree>
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

    private data = [];

    async created() {
        // TODO доделать. Это только проверка взаимодействия
        this.data = (await axios.get("/product-service/categories/tree")).data;
    }

    private onMenuClick() {
        // TODO переход на страницу категории
    }
}