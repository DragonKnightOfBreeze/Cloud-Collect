import Vue from "vue"
import VueRouter, {RouteConfig} from "vue-router"

const AboutView = () => import("@/views/about/AboutView.vue")
const CollectView = () => import("@/views/collect/CollectView.vue")
const CollectDetailView = () => import( "@/views/collect/CollectDetailView.vue")
const PraiseByUserView = () => import("@/views/collect/PraiseByUserView.vue")
const HistoryView = () => import( "@/views/history/HistoryView.vue")
const HomeView = () => import(  "@/views/home/HomeView.vue")
const NoticeView = () => import("@/views/notice/NoticeView.vue")
const NoticeDetailView = () => import( "@/views/notice/NoticeDetailView.vue")
const ProfileView = () => import( "@/views/profile/ProfileView.vue")
const FollowByUserView = () => import("@/views/profile/FollowByUserView.vue")
const FollowToUserView = () => import("@/views/profile/FollowToUserView.vue")
const SearchView = () => import( "@/views/search/SearchView.vue")
const ActivateSuccessView = () => import( "@/views/success/ActivateSuccessView.vue")
const ResetPasswordSuccessView = () => import( "@/views/success/ResetPasswordSuccessView.vue")
const Error403View = () => import("@/views/error/Error403View.vue")
const Error404View = () => import("@/views/error/Error404View.vue")
const Error500View = () => import("@/views/error/Error500View.vue")
const Error501View = () => import("@/views/error/Error501View.vue")

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
    path: "/collect/:id/praiseByUser",
    name: "praiseByUser",
    component: PraiseByUserView
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
    path: "/profile/:id",
    name: "profile",
    component: ProfileView
  }, {
    path: "/profile/:id/followByUser",
    name: "followByUser",
    component: FollowByUserView
  }, {
    path: "/profile/:id/followToUser",
    name: "followToUser",
    component: FollowToUserView
  }, {
    path: "/search",
    name: "search",
    component: SearchView
  }, {
    path: "/success/activate",
    name: "activateSuccess",
    component: ActivateSuccessView
  }, {
    path: "/success/resetPassword",
    name: "resetPasswordSuccess",
    component: ResetPasswordSuccessView
  }, {
    path: "/error",
    redirect: "/error/500"
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
