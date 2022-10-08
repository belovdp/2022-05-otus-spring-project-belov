import VueRouter, { RouteConfig } from "vue-router";
import Vue from "vue";
import HomeView from "@/ts/views/HomeView";
import LoginView from "@/ts/views/LoginView";
import store from "@/ts/config/store";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        component: HomeView
    },
    {
        path: "/login",
        name: "Login",
        component: LoginView,
        meta: {
            fullScreen: true
        }
    },
];

const router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {
    if (!store.state.tokenInfo && to.name !== "Login") {
        next({ name: "Login" });
    } else {
        next();
    }
});

export default router;
