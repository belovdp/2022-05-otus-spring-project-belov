import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import Toolbar from "@/ts/components/common/Toolbar";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {OrderService, OrderShort} from "@/ts/services/OrderService";
import {rubFormatter} from "@/ts/utils/formatters";
import OrderDialog from "@/ts/components/OrderDialog";

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
        <el-table-column prop="username" label="Получатель" sortable="custom">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="$router.push('/users/' + scope.row.userId)">{{scope.row.username}}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="created" label="Время создания" sortable="custom"></el-table-column>
        <el-table-column prop="email" label="email" sortable="custom"></el-table-column>
        <el-table-column prop="phone" label="Телефон" sortable="custom"></el-table-column>
        <el-table-column prop="address" label="Адрес" sortable="custom"></el-table-column>
        <el-table-column prop="totalPrice" :formatter="rubFormatter" label="Сумма" sortable="custom"></el-table-column>
        <el-table-column
            width="320">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="onMoreLinkClick(scope.row.id)">Подробнее</el-button>
          </template>
        </el-table-column>
      </pageable-table>

      <order-dialog :order-id="showOrderId" @close="onOrderDialogClose"></order-dialog>
      </div>
    `,
    components: {
        Toolbar,
        PageableTable,
        OrderDialog
    }
})
export default class OrderListView extends Vue {

    $refs: {
        table: PageableTable<OrderShort>
    };

    /** Сервис по работе с заказами */
    @Inject private readonly orderService: OrderService;
    /** Форматтер цен */
    private readonly rubFormatter = rubFormatter;
    /** Отображаемый в диалоге заказ */
    private showOrderId: number | null = null;

    /**
     * Загрузка заказов
     * @param pageable пагинация
     */
    private async loadOrders(pageable: SpringPageable): Promise<TableData<OrderShort>> {
        return await this.orderService.getOrders(pageable);
    }

    private onMoreLinkClick(orderId: number) {
        this.showOrderId = orderId;
    }

    private onOrderDialogClose() {
        this.showOrderId = null;
    }
}
