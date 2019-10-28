import http from "@/http"

//NOTE 这里仅仅是发送邮件，是否可以不使用异步方法？

const emailUrl = "/email"

export function sendActiveEmail(activateCode: string) {
  const params = {activateCode}
  http.get(`${emailUrl}/sendActiveEmail`, {params})
}

export function sendHelloEmail() {
  http.get(`${emailUrl}/sendHelloEmail`)
}

export function sendResetPasswordEmail(resetPasswordCode: string) {
  const params = {resetPasswordCode}
  http.get(`${emailUrl}/sendResetPasswordEmail`, {params})
}

export function sendResetPasswordSuccessEmail() {
  http.get(`${emailUrl}/sendResetPasswordSuccessEmail`)
}
