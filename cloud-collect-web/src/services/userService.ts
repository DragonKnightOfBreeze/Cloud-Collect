import http from "@/http"
import {Collect, History, Notice, Page, PageableParam, Role, User} from "@/types"

const userUrl = "/user"

export async function modify(id: number, user: User) {
  await http.put(`${userUrl}/${id}`, user)
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

export async function findAllByRole(role: Role, pageableParam: PageableParam) {
  const params = {role, ...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/findAllByRole`, {params})
  return response.data
}

export async function isFollowed(id: number) {
  const response = await http.get<boolean>(`${userUrl}/${id}/isFollowed`)
  return response.data
}

export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Collect>>(`${userUrl}/${id}/collectPage`, {params})
  return response.data
}

export async function getPraiseToCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Collect>>(`${userUrl}/${id}/praiseToCollectPage`, {params})
  return response.data
}

export async function getHistoryPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<History>>(`${userUrl}/${id}/historyPage`, {params})
  return response.data
}

export async function getNoticePage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Notice>>(`${userUrl}/${id}/noticePage`, {params})
  return response.data
}

export async function getFollowToUserPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/${id}/followToUserPage`, {params})
  return response.data
}

export async function getFollowByUserPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<User>>(`${userUrl}/${id}/followByUserPage`, {params})
  return response.data
}
