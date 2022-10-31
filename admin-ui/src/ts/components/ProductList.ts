import "@/style/img/logo.png";
import {Component, Prop, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {ProductItem, ProductService} from "@/ts/services/ProductService";
import {booleanFormatter, rubFormatter} from "@/ts/utils/formatters";
import {Notification} from "element-ui";
import {FileService} from "@/ts/services/FileService";

@Component({
    template: `
      <div class="productList">
          <el-button-group slot="buttons">
            <el-button :disabled="!selected.length || !$store.getters.hasEditRights"
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
            <el-table-column v-if="$store.getters.hasEditRights"
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
            <el-table-column
                label="Изображение"
                width="200">
              <template slot-scope="scope">
                <el-image :src="getPreview(scope.row.id)" fit="cover"></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="title" label="Название" sortable="custom"></el-table-column>
            <el-table-column prop="price" align="right" label="Цена" :formatter="rubFormatter" sortable="custom"></el-table-column>
            <el-table-column prop="published" label="Опубликован" :formatter="booleanFormatter" sortable="custom"></el-table-column>
            <el-table-column prop="sortIndex" label="Порядок вывода" sortable="custom"></el-table-column>
            <el-table-column
                label="Действия"
                width="220">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="onEdit(scope.row.id)">Редактировать</el-button>
                </template>
            </el-table-column>
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
    /** Сервис по работе с файлами */
    @Inject private readonly fileService: FileService;

    /** Идентификатор категории */
    @Prop()
    private categoryId: number;
    /** Выбранные продукты */
    private selected: ProductItem[] = [];
    /** Форматтер да или нет */
    private readonly booleanFormatter = booleanFormatter;
    /** Форматтер цен */
    private readonly rubFormatter = rubFormatter;

    private async loadProducts(pageable: SpringPageable): Promise<TableData<ProductItem>> {
        const filter = this.categoryId ? {categoryId: this.categoryId, deleted: false} : {};
        return await this.productService.getProducts(filter, pageable);
    }

    private onSelect(val: ProductItem[]) {
        this.selected = val;
    }

    private async onDelete(): Promise<void> {
        await this.productService.moveToTrash(this.selected.map(item => item.id) as number[]);
        this.selected = [];
        await this.$refs.table.refresh(false);
        Notification.success("Продукты перемещены в корзину");
    }

    private async onEdit(id: string): Promise<void> {
        await this.$router.push({
            name: "ProductView",
            params: {id}
        });
    }

    private getPreview(id: number) {
        return this.fileService.getPreviewUrl(id);
    }
}
