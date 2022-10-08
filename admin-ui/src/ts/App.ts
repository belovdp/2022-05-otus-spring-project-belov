import {Component, Vue} from "vue-property-decorator";
import Navigation from "@/ts/components/common/Navigation";

/**
 * Основной компонент
 */
@Component({
    template: `
      <div id="app">
      <el-row v-if="!isFullScreen">
        <navigation></navigation>
        <el-col :span="20" class="mainContent">
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

    /** Признак того что страницу отобразить во всю ширину */
    private get isFullScreen() {
        return !!this.$route.meta?.fullScreen;
    }
}
