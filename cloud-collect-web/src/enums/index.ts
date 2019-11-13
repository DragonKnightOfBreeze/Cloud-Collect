import {CollectType, Enum} from "@/types"

export const collectTypes: Enum<CollectType>[] = [
  {name: "NONE", text: "无"},
  {name: "LOVE", text: "喜欢"},
  {name: "IMPORT", text: "重要"},
  {name: "TODO", text: "待办"},
  {name: "DELAY", text: "搁置"}
]
