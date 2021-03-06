import {Comment, Page, PageableParam} from "@/domain"
import http from "@/http"

const commentUrl = "/comment"

export async function create(comment: Comment) {
  await http.post(`${commentUrl}/create`, comment)
}

export async function reply(comment: Comment) {
  await http.post(`${commentUrl}/reply`, comment)
}

export async function deleteById(id: number) {
  await http.delete(`${commentUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<Comment>(`${commentUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Comment>>(`${commentUrl}/findAll`, {params})
  return response.data
}

export async function findAllByCollectId(collectId: number, pageableParam: PageableParam) {
  const params = {collectId, ...pageableParam}
  const response = await http.get<Page<Comment>>(`${commentUrl}/findAllByCollectId`, {params})
  return response.data
}

