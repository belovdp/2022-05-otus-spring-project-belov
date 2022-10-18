import {Component, Prop, Vue, Watch} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {Order, OrderService} from "@/ts/services/OrderService";
import {rubFormatter} from "@/ts/utils/formatters";

@Component({
    template: `
      <el-dialog v-if="orderId && order" :title="'Заказ #' + orderId" :visible="true" @close="$emit('close')">
      <el-descriptions class="margin-top" :column="2" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            Получатель
          </template>
          <el-button type="text" size="small" @click="$router.push('/users/' + order.userId)">{{order.username}}</el-button>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            Телефон
          </template>
          {{order.phone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-eleme"></i>
            Почта
          </template>
          <a :href="'mailto:' + order.email" target="_blank">{{order.email}}</a>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            Заметка
          </template>
          {{order.note}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            Адрес
          </template>
          {{order.address}}
        </el-descriptions-item>
      </el-descriptions>
      
      <el-table :data="order.items">
        <el-table-column prop="productId" label="ID товара" width="100" sortable></el-table-column>
        <el-table-column prop="title" label="Название" sortable></el-table-column>
        <el-table-column prop="price" align="right" label="Цена" :formatter="rubFormatter" sortable></el-table-column>
        <el-table-column prop="count" align="right" label="Количество" sortable></el-table-column>
      </el-table>
      </el-dialog>
    `,
})
export default class OrderDialog extends Vue {

    /** Сервис по работе с заказами */
    @Inject private readonly orderService: OrderService;
    /** Форматтер цен */
    private readonly rubFormatter = rubFormatter;
    /** Идентификатор заказа */
    @Prop()
    private orderId;
    /** Заказ */
    private order: Order | null = null;

    @Watch("orderId", {immediate: true})
    async onOrderIdChanged(newVal: number) {
        this.order = newVal ? await this.orderService.getOrder(newVal) : null;
    }
}
