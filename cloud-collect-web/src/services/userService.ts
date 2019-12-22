import {Page, PageableParam, Role, User} from "@/domain"
import http from "@/http"

const userUrl = "/user"

export async function modify(id: number, user: User) {
  await http.put(`${userUrl}/${id}`, user)
}

export async function uploadAvatar(id: number, file: File) {
  //传入formData作为参数，其中添加名字正确的文件
  //不要忘记在请求头中配置Content-Type
  const formData = new FormData()
  formData.append("multipartFile", file)
  const response = await http.post<string>(`${userUrl}/${id}/uploadAvatar`, formData, {
    headers: {"Content-Type": "multipart/form-data"}
  })
  return response.data
}

export async function follow(id: number) {
  await http.put(`${userUrl}/${id}/follow`)
}

export async function unfollow(id: number) {
  await http.put(`${userUrl}/${id}/unfollow`)
}

export async function findById(id: number) {
  const response = await http.get<User>(`${userUrl}/${id}`)
  return response.data
}

export async function findByUsername(username: string) {
  const response = await http.get<User>(`${userUrl}/u/${username}`)
  return response.data
}

export async function findByEmail(email: string) {
  const response = await http.get<User>(`${userUrl}/e/${email}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAll`, {params})
  return response.data
}

export async function findAllByNicknameContains(nickname: string, pageableParam: PageableParam) {
  const params = {nickname, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByNicknameContains`, {params})
  return response.data
}

export async function findAllByUsernameContains(username: string, pageableParam: PageableParam) {
  const params = {username, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByUsernameContains`, {params})
  return response.data
}

export async function findAllByEmailContains(email: string, pageableParam: PageableParam) {
  const params = {email, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByEmailContains`, {params})
  return response.data
}

export async function findAllByRole(role: Role, pageableParam: PageableParam) {
  const params = {role, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByRole`, {params})
  return response.data
}

export async function findAllByFollowToUserId(followToUserId: number, pageableParam: PageableParam) {
  const params = {followToUserId, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByFollowToUserId`, {params})
  return response.data
}

export async function findAllByNicknameContainsAndFollowToUserId(nickname: string, followToUserId: number, pageableParam: PageableParam) {
  const params = {nickname, followToUserId, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByNicknameContainsAndFollowToUserId`, {params})
  return response.data
}

export async function findAllByFollowByUserId(followByUserId: number, pageableParam: PageableParam) {
  const params = {followByUserId, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByFollowByUserId`, {params})
  return response.data
}

export async function findAllByNicknameContainsAndFollowByUserId(nickname: string, followByUserId: number, pageableParam: PageableParam) {
  const params = {nickname, followByUserId, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByNicknameContainsAndFollowByUserId`, {params})
  return response.data
}

export async function findAllByPraiseToCollectId(praiseToCollectId: number, pageableParam: PageableParam) {
  const params = {praiseToCollectId, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByPraiseToCollectId`, {params})
  return response.data
}
