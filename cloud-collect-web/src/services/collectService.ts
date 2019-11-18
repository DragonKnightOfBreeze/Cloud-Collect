import http from "@/http"
import {Collect, CollectType, Comment, Page, PageableParam, User} from "@/types"

const collectUrl = "/collect"

export async function create(collect: Collect) {
  await http.post(`${collectUrl}/create`, collect)
}

export async function createFrom(collect: Collect) {
  await http.post(`${collectUrl}/createFrom`, collect)
}

export async function modify(id: number, collect: Collect) {
  await http.put(`${collectUrl}/${id}`, collect)
}

export async function praise(id: number) {
  await http.put(`${collectUrl}/${id}/praise`)
}

export async function unpraise(id: number) {
  await http.put(`${collectUrl}/${id}/unpraise`)
}

export async function deleteById(id: number) {
  await http.delete(`${collectUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<Collect>(`${collectUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAll`, {params})
  return response.data
}

export async function findAllByNameContains(name: string, pageableParam: PageableParam) {
  const params = {name, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByNameContains`, {params})
  return response.data
}

export async function findAllByCategoryNameContains(categoryName: string, pageableParam: PageableParam) {
  const params = {categoryName, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByCategoryNameContains`, {params})
  return response.data
}

export async function findAllByTagNameContains(tagName: string, pageableParam: PageableParam) {
  const params = {tagName, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByTagNameContains`, {params})
  return response.data
}

export async function findAllByNameContainsAndUserId(name: string, userId: number, pageableParam: PageableParam) {
  const params = {name, userId, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByNameContainsAndUserId`, {params})
  return response.data
}

export async function findAllByCategoryNameContainsAndUserId(categoryName: string, userId: number, pageableParam: PageableParam) {
  const params = {categoryName, userId, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByCategoryNameContainsAndUserId`, {params})
  return response.data
}

export async function findAllByTagNameContainsAndUserId(tagName: string, userId: number, pageableParam: PageableParam) {
  const params = {tagName, userId, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByTagNameContainsAndUserId`, {params})
  return response.data
}

export async function findAllByCategoryId(categoryId: number, pageableParam: PageableParam) {
  const params = {categoryId, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByCategoryId`, {params})
  return response.data
}

export async function findAllByTypeAndUserId(type: CollectType, userId: number, pageableParam: PageableParam) {
  const params = {type, userId, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByTypeAndUserId`, {params})
  return response.data
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectUrl}/findAllByUserId`, {params})
  return response.data
}

export async function isPraised(id: number) {
  const response = await http.get<boolean>(`${collectUrl}/${id}/isPraised`)
  return response.data
}

export async function getPraiseByUserPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<User>>(`${collectUrl}/${id}/praiseByUserPage`)
  return response.data
}

export async function getCommentPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Comment>>(`${collectUrl}/${id}/commentPage`)
  return response.data
}
