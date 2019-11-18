import {CollectType, Enum, NoticeType} from "@/types"

export const collectTypes: Enum<CollectType>[] = [
  {name: "NONE", text: "无"},
  {name: "LOVE", text: "喜欢"},
  {name: "IMPORTANT", text: "重要"},
  {name: "TODO", text: "待办"},
  {name: "DELAY", text: "搁置"}
]

export const noticeTypes: Enum<NoticeType>[] = [
  {name: "ACCOUNT", text: "账户通知"},
  {name: "HELLO", text: "欢迎通知"},
  {name: "SYSTEM", text: "系统通知"}
]
