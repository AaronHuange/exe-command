import Vue from 'vue'
import App from './App.vue'
import AxiosConfig from "./config/axios.config"
import createRouter from "./router"
import Router from "vue-router"
// import Vconsole from "vconsole";

AxiosConfig.init();
// void new Vconsole();
Vue.config.productionTip = false;
const router = createRouter();
Vue.use(Router);
new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
