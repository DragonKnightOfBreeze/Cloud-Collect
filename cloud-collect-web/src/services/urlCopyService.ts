import http from "@/http"
import {Collect} from "@/types"

const urlCopyUrl = "/urlCopy"

export async function copyAsUrl(type: string, collect: Collect) {
  const response = await http.post<string>(`${urlCopyUrl}/${type}`, collect)
  return response.data
}
