import http from "@/http"
import {Notice, Page, PageableParam} from "@/types"

const noticeUrl = "/notice"

export async function create(notice: Notice) {
  return await http.post(`${noticeUrl}/create`, notice)
}

export async function read(id: number, notice: Notice) {
  return await http.put(`${noticeUrl}/${id}/read`, notice)
}

export async function deleteById(id: number) {
  return await http.delete(`${noticeUrl}/${id}`)
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  return await http.get<Page<Notice>>(`${noticeUrl}/findAll`, {params})
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  return await http.get<Page<Notice>>(`${noticeUrl}/findAllByUserId`, {params})
}

export async function findAllByUserIdAndRead(userId: number, readStatus: boolean, pageableParam: PageableParam) {
  const params = {userId, readStatus, ...pageableParam}
  return await http.get<Page<Notice>>(`${noticeUrl}/findAllByUserIdAndRead`, {params})
}
