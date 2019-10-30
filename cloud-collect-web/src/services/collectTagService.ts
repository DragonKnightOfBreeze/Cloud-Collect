import http from "@/http"
import {Collect, CollectTag, Page, PageableParam} from "@/types"

const collectTagUrl = "/collectTag"

export async function create(tag: CollectTag) {
  await http.post(`${collectTagUrl}/create`, tag)
}

export async function modify(id: number, tag: CollectTag) {
  await http.put(`${collectTagUrl}/${id}`, tag)
}

export async function deleteById(id: number) {
  await http.delete(`${collectTagUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<CollectTag>(`${collectTagUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<CollectTag>>(`${collectTagUrl}/findAll`, {params})
  return response.data
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<CollectTag>>(`${collectTagUrl}/findAllByUserId`, {params})
  return response.data
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageableParam: PageableParam) {
  const params = {userId, name, ...pageableParam}
  const response = await http.get<Page<CollectTag>>(`${collectTagUrl}/findAllByNameContainsAndUserId`, {params})
  return response.data
}


export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Collect>>(`${collectTagUrl}/${id}/collectPage`, {params})
  return response.data
}
