import http from "@/http"
import {Collect, CollectCategory, Page, PageableParam} from "@/types"

const collectCategoryUrl = "/collectCategory"

export async function create(category: CollectCategory) {
  return await http.post(`${collectCategoryUrl}/create`, category)
}

export async function modify(id: number, category: CollectCategory) {
  return await http.put(`${collectCategoryUrl}/${id}`, category)
}

export async function deleteById(id: number) {
  return await http.delete(`${collectCategoryUrl}/${id}`)
}

export async function findById(id: number) {
  return await http.get<CollectCategory>(`${collectCategoryUrl}/${id}`)
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<CollectCategory>>(`${collectCategoryUrl}/findAll`, {params})
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  return await http.get<Page<CollectCategory>>(`${collectCategoryUrl}/findAllByUserId`, {params})
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageableParam: PageableParam) {
  const params = {userId, name, ...pageableParam}
  return await http.get<Page<CollectCategory>>(`${collectCategoryUrl}/findAllByNameContainsAndUserId`, {params})
}


export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Collect>>(`${collectCategoryUrl}/${id}/collectPage`, {params})
}
