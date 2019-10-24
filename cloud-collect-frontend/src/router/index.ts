import About from "@/views/About.vue"
import BrowsingHistory from "@/views/browsingHistory/BrowsingHistory.vue"
import Collect from "@/views/collect/Collect.vue"
import Comment from "@/views/comment/Comment.vue"

import Error404 from "@/views/error/Error404.vue"
import Error500 from "@/views/error/Error500.vue"
import Error501 from "@/views/error/Error501.vue"
import GlobalSearch from "@/views/GlobalSearch.vue"
import Home from "@/views/Home.vue"
import Notice from "@/views/notice/Notice.vue"
import Profile from "@/views/profile/Profile.vue"
import Vue from "vue"
import VueRouter, {RouteConfig} from "vue-router"

const BrowsingHistoryList = () => import("@/views/browsingHistory/BrowsingHistoryList.vue")
const CollectList = () => import("@/views/collect/CollectDetails.vue")
const CollectDetails = () => import("@/views/collect/CollectDetails.vue")
const CommentList = () => import("@/views/comment/CommentList.vue")
const NoticeList = () => import("@/views/notice/NoticeList.vue")
const NoticeDetails = () => import("@/views/notice/NoticeDetails.vue")

Vue.use(VueRouter)

const routes: RouteConfig[] = [
  {
    path: "/",
    name: "home",
    component: Home,
  }, {
    path: "/about",
    component: About,
  }, {
    path: "/globalSearch",
    component: GlobalSearch,
  }, {
    path: "/browsingHistory",
    component: BrowsingHistory,
    children: [
      {
        path: "",
        component: BrowsingHistoryList,
      },
    ],
  }, {
    path: "/collect",
    component: Collect,
    children: [
      {
        path: "",
        component: CollectList,
      }, {
        path: ":id",
        component: CollectDetails,
      },
    ],
  }, {
    path: "/comment",
    component: Comment,
    children: [
      {
        path: "",
        component: CommentList,
      },
    ],
  }, {
    path: "/notice",
    component: Notice,
    children: [
      {
        path: "",
        component: NoticeList,
      }, {
        path: ":id",
        component: NoticeDetails,
      },
    ],
  }, {
    path: "/profile/:id",
    component: Profile,
  }, {
    path: "/error/404",
    component: Error404,
  }, {
    path: "/error/500",
    component: Error500,
  }, {
    path: "/error/501",
    component: Error501,
  },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
})

export default router
