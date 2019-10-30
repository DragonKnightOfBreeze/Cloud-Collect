import http from "@/http"
import {Notice, Page, PageableParam} from "@/types"

const noticeUrl = "/notice"

export async function create(notice: Notice) {
  await http.post(`${noticeUrl}/create`, notice)
}

export async function read(id: number, notice: Notice) {
  await http.put(`${noticeUrl}/${id}/read`, notice)
}

export async function deleteById(id: number) {
  await http.delete(`${noticeUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<Notice>(`${noticeUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Notice>>(`${noticeUrl}/findAll`, {params})
  return response.data
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<Notice>>(`${noticeUrl}/findAllByUserId`, {params})
  return response.data
}

export async function findAllByUserIdAndRead(userId: number, readStatus: boolean, pageableParam: PageableParam) {
  const params = {userId, readStatus, ...pageableParam}
  const response = await http.get<Page<Notice>>(`${noticeUrl}/findAllByUserIdAndRead`, {params})
  return response.data
}
