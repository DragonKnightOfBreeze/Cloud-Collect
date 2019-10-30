import http from "@/http"
import {Collect, CollectCategory, Page, PageableParam} from "@/types"

const collectCategoryUrl = "/collectCategory"

export async function create(category: CollectCategory) {
  await http.post(`${collectCategoryUrl}/create`, category)
}

export async function modify(id: number, category: CollectCategory) {
  await http.put(`${collectCategoryUrl}/${id}`, category)
}

export async function deleteById(id: number) {
  await http.delete(`${collectCategoryUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<CollectCategory>(`${collectCategoryUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<CollectCategory>>(`${collectCategoryUrl}/findAll`, {params})
  return response.data
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<CollectCategory>>(`${collectCategoryUrl}/findAllByUserId`, {params})
  return response.data
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageableParam: PageableParam) {
  const params = {userId, name, ...pageableParam}
  const response = await http.get<Page<CollectCategory>>(`${collectCategoryUrl}/findAllByNameContainsAndUserId`, {params})
  return response.data
}


export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectCategoryUrl}/${id}/collectPage`, {params})
  return response.data
}
