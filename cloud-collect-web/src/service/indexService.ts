import http from "@/http"
import {Collect, LoginForm, ResetPasswordForm, User, UserDetailsVo} from "@/types"
import {Message} from "element-ui"

//NOTE 当发生错误时弹出element-ui信息框提示

export async function login(form: LoginForm) {
  try {
    return await http.post<UserDetailsVo>("/login", form)
  } catch (e) {
    Message.warning("登录失败。")
  }
}

export async function register(user: User) {
  try {
    return await http.post<User>("/register", user)
  } catch (e) {
    Message.warning("注册失败。")
  }
}

export async function activate(username: string, activateCode: string) {
  try {
    const params = {username, activateCode}
    return await http.post("/activate", undefined, {params})
  } catch (e) {
    Message.warning("激活失败。")
  }
}

export async function forgotPassword(username: string) {
  try {
    const params = {username}
    return await http.post("/forgotPassword", undefined, {params})
  } catch (e) {
    Message.warning("操作失败。")
  }
}

export async function resetPassword(form: ResetPasswordForm, resetPasswordCode: string) {
  try {
    const params = {resetPasswordCode}
    return await http.post("/resetPassword", form, {params})
  } catch (e) {
    Message.warning("重置密码失败。")
  }
}


export async function lookAroundCollect() {
  return await http.get<Collect>("/lookAroundCollect")
}

export async function lookAroundUser() {
  return await http.get<User>("/lookAroundUser")
}
