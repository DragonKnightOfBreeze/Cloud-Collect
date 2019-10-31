import Vue from "vue"
import VueRouter, {RouteConfig} from "vue-router"

const Home = () => import(  "@/views/Home.vue")
const Collects = () => import("@/views/Collects.vue")
const CollectOverview = () => import("@/views/collects/CollectOverview.vue")
const CollectDetail = () => import("@/views/collects/CollectDetail.vue")
const CollectDetailOverview = () => import("@/views/collects/collectDetail/CollectDetailOverview.vue")
const CollectDetailStargazers = () => import("@/views/collects/collectDetail/CollectDetailStargazers.vue")
const Profile = () => import("@/views/Profile.vue")
const ProfileOverview = () => import("@/views/profile/ProfileOverview.vue")
const ProfileDetail = () => import("@/views/profile/ProfileDetail.vue")
const ProfileDetailOverview = () => import("@/views/profile/profileDetail/ProfileDetailOverview.vue")
const ProfileDetailCollects = () => import("@/views/profile/profileDetail/ProfileDetailCollects.vue")
const ProfileDetailCategories = () => import("@/views/profile/profileDetail/ProfileDetailCategories.vue")
const ProfileDetailHistory = () => import("@/views/profile/profileDetail/ProfileDetailHistory.vue")
const ProfileDetailNotices = () => import("@/views/profile/profileDetail/ProfileDetailNotices.vue")
const ProfileDetailStars = () => import("@/views/profile/profileDetail/ProfileDetailStars.vue")
const ProfileDetailFollowers = () => import("@/views/profile/profileDetail/ProfileDetailFollowers.vue")
const ProfileDetailFollowing = () => import("@/views/profile/profileDetail/ProfileDetailFollowing.vue")
const Search = () => import( "@/views/Search.vue")
const About = () => import("@/views/About.vue")

const Categories = () => import("@/views/Categories.vue")
const CategoryOverview = () => import("@/views/categories/CategoryOverview.vue")
const CategoryDetail = () => import("@/views/categories/CategoryDetail.vue")
const Tags = () => import("@/views/Tags.vue")
const TagOverview = () => import("@/views/tags/TagOverview.vue")
const TagDetail = () => import("@/views/tags/TagDetail.vue")
const Activate = () => import("@/views/Activate.vue")
const ResetPassword = () => import("@/views/ResetPassword.vue")
const Success = () => import("@/views/Success.vue")
const ActiveSuccess = () => import( "@/views/success/ActivateSuccess.vue")
const ResetPasswordSuccess = () => import( "@/views/success/ResetPasswordSuccess.vue")
const Error = () => import("@/views/Error.vue")
const Error403 = () => import("@/views/error/Error403.vue")
const Error404 = () => import("@/views/error/Error404.vue")
const Error500 = () => import("@/views/error/Error500.vue")
const Error501 = () => import("@/views/error/Error501.vue")

Vue.use(VueRouter)

//DONE 添加重定向到首页并进行对应操作的路由
//DONE 添加重定向到404的路由
const routes: RouteConfig[] = [
  {
    path: "/",
    component: Home
  },
  {
    path: "/login",
    redirect: {path: "./", query: {operation: "login"}}
  },
  {
    path: "/register",
    redirect: {path: "./", query: {operation: "register"}}
  },
  {
    path: "/logout",
    redirect: {path: "./", query: {operation: "logout"}}
  },
  {
    path: "/collects",
    component: Collects,
    children: [
      {path: "", component: CollectOverview},
      {
        path: ":id",
        component: CollectDetail,
        children: [
          {path: "", component: CollectDetailOverview},
          {path: "stargazers", component: CollectDetailStargazers}
        ]
      }
    ]
  },
  {
    path: "/profile",
    component: Profile,
    children: [
      {path: "", component: ProfileOverview},
      {
        path: ":id",
        component: ProfileDetail,
        children: [
          {path: "", component: ProfileDetailOverview},
          {path: "collects", component: ProfileDetailCollects},
          {path: "categories", component: ProfileDetailCategories},
          {path: "history", component: ProfileDetailHistory},
          {path: "notices", component: ProfileDetailNotices},
          {path: "stars", component: ProfileDetailStars},
          {path: "followers", component: ProfileDetailFollowers},
          {path: "following", component: ProfileDetailFollowing}
        ]
      }
    ]
  },
  {
    path: "/search",
    component: Search
  },
  {
    path: "/about",
    component: About
  },
  {
    path: "/categories",
    component: Categories,
    children: [
      {path: "", component: CategoryOverview},
      {path: ":id", component: CategoryDetail}
    ]
  },
  {
    path: "/tags",
    component: Tags,
    children: [
      {path: "", component: TagOverview},
      {path: ":id", component: TagDetail}
    ]
  },
  {
    path: "/activate",
    component: Activate
  },
  {
    path: "/resetPassword",
    component: ResetPassword
  },
  {
    path: "/success",
    component: Success,
    children: [
      {path: "activate", component: ActiveSuccess},
      {path: "resetPassword", component: ResetPasswordSuccess}
    ]
  },
  {
    path: "/error",
    component: Error,
    children: [
      {path: "403", component: Error403},
      {path: "404", component: Error404},
      {path: "500", component: Error500},
      {path: "501", component: Error501}
    ]
  }
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

export default router
