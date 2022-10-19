import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {User, UserService} from "@/ts/services/UserService";

@Component({
    template: `
      <el-dialog :title="title" :visible.sync="visible">
      <el-descriptions v-if="user" class="margin-top" :column="2" border>
        <el-descriptions-item>
          <template slot="label" span="2">
            ID
          </template>
          {{user.id}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            Имя пользователя
          </template>
          {{user.username}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            Имя
          </template>
          {{user.firstName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            Фамилия
          </template>
          {{user.lastName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            Активен
          </template>
          {{user.enabled ? "Да" : "Нет"}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            Почта
          </template>
          {{user.email}}
        </el-descriptions-item>
      </el-descriptions>
      </el-dialog>
    `
})
export default class UserDialog extends Vue {

    /** Сервис по работе с пользователями */
    @Inject private readonly userService: UserService;
    private user: User | null = null;
    private visible = false;

    async show(userId: string) {
        this.user = await this.userService.getUser(userId);
        this.visible = true;
    }

    private get title() {
        return `Пользователь #${this.user?.id}`;
    }
}
