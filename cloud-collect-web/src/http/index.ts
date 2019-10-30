import router from "@/router"
import store from "@/store"
import {ObjectError} from "@/types"
import axios from "axios"

//不要直接使用axios，使用新的实例
const http = axios.create({
  baseURL: "/cloudCollect/api",
  timeout: 36000
})

//REGION 配置拦截器

http.interceptors.request.use(value => {
  //TODO 可能需要设置额外的请求头
  console.log("Request success.")
  return value
}, error => {
  console.error("Request error: ", error)
  Promise.reject(error)
})

http.interceptors.response.use(value => {
  console.log("Response success.")
  return value
}, error => {
  //error: {request} | {response} | {message}
  if (error.response) {
    const status = error.response.status
    switch (status) {
      case 400:
        //如果为参数验证的错误
        if (error.response.data.validationErrors) {
          const validationErrors = error.response.data.validationErrors as ObjectError[]
          store.commit("addValidationErrors", validationErrors)
        }
        break
      case 401:
        //要求用户登录
        router.push({path: "/", params: {operation: "login"}})
        break
      case 403:
        router.push("/error/403")
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