import http from "@/http"
import {Notice, Page, PageableParam} from "@/types"

const noticeUrl = "/notice"

//export async function create(notice: Notice) {
//  await http.post(`${noticeUrl}/create`, notice)
//}

export async function deleteById(id: number) {
  await http.delete(`${noticeUrl}/${id}`)
}

export async function deleteAllByUserId(userId: number) {
  const params = {userId}
  await http.delete(`${noticeUrl}/deleteAllByUserId`, {params})
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<Notice>>(`${noticeUrl}/findAllByUserId`, {params})
  return response.data
}
