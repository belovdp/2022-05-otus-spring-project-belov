import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import Toolbar from "@/ts/components/common/Toolbar";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {OrderService, OrderShort} from "@/ts/services/OrderService";

@Component({
    template: `
      <div class="orderListView">
      <toolbar title="Заказы">
      </toolbar>

      <pageable-table ref="table"
                      :loader="loadOrders"
                      size="mini" border
                      :default-sort="{prop: 'id', order: 'descending'}"
                      empty-text="Данные отсутствуют или не загружены">
        <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="created" label="Время создания" sortable="custom"></el-table-column>
        <el-table-column prop="email" label="email" sortable="custom"></el-table-column>
        <el-table-column prop="phone" label="Телефон" sortable="custom"></el-table-column>
        <el-table-column prop="address" label="Адрес" sortable="custom"></el-table-column>
        <el-table-column prop="username" label="Пользователь" sortable="custom">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="onUserClick(scope.row.userId)">{{scope.row.username}}</el-button>
          </template>
        </el-table-column>
      </pageable-table>
      </div>
    `,
    components: {
        Toolbar,
        PageableTable
    }
})
export default class OrderListView extends Vue {

    $refs: {
        table: PageableTable<OrderShort>
    };

    /** Сервис по работе с заказами */
    @Inject private readonly orderService: OrderService;

    /**
     * Загрузка заказов
     * @param pageable пагинация
     */
    private async loadOrders(pageable: SpringPageable): Promise<TableData<OrderShort>> {
        return await this.orderService.getOrders(pageable);
    }

    private onUserClick(userId: string) {
        // TODO
        throw new Error("Ещё нет страницы " + userId);
    }
}
