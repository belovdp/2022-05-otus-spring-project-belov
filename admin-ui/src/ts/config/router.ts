import VueRouter, { RouteConfig } from "vue-router";
import Vue from "vue";
import HomeView from "@/ts/views/HomeView";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        name: "home",
        component: HomeView
    }
];

const router = new VueRouter({
    mode: "history",
    routes
});

export default router;
