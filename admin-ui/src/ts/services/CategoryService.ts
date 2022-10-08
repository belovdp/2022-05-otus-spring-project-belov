import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";

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
     * Переносит в корзину
     * @param ids идентификаторы категории
     */
    async moveToTrash(ids: number[]): Promise<void> {
        await axios.post("/product-service/admin/categories/trash", ids);
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