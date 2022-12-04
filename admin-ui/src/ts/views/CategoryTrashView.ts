import {Component, Vue} from "vue-property-decorator";
import {Inject} from "typescript-ioc";
import {CategoryItem, CategoryService} from "@/ts/services/CategoryService";
import Toolbar from "@/ts/components/common/Toolbar";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/common/PageableTable";
import {Notification} from "element-ui";
import store from "@/ts/config/store";

@Component({
    template: `
      <div class="categoryView">
      <toolbar title="Корзина категорий">
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
      </pageable-table>
      </div>
    `,
    components: {
        Toolbar,
        PageableTable
    }
})
export default class CategoryTrashView extends Vue {

    $refs: {
        table: PageableTable<CategoryItem>
    };

    /** Сервис по работе с категориями */
    @Inject private readonly categoryService: CategoryService;

    private selected: CategoryItem[] = [];

    private async loadCategoriesTrash(pageable: SpringPageable): Promise<TableData<CategoryItem>> {
        return await this.categoryService.getTrash(pageable);
    }

    private onSelect(val: CategoryItem[]) {
        this.selected = val;
    }

    /**
     * Обработчик востановления
     */
    private async onRestore() {
        await this.categoryService.trashRestore(this.selected.map(cat => cat.id) as number[]);
        store.state.categories = await this.categoryService.getCategoriesTree();
        await this.$refs.table.refresh();
        Notification.warning("Категории востановлены");
    }

    /**
     * Обработчик востановления
     */
    private async onDelete() {
        await this.categoryService.delete(this.selected.map(cat => cat.id) as number[]);
        store.state.categories = await this.categoryService.getCategoriesTree();
        await this.$refs.table.refresh();
        Notification.warning("Категории удалены");
    }
}
