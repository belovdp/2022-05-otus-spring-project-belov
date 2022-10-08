import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";

/**
 * Сервис по работе с авторизацией
 */
@Singleton
@OnlyInstantiableByContainer
export class AuthService {

    /** Axios без interceptor и очищающий заголовок авторизации */
    private http = axios.create({
        transformRequest: [(data, headers) => {
            if (headers) {
                delete headers.common["Authorization"];
            }
            return data;
        }
        ].concat(axios.defaults.transformRequest || [])
    });

    /**
     * Возвращает токен на основе логина и пароля
     */
    async login(request: LoginRequest): Promise<TokenInfo> {
        const tokenInfo = (await this.http.post<TokenInfoResponse>("/user-service/users/token", {...request})).data;
        return AuthService.convertTokenResponse(tokenInfo);
    }

    /**
     * Возвращает токен на основе refresh Токена
     */
    async refreshToken(refreshToken: string): Promise<TokenInfo> {
        const tokenInfo = (await this.http.post<TokenInfoResponse>("/user-service/users/refreshToken", refreshToken)).data;
        return AuthService.convertTokenResponse(tokenInfo);
    }

    /**
     * Конвертирует ответ от сервера в информацию требуемую нам для механизма авторизации
     * @param tokenInfo подробная информация о токене от сервера
     * @return информация о токене и когда он протухает
     */
    private static convertTokenResponse(tokenInfo: TokenInfoResponse): TokenInfo {
        const tokenExpiresAt = Date.parse(tokenInfo.loginResponseTime) + tokenInfo.expiresIn * 1000;
        const refreshTokenExpiresAt = Date.parse(tokenInfo.loginResponseTime) + tokenInfo.refreshExpiresIn * 1000;
        return {
            token: tokenInfo.token,
            refreshToken: tokenInfo.refreshToken,
            tokenExpiresAt: tokenExpiresAt,
            refreshTokenExpiresAt: refreshTokenExpiresAt,
        };
    }
}

/** Запрос на получение токена */
export type LoginRequest = {
    /** Логин */
    login: string;
    /** Пароль */
    password: string;
}

/** Информация о токене от сервера */
export type TokenInfoResponse = {
    /** Токен */
    token: string;
    /** Время жизни токена */
    expiresIn: number;
    /** Токен для обновления токена */
    refreshToken: string;
    /** Время жизни токена для обновления токена */
    refreshExpiresIn: number;
    /** Время получения токена */
    loginResponseTime: string;
    /** Время окончания токена */
    endDateTime: Date;
}

/** Информация о токене */
export type TokenInfo = {
    /** Токен */
    token: string;
    /** Токен для обновления токена */
    refreshToken: string;
    /** Время окончания токена */
    tokenExpiresAt: number;
    /** Время окончания refresh токена */
    refreshTokenExpiresAt: number;
}