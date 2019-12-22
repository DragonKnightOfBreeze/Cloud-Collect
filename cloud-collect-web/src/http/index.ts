import {Message} from "@/domain"
import router from "@/router"
import store from "@/store"
import axios from "axios"

export const rootUrl = "http://localhost:8080/cloudCollect/api"

//不要直接使用axios，使用新的实例
const http = axios.create({
  baseURL: rootUrl,
  timeout: 3600000,
  headers: {
    "Access-Control-Allow-Origin": "*"
  }
})

//上传文件时需要一个纯净的formData
export const httpFormData = axios.create({
  baseURL: "http://localhost:8080/cloudCollect/api",
  timeout: 3600000
})

//REGION 配置拦截器

http.interceptors.request.use(value => {
  //如果存在当前用户且存在jwt令牌，则将jwt令牌存储在请求头中
  if (!!store.getters.currentUser && !!(store.getters.jwtToken as string)) {
    value.headers["Authorization"] = `Bearer ${store.getters.jwtToken}`
  }
  console.log(`Request success:`, value)
  return value
}, error => {
  console.error("Request with error:", error)
  Promise.reject(error)
})

http.interceptors.response.use(value => {
  console.log("Response success:", value)
  return value
}, error => {
  console.error("Error:", error)
  //error: {request} | {response} | {message}
  if (error.response) {
    console.error("Response with error:", error.response)
    const status = error.response.status
    switch (status) {
      case 400:
        //取得错误消息并存储到store中
        const errorMessage = (error.response.data as Message).message
        console.error("Error message:", errorMessage)
        store.commit("setErrorMessage", errorMessage)
        break
      case 401:
        //要求用户登录
        router.push("/login")
        break
      case 404:
        router.push("/error/404")
        break
      case 500:
        router.push("/error/500")
        break
      case 501:
        router.push("/error/501")
        break
      default:
        router.push("/error/500")
        break
    }
  }
  return Promise.reject(error)
})

export default http
