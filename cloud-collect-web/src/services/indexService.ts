import {Collect, LoginForm, ResetPasswordForm, User, UserDetailsVo} from "@/domain"
import http from "@/http"

//NOTE 当发生错误时弹出element-ui信息框提示

export async function login(form: LoginForm) {
  const response = await http.post<UserDetailsVo>("/login", form)
  return response.data
}

export async function register(user: User) {
  await http.post("/register", user)
}

export async function generateToken(username: string) {
  const params = {username}
  const response = await http.get<string>("/generateToken", {params})
  return response.data
}

export async function logout() {
  await http.post("/logout")
}

export async function activate(username: string, activateCode: string) {
  const params = {username, activateCode}
  await http.post("/activate", undefined, {params})
}

export async function forgotPassword(email: string) {
  const params = {email}
  await http.post("/forgotPassword", undefined, {params})
}

export async function resetPassword(form: ResetPasswordForm, resetPasswordCode: string) {
  const params = {resetPasswordCode}
  await http.post("/resetPassword", form, {params})
}

export async function lookAroundCollect() {
  const response = await http.get<Collect>("/lookAroundCollect")
  return response.data
}

export async function lookAroundUser() {
  const response = await http.get<User>("/lookAroundUser")
  return response.data
}
