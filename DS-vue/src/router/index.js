import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
   return VueRouterPush.call(this, to).catch(err => err) 
  }

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        name: 'desktop',
        path: '/desktop',
        component: () => import(/* webpackChunkName: "about" */ '@/views/DeskTop.vue')
      },
      {
        name: 'my',
        path: '/my',
        component: () => import(/* webpackChunkName: "about" */ '@/views/My.vue')
      },
    ]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
