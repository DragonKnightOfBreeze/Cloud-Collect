import http from "@/http"
import {Collect, CollectType, Page, PageableParam, User} from "@/types"

const collect = "/collect"

export async function create(collect: Collect) {
  return await http.post<Collect>(`${collect}/create`, collect)
}

export async function createFrom(collect: Collect) {
  return await http.post<Collect>(`${collect}/createFrom`, collect)
}

export async function modify(id: number, collect: Collect) {
  await http.put(`${collect}/${id}`, collect)
}

export async function praise(id: number, collect: Collect) {
  await http.put(`${collect}/${id}/praise`, collect)
}

export async function deleteById(id: number) {
  await http.delete(`${collect}/${id}`)
}

export async function findById(id: number) {
  return await http.get<Collect>(`${collect}/${{id}}`)
}

export async function findAll(pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAll`, {params})
}

export async function findAllByNameContains(name: string, pageable: PageableParam) {
  const params = {name, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByNameContains`, {params})
}

export async function findAllByCategoryNameContains(categoryName: string, pageable: PageableParam) {
  const params = {categoryName, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByCategoryNameContains`, {params})
}

export async function findAllByTagNameContains(tagName: string, pageable: PageableParam) {
  const params = {tagName, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByTagNameContains`, {params})
}

export async function findAllByNameContainsAndUserId(name: string, userId: number, pageable: PageableParam) {
  const params = {name, userId, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByNameContainsAndUserId`, {params})
}

export async function findAllByCategoryNameContainsAndUserId(categoryName: string, userId: number, pageable: PageableParam) {
  const params = {categoryName, userId, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByCategoryNameContainsAndUserId`, {params})
}

export async function findAllByTagNameContainsAndUserId(tagName: string, userId: number, pageable: PageableParam) {
  const params = {tagName, userId, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByTagNameContainsAndUserId`, {params})
}

export async function findAllByCategoryId(categoryId: number, pageable: PageableParam) {
  const params = {categoryId, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByCategoryId`, {params})
}

export async function findAllByTypeAndUserId(type: CollectType, userId: number, pageable: PageableParam) {
  const params = {type, userId, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByTypeAndUserId`, {params})
}

export async function findAllByUserId(userId: number, pageable: PageableParam) {
  const params = {userId, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByUserId`, {params})
}


export async function getPraiseByUserPage(id: number, pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<User>>(`${collect}/${id}/praiseByUserPage`)
}

export async function getCommentPage(id: number, pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<Comment>>(`${collect}/${id}/commentPage`)
}
