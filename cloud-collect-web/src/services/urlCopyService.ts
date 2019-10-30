import http from "@/http"
import {Collect, Pair} from "@/types"

//NOTE 是否可考虑直接在前端实现这一功能？

const urlCopyUrl = "/urlCopy"

export async function copyAsHtmlUrl(collect: Collect) {
  const response = await http.post<string>(`${urlCopyUrl}/copyAsHtmlUrl`, collect)
  return response.data
}

export async function copyAsMdUrl(collect: Collect) {
  const response = await http.post<string>(`${urlCopyUrl}/copyAsMdUrl`, collect)
  return response.data
}

export async function copyAsMdRefUrl(collect: Collect) {
  const response = await http.post<Pair<string, string>>(`${urlCopyUrl}/copyAsMdRefUrl`, collect)
  return response.data
}

export async function copyAsMdImgUrl(collect: Collect) {
  const response = await http.post<string>(`${urlCopyUrl}/copyAsMdImgUrl`, collect)
  return response.data
}

export async function copyAsMdImgRefUrl(collect: Collect) {
  const response = await http.post<Pair<string, string>>(`${urlCopyUrl}/copyAsMdImgRefUrl`, collect)
  return response.data
}
