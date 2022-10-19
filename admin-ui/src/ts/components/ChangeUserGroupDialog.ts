import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {Group, UserService} from "@/ts/services/UserService";

/**
 * Диалог измененеия группы пользователя
 */
@Component({
    template: `
      <el-dialog title="Смена группы" :visible.sync="visible">
          <el-form>
            <el-form-item label="Группа">
                <el-select v-model="group">
                  <el-option v-for="item in groups"
                             :key="item.id"
                             :label="item.name"
                             :value="item.id"></el-option>
                </el-select>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">Отмена</el-button>
            <el-button type="primary" @click="onSave">Изменить</el-button>
          </span>
      </el-dialog>
    `,
})
export default class ChangeUserGroupDialog extends Vue {

    /** Сервис по работе с пользователями */
    @Inject private readonly userService: UserService;
    /** Идентификатор пользователя */
    private userId: string | null = null;
    /** Группа */
    private group: string | null = null;
    /** Признак отображения диалога */
    private visible = false;
    /** Группы пользователей */
    private groups: Group[] = [];

    async created() {
        this.groups = await this.userService.getGroups();
    }

    /**
     * Отображает диалог смены группы пользователя
     * @param userId           пользователь
     * @param currentGroupName текущая группа
     */
    show(userId: string, currentGroupName: string) {
        this.userId = userId;
        this.group = this.groups.find(item => item.name === currentGroupName)?.id || null;
        this.visible = true;
    }

    /**
     * Обработчик изменения группы пользователя
     */
    private async onSave() {
        await this.userService.changeGroup(this.userId!, this.group!);
        this.visible = false;
        this.$emit("saved");
    }
}
