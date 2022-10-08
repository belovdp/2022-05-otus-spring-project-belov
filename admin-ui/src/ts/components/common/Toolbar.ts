import {Component, Prop, Vue} from "vue-property-decorator";
import store from "@/ts/config/store";

@Component({
    template: `
      <el-row type="flex" justify="space-between" class="toolbar">
        <el-col :span="16">
          <h3 v-if="title">{{title}}</h3>
        </el-col>
        <el-col :span="8" class="buttons">
          <slot name="buttons"></slot>
          <el-button-group style="margin-left: 15px">
            <el-button type="danger" size="mini" round @click="onExit">Выход</el-button>
          </el-button-group>
        </el-col>
      </el-row>
    `
})
export default class Toolbar extends Vue {
    @Prop()
    private title: string;

    /**
     * Обработчик кнопки выхода
     */
    private async onExit() {
        await store.dispatch("AUTH_LOGOUT");
    }
}
