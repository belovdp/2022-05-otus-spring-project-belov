import store from "@/ts/config/store";
import {AxiosRequestConfig} from "axios";

/** Время до протухания токена, до которого стоило бы сходить за новым */
const REFRESH_TOKEN_BEFORE = 60 * 1000;

/**
 * Перехватчики ответов и запросов для Axios
 * Что делаем:
 * - перехватываем запросы и обновляем токен, если можем, либо отправляем на авторизацию
 */
export default class AxiosInterceptor {

    /**
     * Проверяет не требуется ли авторизация или обновление токена.
     * Если требуется, то обновляем токен или кидаем на окно авторизации
     * return конфиг запроса
     */
    static async beforeRequest(config: AxiosRequestConfig) {
        if (AxiosInterceptor.isNeedAuth()) {
            if (store.state.tokenInfo !== null && store.state.tokenInfo.refreshTokenExpiresAt <= Date.now() + REFRESH_TOKEN_BEFORE) {
                await store.dispatch("AUTH_LOGOUT");
                throw new Error("Требуется авторизация");
            }
            await store.dispatch("REFRESH_TOKEN");
            const commonHeaders = (config.headers!.common as unknown) as Record<string, string>;
            commonHeaders.Authorization = `Bearer ${store.state.tokenInfo!.token}`;
        }
        return config;
    }

    /**
     * Обработчик ошибок запроса
     * @param error ошибка
     */
    static onRequestError(error) {
        return Promise.reject(error);
    }

    /**
     * Возвращает признак необходимости авторизации
     */
    private static isNeedAuth() {
        if (!store.state.tokenInfo) {
            return true;
        }
        return store.state.tokenInfo.tokenExpiresAt <= Date.now() + REFRESH_TOKEN_BEFORE;
    }
}