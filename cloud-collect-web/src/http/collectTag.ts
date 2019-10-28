import http from "@/http"
import {Collect, CollectTag, Page, PageableParam} from "@/types"

const collectTag = "/collectTag"

export async function create(tag: CollectTag) {
  return await http.post<CollectTag>(`${collectTag}/create`, tag)
}

export async function modify(id: number, tag: CollectTag) {
  await http.put(`${collectTag}/${id}`, tag)
}

export async function deleteById(id: number) {
  await http.delete(`${collectTag}/${id}`)
}

export async function findById(id: number) {
  return await http.get<CollectTag>(`${collectTag}/${id}`)
}

export async function findAll(pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<CollectTag>>(collectTag, {params})
}

export async function findAllByUserId(userId: number, pageable: PageableParam) {
  const params = {userId, ...pageable}
  return await http.get<Page<CollectTag>>(`${collectTag}/findAllByUserId`, {params})
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageable: PageableParam) {
  const params = {userId, name, ...pageable}
  return await http.get<Page<CollectTag>>(`${collectTag}/findAllByNameContainsAndUserId`, {params})
}


export async function getCollectPage(id: number, pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<Collect>>(`${collectTag}/${id}/collectPage`, {params})
}
