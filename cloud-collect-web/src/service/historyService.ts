import http from "@/http"
import {History, Page, PageableParam} from "@/types"

const historyUrl = "/history"

export async function create(history: History) {
  return await http.post(`${historyUrl}/create`, history)
}

export async function deleteById(id: number) {
  return await http.delete(`${historyUrl}/${id}`)
}

export async function deleteAllByUserId(userId: number) {
  const params = {userId}
  return await http.delete(`${historyUrl}/deleteAllByUserId`, {params})
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  return await http.get<Page<History>>(`${historyUrl}/findAllByUserId`, {params})
}
