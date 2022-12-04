import "@/style/styles.scss";
import "@/style/img/favicon.ico";
import App from "@/ts/App";
import axios, {AxiosError} from "axios";
import Element, {Notification} from "element-ui";
import Vue from "vue";
import router from "@/ts/config/router";
import store from "@/ts/config/store";
import locale from "element-ui/lib/locale/lang/ru-RU";
import "@/js/env.js";
import VueAxios from "vue-axios";
import VueCookies from "vue-cookies";
import AxiosInterceptor from "@/ts/config/axiosInterceptor";

Vue.config.productionTip = false;

axios.defaults.baseURL = ENVS.GATEWAY_URL;

initAuthInfo();

Vue.use(Element, { locale });
Vue.use(VueAxios, axios);
Vue.use(VueCookies);

axios.interceptors.request.use(AxiosInterceptor.beforeRequest, AxiosInterceptor.onRequestError);

/**
 * Глобальный обработчик ошибок Vue
 */
Vue.config.errorHandler = (err: Error & AxiosError) => {
    Notification.error(getErrorMessage(err));
};

/**
 * Глобальный обработчик ошибок для промисов
 */
window.addEventListener("unhandledrejection", (event) => {
    Notification.error(getErrorMessage(event.reason));
});

/**
 * Извлекает сообщение об ошибке
 * @param error ошибка
 */
function getErrorMessage(error: Error & AxiosError) {
    return error.response?.data?.message ? error.response?.data?.message : error.message;
}

/**
 * Проставляет информацию с авторизацией из куков
 */
function initAuthInfo() {
    if (window.$cookies.get("token-info")) {
        const tokenInfo = window.$cookies.get("token-info");
        axios.defaults.headers.common.Authorization = `Bearer ${tokenInfo.token}`;
        store.state.tokenInfo = tokenInfo;
    }
}

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
