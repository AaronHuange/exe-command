import Vue from 'vue'
import App from './App.vue'
import AxiosConfig from "./config/axios.config"


AxiosConfig.init();
Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app');
