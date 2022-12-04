import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import Toolbar from "@/ts/components/common/Toolbar";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {User, UserService} from "@/ts/services/UserService";
import ChangeUserGroupDialog from "@/ts/components/ChangeUserGroupDialog";

@Component({
    template: `
      <div class="orderListView">
      <toolbar title="Пользователи">
      </toolbar>

      <pageable-table ref="table"
                      :loader="loadUsers"
                      size="mini" border
                      empty-text="Данные отсутствуют или не загружены">
        <el-table-column prop="username" label="Имя пользователя"></el-table-column>
        <el-table-column prop="lastName" label="Фамилия"></el-table-column>
        <el-table-column prop="firstName" label="Имя"></el-table-column>
        <el-table-column prop="enabled" label="Активен">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="onChangeEnableStatus(scope.row.id, !scope.row.enabled)">
              {{scope.row.enabled ? "Да" : "Нет"}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="Почта" width="200"></el-table-column>
        <el-table-column prop="groups" label="Группы"></el-table-column>
        <el-table-column
            width="220">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="onChangeGroupLinkClick(scope.row.id, scope.row.groups[0])">Изменить группу</el-button>
          </template>
        </el-table-column>
      </pageable-table>
      <change-user-group-dialog ref="changeGroupDialog" @saved="onDataChanged"/>
      </div>
    `,
    components: {
        Toolbar,
        PageableTable,
        ChangeUserGroupDialog
    }
})
export default class UserListView extends Vue {

    $refs: {
        table: PageableTable<User>
        changeGroupDialog: ChangeUserGroupDialog
    };

    /** Сервис по работе с пользователями */
    @Inject private readonly userService: UserService;

    /**
     * Загрузка заказов
     * @param pageable пагинация
     */
    private async loadUsers(pageable: SpringPageable): Promise<TableData<User>> {
        return await this.userService.getUsers(pageable);
    }

    /**
     * Обработчик нажатия на кнопку изменения группы
     * @param userId идентификатор пользователя
     * @param group  группа текущая
     */
    private onChangeGroupLinkClick(userId: string, group: string) {
        this.$refs.changeGroupDialog.show(userId, group);
    }

    /**
     * Обработчик активации/деактивации пользователя
     * @param userId  идентификатор пользователя
     * @param enabled признак активации
     */
    private async onChangeEnableStatus(userId: string, enabled: boolean) {
        await this.userService.onChangeEnableStatus(userId, enabled);
        await this.$refs.table.refresh();
    }

    /**
     * Обработчик изменения данных
     */
    private async onDataChanged() {
        await this.$refs.table.refresh();
    }
}
