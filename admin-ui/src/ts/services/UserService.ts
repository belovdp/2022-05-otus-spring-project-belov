import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import {SpringPageable, TableData} from "@/ts/components/common/PageableTable";

/**
 * Сервис по работе с пользователями
 */
@Singleton
@OnlyInstantiableByContainer
export class UserService {

    /**
     * Возвращает список пользователей
     * @param pageable пагинация
     */
    async getUsers(pageable: SpringPageable): Promise<TableData<User>> {
        return (await axios.get<TableData<User>>("/user-service/admin/users/", {
            params: {
                ...pageable
            }
        })).data;
    }

    async getUser(userId: string): Promise<User> {
        return (await axios.get<User>(`/user-service/admin/users/${userId}`)).data;
    }

    /**
     * Возвращает список групп пользователей
     */
    async getGroups(): Promise<Group[]> {
        return (await axios.get<Group[]>("/user-service/admin/groups")).data;
    }

    /**
     * Меняет группу пользователей
     * @param userId  идентификатор пользователя
     * @param groupId группа пользователей
     */
    async changeGroup(userId: string, groupId: string): Promise<void> {
        await axios.post(`/user-service/admin/users/${userId}/group`, groupId, {
            headers: { "Content-Type": "text/plain" }
        });
    }

    /**
     * Изменяет статус пользователя
     * @param userId   идентификатор пользователя
     * @param activate статус активации
     */
    async onChangeEnableStatus(userId: string, activate: boolean): Promise<void> {
        await axios.post(`/user-service/admin/users/${userId}/activator`, activate, {
            headers: { "Content-Type": "application/json" }
        });
    }
}

/** Пользователь */
export type User = {
    /** Идентификатор */
    id: string;
    /** Имя пользователя */
    username: string
    /** Активен */
    enabled: boolean;
    /** Почта */
    email: string;
    /** Имя */
    firstName: string;
    /** Фамилия */
    lastName: string;
    /** Группы */
    groups: string[];
}

/** Группа */
export type Group = {
    id: string;
    name: string;
}