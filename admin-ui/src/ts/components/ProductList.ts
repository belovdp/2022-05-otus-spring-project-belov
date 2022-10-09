import "@/style/img/logo.png";
import {Component, Prop, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {ProductItem, ProductService} from "@/ts/services/ProductService";
import {booleanFormatter} from "@/ts/utils/formatters";

@Component({
    template: `
      <div class="productList">
          <el-button-group slot="buttons">
            <el-button :disabled="!selected.length"
                       type="danger"
                       size="mini"
                       icon="el-icon-delete-solid"
                       round
                       @click="onDelete"></el-button>
          </el-button-group>
    
          <pageable-table ref="table"
                          :loader="loadProducts"
                          size="mini" border
                          :default-sort="{prop: 'id', order: 'descending'}"
                          @selection-change="onSelect"
                          empty-text="Данные отсутствуют или не загружены">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
            <el-table-column prop="title" label="Название" sortable="custom"></el-table-column>
            <el-table-column prop="published" label="Опубликован" :formatter="booleanFormatter" sortable="custom"></el-table-column>
            <el-table-column prop="sortIndex" label="Порядок вывода" sortable="custom"></el-table-column>
          </pageable-table>
      </div>
    `,
    components: {
        PageableTable
    }
})
export default class ProductList extends Vue {

    $refs: {
        table: PageableTable<ProductItem>
    };

    /** Сервис по работе с продуктами */
    @Inject private readonly productService: ProductService;

    /** Идентификатор категории */
    @Prop()
    private categoryId: number;
    /** Выбранные продукты */
    private selected: ProductItem[] = [];
    /** Форматтер да или нет */
    private readonly booleanFormatter = booleanFormatter;

    private async loadProducts(pageable: SpringPageable): Promise<TableData<ProductItem>> {
        const filter = this.categoryId ? {categoryId: this.categoryId, deleted: false} : {};
        return await this.productService.getProducts(filter, pageable);
    }

    private onSelect(val: ProductItem[]) {
        this.selected = val;
    }

    private async onDelete(): Promise<void> {
        await this.productService.moveToTrash(this.selected.map(item => item.id));
        this.selected = [];
        await this.$refs.table.refresh(false);
    }
}