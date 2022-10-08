import {Component, Vue} from "vue-property-decorator";
import store from "@/ts/config/store";
import Navigation from "@/ts/components/Navigation";

/**
 * Основной компонент
 */
@Component({
    template: `
      <div id="app">
      <el-row :gutter="10" v-if="!isFullScreen">
        <navigation></navigation>
        <el-col :span="20">
          <el-row type="flex" justify="end">
            <el-button type="danger" round @click="onExit">Выход</el-button>
          </el-row>
          <router-view :key="$route.path"/>
        </el-col>
      </el-row>
      <router-view v-else :key="$route.path"/>
      </div>
    `,
    components: {
        Navigation
    }
})
export default class App extends Vue {

    /**
     * Обработчик кнопки выхода
     */
    private async onExit() {
        await store.dispatch("AUTH_LOGOUT");
    }

    /** Признак того что страницу отобразить во всю ширину */
    private get isFullScreen() {
        return !!this.$route.meta?.fullScreen;
    }
}
