import Vue from "vue"

const enumText: { [enumName: string]: { [enumConst: string]: string } } = {
  CollectPrivacy: {
    PUBLIC: "公共的",
    PRIVATE: "私有的"
  },
  CollectType: {
    NONE: "无",
    LOVE: "喜欢",
    IMPORT: "重要",
    TODO: "待办",
    DELAY: "搁置"
  },
  NoticeType: {
    SYSTEM: "系统通知",
    ACCOUNT: "账户通知",
    HELLO: "欢迎通知"
  },
  Role: {
    NORMAL: "普通用户",
    ADMIN: "管理员"
  }
}

//将枚举值转化成对应的枚举文本的过滤器
//typescript中枚举不能携带属性，且toString()方法默认返回数值而非字符串
//因此这里使用字符串字面量类型和嵌套对象来实现此功能
Vue.filter("enumText", (value: string, type: string) => {
  return enumText[type][value]
})

Vue.filter("enumValues", (value: any) => {
  return Object.values(value)
})

//NOTE 在后台使用@JsonFormat格式化时间
//Vue.filter("dateTime",(value:string)=>{
//  return value
//})

//Vue.filter("categoryName", (value: Category): string => {
//  return value && value.name || "未分类"
//})

//Vue.filter("tagNames", (value: Tag[]): string[] => {
//  const limit = 4
//  const names = value.map(tag => tag.name)
//  if (names.length <= limit) {
//    return names
//  } else {
//    return names.slice(0, limit - 1).concat("...")
//  }
//})
