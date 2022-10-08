import {Component, Prop, Vue} from "vue-property-decorator";
import {DefaultSortOptions, SortOrder as ElSortOrder, ElTable} from "element-ui/types/table";
import {ElTableColumn} from "element-ui/types/table-column";

/**
 * Компонент таблиц с пагинацией и сортировкой
 * T - тип данных записи таблицы
 */
@Component({
    template: `
        <div class="pageableTable">
            <el-row type="flex" justify="center">
                <el-table v-bind="$attrs"
                          :data="data.content"
                          lazy
                          :defaultSort="defaultSort"
                          v-on="$listeners"
                          @sort-change="onSortChanged"
                          ref="table">
                    <template v-for="(_, slot) of $scopedSlots" v-slot:[slot]="scope"><slot :name="slot" v-bind="scope"/></template>
                </el-table>
            </el-row>
    
            <el-row v-if="data.totalPages" type="flex" justify="center">
                <el-pagination layout="total, jumper, prev, pager, next, sizes"
                               @size-change="refresh"
                               @current-change="() => refresh(false)"
                               :current-page.sync="pageable.page"
                               :page-sizes="pageSizes"
                               :page-size.sync="pageable.size"
                               :total="data.totalElements">
                </el-pagination>
            </el-row>
        </div>
    `
})
export default class PageableTable<T> extends Vue {
    /** Описание справочников */
    $refs!: {
        table: ElTable
    };
    /** Функция-загрузчик данных */
    @Prop({required: true})
    private loader: (pageable: SpringPageable) => Promise<TableData<T>>;
    /** Сортировка по умолчанию */
    @Prop()
    private defaultSort: DefaultSortOptions;
    /** Данные таблицы */
    private data: TableData<T> | null = this.getNullableData();
    /** Опции получения данных */
    private pageable: Pageable = {
        size: 5,
        page: 1
    };
    /** Варианты количества отображения на странице */
    private readonly pageSizes = [5, 10, 20, 50, 100];

    private async mounted(): Promise<void> {
        if (this.defaultSort) {
            this.$refs.table.sort(this.defaultSort.prop, this.defaultSort.order);
        } else {
            await this.refresh();
        }
    }

    /**
     * Обновляет данные таблицы
     * @param goToFirstPage признак перехода на первую страницу
     */
    public async refresh(goToFirstPage = true): Promise<void> {
        if (goToFirstPage) {
            this.pageable.page = 1;
        }
        try {
            const {size, page, sortField, sortOrder} = this.pageable;
            this.data = await this.loader({
                size,
                page: page - 1,
                sort: `${sortField},${sortOrder ? sortOrder: SortOrder.ASC}`
            });
        } catch (e) {
            this.data = this.getNullableData();
        }
    }

    /**
     * Обработчик сортировки таблицы
     * @param sortOptions опции сортировки
     */
    private async onSortChanged(sortOptions: DefaultSortOptions & {column: ElTableColumn}): Promise<void> {
        this.pageable.sortField = sortOptions.column.columnKey ? sortOptions.column.columnKey : sortOptions.prop;
        this.pageable.sortOrder = this.convertSortString(sortOptions.order);
        await this.refresh();
    }

    /**
     * Возвращает нулевые данные для таблицы
     * @return нулевые данные для таблицы
     */
    private getNullableData(): TableData<T> {
        return {
            last: true,
            first: true,
            totalPages: 0,
            totalElements: 0,
            empty: true,
            content: [],
        };
    }

    /**
     * Возвращает направление сортировки на основе строки сортировки используемой в element
     * @param string строка сортировки используемая в element
     * @return направление сортировки
     */
    private convertSortString(string: ElSortOrder): SortOrder {
        return string === "ascending" ? SortOrder.ASC : SortOrder.DESC;
    }
}

/** Ответ сервера на запрос данных для таблицы */
export type TableData<T> = {
    /** Признак того что это последняя страница */
    last: boolean;
    /** Признак того что это первкая страница */
    first: boolean;
    /** Количество страниц всего */
    totalPages: number;
    /** Количество строк всего */
    totalElements: number;
    /** Признак того что ничего не найдено */
    empty: boolean;
    /** Строки согласно пагинации */
    content: T[];
}

/** Запрос на пагинацию ожидаемая спрингом */
export type SpringPageable = {
    size: number;
    page: number;
    sort?: string;
}

/** Внутренняя сущность для пагинации */
type Pageable = {
    size: number;
    page: number;
    sortField?: string;
    sortOrder?: SortOrder;
}

/** Порядок сортировки */
enum SortOrder {
    DESC = "DESC",
    ASC = "ASC"
}