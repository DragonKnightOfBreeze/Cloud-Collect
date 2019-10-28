import http from "@/http"
import {Collect, Pair} from "@/types"

//NOTE 是否可考虑直接在前端实现这一功能？

const urlCopyUrl = "/urlCopy"

export async function copyAsHtmlUrl(collect: Collect) {
  return await http.post<string>(`${urlCopyUrl}/copyAsHtmlUrl`, collect)
}

export async function copyAsMdUrl(collect: Collect) {
  return await http.post<string>(`${urlCopyUrl}/copyAsMdUrl`, collect)
}

export async function copyAsMdRefUrl(collect: Collect) {
  return await http.post<Pair<string, string>>(`${urlCopyUrl}/copyAsMdRefUrl`, collect)
}

export async function copyAsMdImgUrl(collect: Collect) {
  return await http.post<string>(`${urlCopyUrl}/copyAsMdImgUrl`, collect)
}

export async function copyAsMdImgRefUrl(collect: Collect) {
  return await http.post<Pair<string, string>>(`${urlCopyUrl}/copyAsMdImgRefUrl`, collect)
}
