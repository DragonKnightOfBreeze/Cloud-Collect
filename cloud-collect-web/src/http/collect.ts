import http from "@/http"
import {Collect, Page, PageableParam} from "@/types"

const collect = "/collect"

export async function create(collect: Collect) {
  return await http.post<Collect>(`${collect}/create`, collect)
}

export async function createFrom(collect: Collect) {
  return await http.post<Collect>(`${collect}/createFrom`, collect)
}

export async function modify(id: number, collect: Collect) {
  await http.put(`${collect}/${id}`, collect)
}

export async function praise(id: number, collect: Collect) {
  await http.put(`${collect}/${id}/praise`, collect)
}

export async function deleteById(id: number) {
  await http.delete(`${collect}/${id}`)
}

export async function findById(id: number) {
  const params = {id}
  return await http.get<Collect>(`${collect}/${{id}}`, {params})
}

export async function findAll(pageable: PageableParam) {
  const params = {...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAll`, {params})
}

export async function findAllByNameContains(name: string, pageable: PageableParam) {
  const params = {name, ...pageable}
  return await http.get<Page<Collect>>(`${collect}/findAllByNameContains`, {params})
}
