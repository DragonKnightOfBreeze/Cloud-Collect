import http from "@/http"
import {Page, PageableParam} from "@/types"

const commentUrl = "/comment"

export async function create(comment: Comment) {
  return await http.post(`${commentUrl}/create`, comment)
}

export async function reply(replyToCommentId: number, comment: Comment) {
  const params = {replyToCommentId}
  return await http.post(`${commentUrl}/reply`, comment, {params})
}

export async function deleteById(id: number) {
  return await http.delete(`${commentUrl}/${id}`)
}

export async function findById(id: number) {
  return await http.get<Comment>(`${commentUrl}/${id}`)
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Comment>>(`${commentUrl}/findAll`, {params})
}

export async function findByCollectId(collectId: number, pageableParam: PageableParam) {
  const params = {collectId, ...pageableParam}
  return await http.get<Page<Comment>>(`${commentUrl}/findByCollectId`, {params})
}


export async function getReplyByCommentPage(id: number, pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Comment>>(`${commentUrl}/${id}/replyByCommentPage`, {params})
}

