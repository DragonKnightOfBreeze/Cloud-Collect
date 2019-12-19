import {CollectType, DataType, Enum, NoticeType} from "@/domain"

export const collectTypes: Enum<CollectType>[] = [
  {name: "NONE", text: "无"},
  {name: "LOVE", text: "喜欢"},
  {name: "IMPORTANT", text: "重要"},
  {name: "TODO", text: "待办"},
  {name: "DELAY", text: "搁置"}
]

export const noticeTypes: Enum<NoticeType>[] = [
  {name: "SYSTEM", text: "系统通知"},
  {name: "ACCOUNT", text: "账户通知"}
]

export const dataTypes: Enum<DataType>[] = [
  {name: "XML", text: "Xml"},
  {name: "JSON", text: "Json"},
  {name: "YAML", text: "Yaml"}
]
