import Vue from "vue"
import VueClipboard from "vue-clipboard2"
import {Component} from "vue-property-decorator"
import App from "./App.vue"
import "./filters"
import http from "./http"
import "./plugins/element.js"
import router from "./router"
import store from "./store"

Vue.config.productionTip = false

Vue.prototype.$http = http

//需要手动挂载路由的钩子函数
//https://www.npmjs.com/package/vue-class-component#adding-custom-hooks
Component.registerHooks([
  "beforeRouteEnter",
  "beforeRouteLeave",
  "beforeRouteUpdate"
])

Vue.use(VueClipboard)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app")
