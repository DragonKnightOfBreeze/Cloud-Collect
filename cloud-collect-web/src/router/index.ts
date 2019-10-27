import Vue from "vue"
import VueRouter from "vue-router"

const About = () => import(/* webpackChunkName: "about" */ "@/views/about/About.vue")
const Collect = () => import(/* webpackChunkName: "collect" */ "@/views/collect/Collect.vue")
const CollectDetail = () => import(/* webpackChunkName: "collectDetail" */ "@/views/collect/CollectDetail.vue")
const Comment = () => import(/* webpackChunkName: "comment" */ "@/views/comment/Collect.vue")
const History = () => import(/* webpackChunkName: "history" */ "@/views/history/History.vue")
const Home = () => import(/* webpackChunkName: "home" */  "@/views/home/Home.vue")
const Notice = () => import(/* webpackChunkName: "notice" */ "@/views/notice/Notice.vue")
const NoticeDetail = () => import(/* webpackChunkName: "noticeDetail" */ "@/views/notice/NoticeDetail.vue")
const Profile = () => import(/* webpackChunkName: "profile" */ "@/views/profile/Profile.vue")
const Search = () => import(/* webpackChunkName: "search" */ "@/views/search/Search.vue")
const User = () => import(/* webpackChunkName: "user" */ "@/views/user/User.vue")
const UserDetail = () => import(/* webpackChunkName: "userDetail" */ "@/views/user/UserDetail.vue")
const Error403 = () => import(/* webpackChunkName: "error403" */ "@/views/error/Error403.vue")
const Error404 = () => import(/* webpackChunkName: "error404" */ "@/views/error/Error404.vue")
const Error500 = () => import(/* webpackChunkName: "error500" */ "@/views/error/Error500.vue")
const Error501 = () => import(/* webpackChunkName: "error501" */ "@/views/error/Error501.vue")

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "home",
    component: Home
  }, {
    path: "/about",
    name: "about",
    component: About
  }, {
    path: "/collect",
    name: "collect",
    component: Collect
  }, {
    path: "/collect/:id",
    name: "collectDetail",
    component: CollectDetail
  }, {
    path: "/comment",
    name: "comment",
    component: Comment
  }, {
    path: "/history",
    name: "history",
    component: History
  }, {
    path: "/notice",
    name: "notice",
    component: Notice
  }, {
    path: "/notice/:id",
    name: "noticeDetail",
    component: NoticeDetail
  }, {
    path: "/profile",
    name: "profile",
    component: Profile
  }, {
    path: "/search",
    name: "search",
    component: Search
  }, {
    path: "/user",
    name: "user",
    component: User
  }, {
    path: "/user/:id",
    name: "userDetail",
    component: UserDetail
  }, {
    path: "/error/403",
    name: "error403",
    component: Error403
  }, {
    path: "/error/404",
    name: "error404",
    component: Error404
  }, {
    path: "/error/500",
    name: "error500",
    component: Error500
  }, {
    path: "/error/501",
    name: "error501",
    component: Error501
  }
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

export default router
