import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import {LoginRequest, TokenInfo, AuthService} from "@/ts/services/AuthService";
import {Container} from "typescript-ioc";
import router from "@/ts/config/router";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        tokenInfo: null
    } as State,
    getters: {},
    mutations: {},
    actions: {
        async AUTH_LOGIN({state}, authInfo: LoginRequest) {
            const userService = Container.get(AuthService);
            const tokenInfo = await userService.login(authInfo);
            Vue.$cookies.set("token-info", tokenInfo);
            axios.defaults.headers.common.Authorization = `Bearer ${tokenInfo.token}`;
            state.tokenInfo = tokenInfo;
        },
        async REFRESH_TOKEN({state}) {
            const userService = Container.get(AuthService);
            if (!state.tokenInfo) {
                throw new Error("Что то пошло не так. Авторизуйтесь повторно.");
            }
            const tokenInfo = await userService.refreshToken(state.tokenInfo.refreshToken);
            Vue.$cookies.set("token-info", tokenInfo);
            axios.defaults.headers.common.Authorization = `Bearer ${tokenInfo.token}`;
            state.tokenInfo = tokenInfo;
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
}