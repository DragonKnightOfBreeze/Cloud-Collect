import {Category, Page, PageableParam} from "@/domain"
import http from "@/http"

const categoryUrl = "/category"

export async function create(category: Category) {
  await http.post(`${categoryUrl}/create`, category)
}

export async function modify(id: number, category: Category) {
  await http.put(`${categoryUrl}/${id}`, category)
}

export async function deleteById(id: number) {
  await http.delete(`${categoryUrl}/${id}`)
}

export async function findById(id: number) {
  const response = await http.get<Category>(`${categoryUrl}/${id}`)
  return response.data
}

export async function findAll(pageableParam: PageableParam) {
  const params = {...pageableParam}
  const response = await http.get<Page<Category>>(`${categoryUrl}/findAll`, {params})
  return response.data
}

export async function findAllByNameContains(name: string, pageableParam: PageableParam) {
  const params = {name, ...pageableParam}
  const response = await http.get<Page<Category>>(`${categoryUrl}/findAllByNameContains`, {params})
  return response.data
}

export async function findAllByUserId(userId: number, pageableParam: PageableParam) {
  const params = {userId, ...pageableParam}
  const response = await http.get<Page<Category>>(`${categoryUrl}/findAllByUserId`, {params})
  return response.data
}

export async function findAllByNameContainsAndUserId(name: string, userId: number, pageableParam: PageableParam) {
  const params = {name, userId, ...pageableParam}
  const response = await http.get<Page<Category>>(`${categoryUrl}/findAllByNameContainsAndUserId`, {params})
  return response.data
}
