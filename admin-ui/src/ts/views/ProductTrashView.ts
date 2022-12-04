import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import Toolbar from "@/ts/components/common/Toolbar";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {booleanFormatter, rubFormatter} from "@/ts/utils/formatters";
import {Notification} from "element-ui";
import {ProductItem, ProductService} from "@/ts/services/ProductService";

@Component({
    template: `
      <div class="categoryView">
      <toolbar title="Корзина продуктов">
        <el-button-group slot="buttons">
          <el-button :disabled="!selected.length || !$store.getters.hasEditRights"
                     type="warning"
                     size="mini"
                     icon="el-icon-upload2"
                     round
                     @click="onRestore">Востановить</el-button>
          <el-button :disabled="!selected.length || !$store.getters.hasEditRights"
                     type="danger"
                     size="mini"
                     icon="el-icon-delete-solid"
                     round
                     @click="onDelete">Удалить</el-button>
        </el-button-group>
      </toolbar>
      
      <pageable-table ref="table"
                      :loader="loadCategoriesTrash"
                      size="mini" border
                      :default-sort="{prop: 'id', order: 'descending'}"
                      @selection-change="onSelect"
                      empty-text="Данные отсутствуют или не загружены">
        <el-table-column v-if="$store.getters.hasEditRights"
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="title" label="Название" sortable="custom"></el-table-column>
        <el-table-column prop="price" align="right" label="Цена" :formatter="rubFormatter" sortable="custom"></el-table-column>
        <el-table-column prop="published" label="Опубликован" :formatter="booleanFormatter" sortable="custom"></el-table-column>
      </pageable-table>
      </div>
    `,
    components: {
        Toolbar,
        PageableTable
    }
})
export default class ProductTrashView extends Vue {

    $refs: {
        table: PageableTable<ProductItem>
    };

    /** Сервис по работе с категориями */
    @Inject private readonly productService: ProductService;

    /** Выбранные элементы */
    private selected: ProductItem[] = [];
    /** Форматтер да или нет */
    private readonly booleanFormatter = booleanFormatter;
    /** Форматтер цен */
    private readonly rubFormatter = rubFormatter;

    private async loadCategoriesTrash(pageable: SpringPageable): Promise<TableData<ProductItem>> {
        return await this.productService.getTrash(pageable);
    }

    private onSelect(val: ProductItem[]) {
        this.selected = val;
    }

    /**
     * Обработчик востановления
     */
    private async onRestore() {
        await this.productService.trashRestore(this.selected.map(cat => cat.id) as number[]);
        await this.$refs.table.refresh();
        Notification.warning("Продукты востановлены");
    }

    /**
     * Обработчик востановления
     */
    private async onDelete() {
        await this.productService.delete(this.selected.map(cat => cat.id) as number[]);
        await this.$refs.table.refresh();
        Notification.warning("Продукты удалены");
    }
}
