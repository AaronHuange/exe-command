import Vue from 'vue'
import App from './App.vue'
import AxiosConfig from "./config/axios.config"
import routerSetting from "./router"
import Router from "vue-router"


AxiosConfig.init();
Vue.config.productionTip = false

Vue.use(Router);

const router=routerSetting();
new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
