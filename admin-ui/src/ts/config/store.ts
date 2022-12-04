import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import {AuthService, LoginRequest, TokenInfo} from "@/ts/services/AuthService";
import {Container} from "typescript-ioc";
import router from "@/ts/config/router";
import {CategoryTreeItem} from "@/ts/services/CategoryService";
import {Role} from "@/ts/enums/role";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        tokenInfo: null,
        categories: []
    } as State,
    getters: {
        hasEditRights(state) {
            return state.tokenInfo?.roles.some(role => [Role.ADMIN, Role.EDITOR].includes(Role[role]));
        },
        hasAdminRights(state) {
            return state.tokenInfo?.roles.some(role => Role.ADMIN === role);
        }
    },
    mutations: {},
    actions: {
        async AUTH_LOGIN({state}, authInfo: LoginRequest) {
            const userService = Container.get(AuthService);
            const tokenInfo = await userService.login(authInfo);
            if (!tokenInfo.roles.some(role => Role[role])) {
                throw new Error("Доступ запрещён. Недостаточно прав.");
            }
            Vue.$cookies.set("token-info", tokenInfo);
            axios.defaults.headers.common.Authorization = `Bearer ${tokenInfo.token}`;
            state.tokenInfo = tokenInfo;
        },
        async REFRESH_TOKEN({dispatch, state}) {
            const userService = Container.get(AuthService);
            if (!state.tokenInfo) {
                throw new Error("Что то пошло не так. Авторизуйтесь повторно.");
            }
            try {
                const tokenInfo = await userService.refreshToken(state.tokenInfo.refreshToken);
                if (!tokenInfo.roles.some(role => Role[role])) {
                    throw new Error("Доступ запрещён. Недостаточно прав.");
                }
                Vue.$cookies.set("token-info", tokenInfo);
                axios.defaults.headers.common.Authorization = `Bearer ${tokenInfo.token}`;
                state.tokenInfo = tokenInfo;
            } catch (e) {
                dispatch("AUTH_LOGOUT");
            }
        },
        async AUTH_LOGOUT({state}) {
            Vue.$cookies.remove("token-info");
            delete axios.defaults.headers.common.Authorization;
            state.tokenInfo = null;
            await router.push({name: "Login"});
        }
    },
    modules: {}
});

export type State = {
    /** Информация о токене */
    tokenInfo: TokenInfo | null;
    /** Категории */
    categories: CategoryTreeItem[];
}