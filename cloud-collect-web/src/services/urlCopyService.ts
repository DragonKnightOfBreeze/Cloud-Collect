import {Collect, Url} from "@/domain"
import http from "@/http"

const urlCopyUrl = "/urlCopy"

export async function copyAsUrl(type: string, collect: Collect) {
  const response = await http.post<Url>(`${urlCopyUrl}/${type}`, collect)
  return response.data.url
}
