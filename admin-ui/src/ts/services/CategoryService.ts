import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import {SpringPageable, TableData} from "@/ts/components/common/PageableTable";

/**
 * Сервис по работе с авторизацией
 */
@Singleton
@OnlyInstantiableByContainer
export class CategoryService {

    /**
     * Возвращает категории в виде дерева
     */
    async getCategoriesTree(): Promise<CategoryTreeItem[]> {
        return (await axios.get<CategoryTreeItem[]>("/product-service/admin/categories/tree", {})).data;
    }

    /**
     * Возвращает категорию
     * @param id идентификатор категории
     */
    async getCategory(id: number): Promise<CategoryItem> {
        return (await axios.get<CategoryItem>(`/product-service/categories/${id}`)).data;
    }

    /**
     * Возвращает содержимое корзины
     */
    async getTrash(pageable: SpringPageable): Promise<TableData<CategoryItem>> {
        return (await axios.get<TableData<CategoryItem>>("/product-service/admin/categories/trash", {
            params: pageable
        })).data;
    }

    /**
     * Переносит в корзину
     * @param ids идентификаторы категории
     */
    async moveToTrash(ids: number[]): Promise<void> {
        await axios.post("/product-service/admin/categories/trash", ids);
    }

    /**
     * Переносит в корзину
     * @param ids идентификаторы категории
     */
    async trashRestore(ids: number[]): Promise<void> {
        await axios.post("/product-service/admin/categories/trash/restore", ids);
    }

    /**
     * Удаляет с концами
     * @param ids идентификаторы категории
     */
    async delete(ids: number[]): Promise<void> {
        await axios.delete("/product-service/admin/categories/", {
            data: ids
        });
    }

    /**
     * Сохраняет категорию
     * @param categoryItem сохраняемая категория
     */
    async saveCategory(categoryItem: CategoryItem): Promise<CategoryItem> {
        return (await axios.post<CategoryItem>("/product-service/admin/categories/", categoryItem)).data;
    }
}

/** Список категорий в виде дерева */
export type CategoryTreeItem = {

    /** Идентификатор */
    id: number;
    /** Заголовок */
    title: string;
    /** Признак что ресурс в корзине */
    deleted: boolean;
    /** Скрыт в меню */
    hideMenu: boolean;
    /** Опубликован */
    published: boolean;
    /** Индекс сортировки */
    sortIndex: number;
    /** Дочерние категории */
    childs: CategoryTreeItem[];
}

/** Категория на редактирование */
export type CategoryItem = {
    /** Идентификатор */
    id: number | null;
    /** Заголовок */
    title: string;
    /** Признак что ресурс в корзине */
    deleted: boolean;
    /** Скрыт в меню */
    hideMenu: boolean;
    /** Опубликован */
    published: boolean;
    /** Индекс сортировки */
    sortIndex: number;
    /** Родительская категория */
    parent: number | null;
}