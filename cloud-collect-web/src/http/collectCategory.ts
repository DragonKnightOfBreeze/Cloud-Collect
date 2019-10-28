import http from "@/http"
import {Collect, CollectCategory, Page, PageableParam} from "@/types"

const collectCategory = "/collectCategory"

export async function create(category: CollectCategory) {
  return await http.post<CollectCategory>(`${collectCategory}/create`, category)
}

export async function modify(id: number, category: CollectCategory) {
  await http.put(`${collectCategory}/${id}`, category)
}

export async function deleteById(id: number) {
  await http.delete(`${collectCategory}/${id}`)
}

export async function findById(id: number) {
  return await http.get<CollectCategory>(`${collectCategory}/${id}`)
}

export async function findAll(pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<CollectCategory>>(collectCategory, {params})
}

export async function findAllByUserId(userId: number, pageable: PageableParam) {
  const params = {userId, ...pageable}
  return await http.get<Page<CollectCategory>>(`${collectCategory}/findAllByUserId`, {params})
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageable: PageableParam) {
  const params = {userId, name, ...pageable}
  return await http.get<Page<CollectCategory>>(`${collectCategory}/findAllByNameContainsAndUserId`, {params})
}

export async function getCollectPage(id: number, pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<Collect>>(`${collectCategory}/${id}/collectPage`, {params})
}
