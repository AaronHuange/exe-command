import Router from "vue-router";

export default () => {
    return new Router({
        mode: "hash",
        base: process.env.BASE_URL,
        routes: [
            {
                path:"/",
                name:"App",

            },
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
