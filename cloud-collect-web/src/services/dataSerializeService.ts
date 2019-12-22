import http from "@/http"

const dataSerializeUrl = "/dataSerialize"

export async function importData(dataType: string, file: File) {
  //传入formData作为参数，其中添加名字正确的文件
  //不要忘记在请求头中配置Content-Type
  const formData = new FormData()
  formData.append("multipartFile", file)
  await http.post(`${dataSerializeUrl}/importData`, formData, {
    params: {dataType},
    headers: {"Content-Type": "multipart/form-data"}
  })
}

export async function exportData(dataType: string) {
  //响应类型为blob
  //后台返回的responseBody是一个byte[]、File、Resource对象
  //前端接受到的response.data是一个Blob对象
  //将返回的Blob对象作为参数构造ObjectURL，然后创造一个不显示且立即点击的链接，从而达到自动下载文件的效果
  const response = await http.get(`${dataSerializeUrl}/exportData`, {
    params: {dataType},
    responseType: "blob"
  })
  return response.data
}
