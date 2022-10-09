import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import {SpringPageable, TableData} from "@/ts/components/common/PageableTable";

/**
 * Сервис по работе с авторизацией
 */
@Singleton
@OnlyInstantiableByContainer
export class ProductService {

    /**
     * Возвращает продукты
     * @param filter   фильтр
     * @param pageable пагинация
     */
    async getProducts(filter: ProductFilter, pageable: SpringPageable): Promise<TableData<ProductItem>> {
        return (await axios.get<TableData<ProductItem>>("/product-service/admin/products/", {
            params: {
                ...pageable,
                ...filter
            }
        })).data;
    }

    /**
     * Возвращает продукт
     * @param id идентификатор продукта
     */
    async getProduct(id: number): Promise<ProductItem> {
        return (await axios.get<ProductItem>(`/product-service/products/${id}`)).data;
    }

    /**
     * Возвращает содержимое корзины
     */
    async getTrash(pageable: SpringPageable): Promise<TableData<ProductItem>> {
        return (await axios.get<TableData<ProductItem>>("/product-service/admin/products/trash", {
            params: pageable
        })).data;
    }

    /**
     * Переносит в корзину
     * @param ids идентификаторы продуктов
     */
    async moveToTrash(ids: number[]): Promise<void> {
        await axios.post("/product-service/admin/products/trash", ids);
    }

    /**
     * Переносит в корзину
     * @param ids идентификаторы категории
     */
    async trashRestore(ids: number[]): Promise<void> {
        await axios.post("/product-service/admin/products/trash/restore", ids);
    }

    /**
     * Удаляет с концами
     * @param ids идентификаторы категории
     */
    async delete(ids: number[]): Promise<void> {
        await axios.delete("/product-service/admin/products/", {
            data: ids
        });
    }

    /**
     * Сохраняет категорию
     * @param product сохраняемый продукт
     */
    async saveProduct(product: SaveProductRequest): Promise<ProductItem> {
        return (await axios.post<ProductItem>("/product-service/admin/products/", product)).data;
    }
}

/** Фильтр продуктов */
export type ProductFilter = {
    /** Категория */
    categoryId?: number;
    /** Признак того что продукт в корзине */
    deleted?: boolean;
    /** Признак того что опубликовано */
    published?: boolean;
}

/** Продукт */
export type ProductItem = {
    /** Идентификатор */
    id: number;
    /** Заголовок */
    title: string;
    /** Признак что ресурс в корзине */
    deleted: boolean;
    /** Опубликован */
    published: boolean;
    /** Индекс сортировки */
    sortIndex: number;
}

export type SaveProductRequest = ProductItem & {
    /** Список идентификаторов категорий */
    categories: number[];
}