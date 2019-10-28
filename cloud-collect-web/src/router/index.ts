import Vue from "vue"
import VueRouter, {RouteConfig} from "vue-router"

const AboutView = () => import(/* webpackChunkName: "about" */ "@/views/about/AboutView.vue")
const CollectView = () => import(/* webpackChunkName: "collect" */ "@/views/collect/CollectView.vue")
const CollectDetailView = () => import(/* webpackChunkName: "collectDetail" */ "@/views/collect/CollectDetailView.vue")
const CommentView = () => import(/* webpackChunkName: "comment" */ "@/views/comment/CommentView.vue")
const HistoryView = () => import(/* webpackChunkName: "history" */ "@/views/history/HistoryView.vue")
const HomeView = () => import(/* webpackChunkName: "home" */  "@/views/home/HomeView.vue")
const NoticeView = () => import(/* webpackChunkName: "notice" */ "@/views/notice/NoticeView.vue")
const NoticeDetailView = () => import(/* webpackChunkName: "noticeDetail" */ "@/views/notice/NoticeDetailView.vue")
const ProfileView = () => import(/* webpackChunkName: "profile" */ "@/views/profile/ProfileView.vue")
const SearchView = () => import(/* webpackChunkName: "search" */ "@/views/search/SearchView.vue")
const UserView = () => import(/* webpackChunkName: "user" */ "@/views/user/UserView.vue")
const UserDetailView = () => import(/* webpackChunkName: "userDetail" */ "@/views/user/UserDetailView.vue")
const Error403View = () => import(/* webpackChunkName: "error403" */ "@/views/error/Error403View.vue")
const Error404View = () => import(/* webpackChunkName: "error404" */ "@/views/error/Error404View.vue")
const Error500View = () => import(/* webpackChunkName: "error500" */ "@/views/error/Error500View.vue")
const Error501View = () => import(/* webpackChunkName: "error501" */ "@/views/error/Error501View.vue")

Vue.use(VueRouter)

const routes: RouteConfig[] = [
  {
    path: "/",
    name: "home",
    component: HomeView
  }, {
    path: "/about",
    name: "about",
    component: AboutView
  }, {
    path: "/collect",
    name: "collect",
    component: CollectView
  }, {
    path: "/collect/:id",
    name: "collectDetail",
    component: CollectDetailView
  }, {
    path: "/comment",
    name: "comment",
    component: CommentView
  }, {
    path: "/history",
    name: "history",
    component: HistoryView
  }, {
    path: "/notice",
    name: "notice",
    component: NoticeView
  }, {
    path: "/notice/:id",
    name: "noticeDetail",
    component: NoticeDetailView
  }, {
    path: "/profile",
    name: "profile",
    component: ProfileView
  }, {
    path: "/search",
    name: "search",
    component: SearchView
  }, {
    path: "/user",
    name: "user",
    component: UserView
  }, {
    path: "/user/:id",
    name: "userDetail",
    component: UserDetailView
  }, {
    path: "/error/403",
    name: "error403",
    component: Error403View
  }, {
    path: "/error/404",
    name: "error404",
    component: Error404View
  }, {
    path: "/error/500",
    name: "error500",
    component: Error500View
  }, {
    path: "/error/501",
    name: "error501",
    component: Error501View
  }
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

export default router
