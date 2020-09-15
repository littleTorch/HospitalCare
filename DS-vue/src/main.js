import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/flex.css'
import axios from 'axios'
import echarts from 'echarts'

Vue.prototype.$echarts = echarts

Vue.prototype.$axios = axios
//文字不能隐藏问题
import Fragment from 'vue-fragment'

Vue.use(Fragment.Plugin)
Vue.use(ElementUI);
Vue.config.productionTip = false
// 进入路由之前执行
router.beforeEach((to, from, next) => {
    //to 将要进入的路由
    // from 将要离开的路由
    // next 让路由继续执行

    // 获取当前打开的选项卡
    store.commit('getTabs');
    // 设置当前激活的选项卡
    store.commit('setActiveTabs', to.name);
    //决绝刷新之后菜单数据不存在问题

    let menuData = sessionStorage.getItem('menuList');
    if (to.path === '/') {
        if (menuData) {
            next({path: '/desktop'})
        } else {
            if (!menuData && to.name !== 'login') {
                //next({path: '/'})
            }
        }
    } else {
        if (!menuData && to.name !== 'login') {
            //next({path: '/'})
        }else if(to.path === '/home'){
            next({path: '/desktop'})
        } else {
            if (store.state.MenuStore.menu_data.length == 0) {
                store.commit('getMenuList', router);
                next({path: to.path})
            } else {
                next();
            }
        }
    }
    next();
})
axios.interceptors.request.use(config => {
    let token = sessionStorage.getItem("token");
    config.headers.common['token'] = token;
    return config;
})
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
