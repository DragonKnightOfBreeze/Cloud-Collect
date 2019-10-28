import http from "@/http"
import {Collect, CollectTag, Page, PageableParam} from "@/types"

const collectTagUrl = "/collectTag"

export async function create(tag: CollectTag) {
  return await http.post(`${collectTagUrl}/create`, tag)
}

export async function modify(id: number, tag: CollectTag) {
  return await http.put(`${collectTagUrl}/${id}`, tag)
}

export async function deleteById(id: number) {
  return await http.delete(`${collectTagUrl}/${id}`)
}

export async function findById(id: number) {
  return await http.get<CollectTag>(`${collectTagUrl}/${id}`)
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<CollectTag>>(`${collectTagUrl}/findAll`, {params})
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  return await http.get<Page<CollectTag>>(`${collectTagUrl}/findAllByUserId`, {params})
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageableParam: PageableParam) {
  const params = {userId, name, ...pageableParam}
  return await http.get<Page<CollectTag>>(`${collectTagUrl}/findAllByNameContainsAndUserId`, {params})
}


export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Collect>>(`${collectTagUrl}/${id}/collectPage`, {params})
}
