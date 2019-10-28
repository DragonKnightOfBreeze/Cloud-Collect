import http from "@/http"
import {Page, PageableParam, Role, User} from "@/types"

const userUrl = "/user"

export async function modify(id: number, user: User) {
  return await http.put(`${userUrl}/${id}`, user)
}

export async function findById(id: number) {
  return await http.get<User>(`${userUrl}/${id}`)
}

export async function findByUsername(username: string) {
  return await http.get<User>(`${userUrl}/u/${username}`)
}

export async function findByEmail(email: string) {
  return await http.get<User>(`${userUrl}/e/${email}`)
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/findAll`, {params})
}

export async function findAllByNicknameContains(nickname: string, pageableParam: PageableParam) {
  const params = {nickname, ...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/findAllByNicknameContains`, {params})
}

export async function findAllByRole(role: Role, pageableParam: PageableParam) {
  const params = {role, ...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/findAllByRole`, {params})
}


export async function getFollowToUserPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/${id}/followToUserPage`, {params})
}

export async function getFollowByUserPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/${id}/followByUserPage`, {params})
}

export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/${id}/collectPage`, {params})
}

export async function getCommentPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/${id}/commentPage`, {params})
}

export async function getNoticePage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${userUrl}/${id}/noticePage`, {params})
}
