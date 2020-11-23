import router from "vue-router"
import {Router} from "express";

export default () => {
    return new Router({
        mode: "hase",
        base: process.env.BASE_URL,
        routes: [
            {
                path: "/auth",
                name: "Auth",
                component: () => import(/* webpackChunkName: "Login" */"./view/Login")
            },
            {
                path: "/home",
                name: "Home",
                component: () => import(/* webpackChunkName: "Home" */"./view/Home")
            }
        ]
    });
}
