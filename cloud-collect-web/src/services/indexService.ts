import http from "@/http"
import {Collect, LoginForm, ResetPasswordForm, User, UserDetailsVo} from "@/types"

//NOTE 当发生错误时弹出element-ui信息框提示

export async function login(form: LoginForm) {
  const response = await http.post<UserDetailsVo>("/login", form)
  return response.data
}

export async function register(user: User) {
  const response = await http.post<User>("/register", user)
  return response.data
}

export async function activate(username: string, activateCode: string) {
  const params = {username, activateCode}
  const response = await http.post("/activate", undefined, {params})
  return response.data
}

export async function forgotPassword(email: string) {
  const params = {email}
  const response = await http.post("/forgotPassword", undefined, {params})
  return response.data
}

export async function resetPassword(form: ResetPasswordForm, resetPasswordCode: string) {
  const params = {resetPasswordCode}
  const response = await http.post("/resetPassword", form, {params})
  return response.data
}


export async function lookAroundCollect() {
  const response = await http.get<Collect>("/lookAroundCollect")
  return response.data
}

export async function lookAroundUser() {
  const response = await http.get<User>("/lookAroundUser")
  return response.data
}
