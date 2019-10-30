import http from "@/http"
import {Page, PageableParam} from "@/types"

const commentUrl = "/comment"

export async function create(comment: Comment) {
  await http.post(`${commentUrl}/create`, comment)
}

export async function reply(replyToCommentId: number, comment: Comment) {
  const params = {replyToCommentId}
  await http.post(`${commentUrl}/reply`, comment, {params})
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

export async function findByCollectId(collectId: number, pageableParam: PageableParam) {
  const params = {collectId, ...pageableParam}
  const response = await http.get<Page<Comment>>(`${commentUrl}/findByCollectId`, {params})
  return response.data
}


export async function getReplyByCommentPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Comment>>(`${commentUrl}/${id}/replyByCommentPage`, {params})
  return response.data
}

