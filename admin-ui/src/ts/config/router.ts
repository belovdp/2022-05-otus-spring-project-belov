import VueRouter, {RawLocation, Route, RouteConfig} from "vue-router";
import Vue from "vue";
import LoginView from "@/ts/views/LoginView";
import store from "@/ts/config/store";
import CategoryView from "@/ts/views/CategoryView";
import CategoryTrashView from "@/ts/views/CategoryTrashView";
import ProductTrashView from "@/ts/views/ProductTrashView";
import ProductView from "@/ts/views/ProductView";
import OrderListView from "@/ts/views/OrderListView";
import UserListView from "@/ts/views/UserListView";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
    {
        path: "/",
        component: OrderListView
    },
    {
        path: "/login",
        name: "Login",
        component: LoginView,
        meta: {
            fullScreen: true
        }
    },
    {
        path: "/category/trash",
        name: "CategoryTrashView",
        component: CategoryTrashView
    },
    {
        path: "/category/:id*",
        name: "CategoryView",
        component: CategoryView
    },
    {
        path: "/product/trash",
        name: "ProductTrashView",
        component: ProductTrashView
    },
    {
        path: "/product/:id*",
        name: "ProductView",
        component: ProductView
    },
    {
        path: "/orders",
        name: "OrderListView",
        component: OrderListView
    },
    {
        path: "/users",
        name: "UserListView",
        component: UserListView
    },
];

/** Игнорируем ошибку о попытке перейти с урла на тот же самый урл */
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location: RawLocation): Promise<Route> {
    return originalPush.call<VueRouter, [RawLocation], Promise<Route>>(this, location).catch(err => {
        if (err.name !== "NavigationDuplicated") {
            throw err;
        }
        return (null as unknown) as Route;
    });
};

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
