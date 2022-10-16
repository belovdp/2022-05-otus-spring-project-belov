import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import {SpringPageable, TableData} from "@/ts/components/common/PageableTable";

/**
 * Сервис по работе с заказами
 */
@Singleton
@OnlyInstantiableByContainer
export class OrderService {

    /**
     * Возвращает заказы пользователя
     * @param pageable пагинация
     * @return заказы
     */
    async getOrders(pageable: SpringPageable): Promise<TableData<OrderShort>> {
        return (await axios.get<TableData<OrderShort>>("/order-service/admin/orders/", {
            params: {pageable}
        })).data;
    }

    /**
     * Вовзращает заказ
     * @param orderId идентификатор заказа
     * @return заказ
     */
    async getOrder(orderId: number): Promise<Order> {
        return (await axios.get<Order>(`/order-service/admin/orders/${orderId}`)).data;
    }

    /**
     * Изменяет данные заказа
     * @param updateOrderRequest запрос на изменение заказа
     * @return заказ
     */
    async updateOrder(updateOrderRequest: UpdateOrderRequest): Promise<Order> {
        return (await axios.post<Order>("/order-service/admin/orders/", updateOrderRequest)).data;
    }
}

/** Заказ укороченный */
export type OrderShort = {
    /** Идентификатор */
    id: number;
    /** Время создания */
    created: string;
    /** Email указанный при заказе */
    email: string;
    /** Телефон указанный для связи */
    phone: string;
    /** Заметка */
    note: string;
    /** Адрес */
    address: string;
    /** Идентификатор пользователя из keycloak */
    userId: string;
    /** Имя контактного лица */
    username: string;
}

/** Заказ */
export type Order = {
    /** Подробная информация о пользователе */
    userInfo: UserInfo;
    /** Продукты в заказе */
    items: OrderItem[];
} & OrderShort;

/** Информация о пользователе */
type UserInfo = {
    /** Идентификатор пользователя */
    id: string;
    /** Информация о пользователе */
    username: string;
    /** Активность */
    enabled: boolean;
    /** Имя */
    firstName: string;
    /** Фамилия */
    lastName: string;
    /** Почта */
    email: string;
}

/** Информация о товаре */
type OrderItem = {
    /** Идентификатор */
    id: number;
    /** Идентификатор товара */
    productId: number;
    /** Заголовок товара */
    title: string;
    /** Цена */
    price: number;
    /** Количество */
    count: number;
}

/** Запрос на обновление заказа */
export type UpdateOrderRequest = {
    /** Идентификатор */
    id: number;
    /** Email указанный при заказе */
    email: string;
    /** Телефон указанный для связи */
    phone: string;
    /** Заметка */
    note: string;
    /** Адрес */
    address: string;
    /** Имя контактного лица */
    username: string;
}