import http from "@/http"
import {Collect, Page, PageableParam, Tag} from "@/types"

const tagUrl = "/tag"

export async function create(tag: Tag) {
  await http.post(`${tagUrl}/create`, tag)
}

export async function modify(id: number, tag: Tag) {
  await http.put(`${tagUrl}/${id}`, tag)
}

export async function deleteById(id: number) {
  await http.delete(`${tagUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<Tag>(`${tagUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Tag>>(`${tagUrl}/findAll`, {params})
  return response.data
}

export async function findAllByNameContains(name: string, pageableParam: PageableParam) {
  const params = {name, ...pageableParam}
  const response = await http.get<Page<Tag>>(`${tagUrl}/findAllByNameContains`, {params})
  return response.data
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<Tag>>(`${tagUrl}/findAllByUserId`, {params})
  return response.data
}

export async function findAllByNameContainsAndUserId(userId: number, name: string, pageableParam: PageableParam) {
  const params = {userId, name, ...pageableParam}
  const response = await http.get<Page<Tag>>(`${tagUrl}/findAllByNameContainsAndUserId`, {params})
  return response.data
}


export async function getCollectPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Collect>>(`${tagUrl}/${id}/collectPage`, {params})
  return response.data
}