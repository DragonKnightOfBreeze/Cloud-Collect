import http from "@/http"
import {Collect, CollectType, Page, PageableParam, User} from "@/types"

const collectUrl = "/collect"

export async function create(collect: Collect) {
  return await http.post<Collect>(`${collectUrl}/create`, collect)
}

export async function createFrom(collect: Collect) {
  return await http.post(`${collectUrl}/createFrom`, collect)
}

export async function modify(id: number, collect: Collect) {
  return await http.put(`${collectUrl}/${id}`, collect)
}

export async function praise(id: number, collect: Collect) {
  return await http.put(`${collectUrl}/${id}/praise`, collect)
}

export async function deleteById(id: number) {
  return await http.delete(`${collectUrl}/${id}`)
}

export async function findById(id: number) {
  return await http.get<Collect>(`${collectUrl}/${{id}}`)
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAll`, {params})
}

export async function findAllByNameContains(name: string, pageableParam: PageableParam) {
  const params = {name, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByNameContains`, {params})
}

export async function findAllByCategoryNameContains(categoryName: string, pageableParam: PageableParam) {
  const params = {categoryName, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByCategoryNameContains`, {params})
}

export async function findAllByTagNameContains(tagName: string, pageableParam: PageableParam) {
  const params = {tagName, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByTagNameContains`, {params})
}

export async function findAllByNameContainsAndUserId(name: string, userId: number, pageableParam: PageableParam) {
  const params = {name, userId, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByNameContainsAndUserId`, {params})
}

export async function findAllByCategoryNameContainsAndUserId(categoryName: string, userId: number, pageableParam: PageableParam) {
  const params = {categoryName, userId, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByCategoryNameContainsAndUserId`, {params})
}

export async function findAllByTagNameContainsAndUserId(tagName: string, userId: number, pageableParam: PageableParam) {
  const params = {tagName, userId, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByTagNameContainsAndUserId`, {params})
}

export async function findAllByCategoryId(categoryId: number, pageableParam: PageableParam) {
  const params = {categoryId, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByCategoryId`, {params})
}

export async function findAllByTypeAndUserId(type: CollectType, userId: number, pageableParam: PageableParam) {
  const params = {type, userId, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByTypeAndUserId`, {params})
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  return await http.get<Page<Collect>>(`${collectUrl}/findAllByUserId`, {params})
}


export async function isPraised(id: number) {
  return await http.get<boolean>(`${collectUrl}/${id}/isPraised`)
}

export async function getPraiseByUserPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<User>>(`${collectUrl}/${id}/praiseByUserPage`)
}

export async function getCommentPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Comment>>(`${collectUrl}/${id}/commentPage`)
}
