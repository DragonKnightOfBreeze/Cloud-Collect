import {Enum} from "@/domain"
import Vue from "vue"

//将枚举值转化成对应的枚举文本的过滤器
//typescript中枚举不能携带属性，且toString()方法默认返回数值而非字符串
Vue.filter("enumText", (value: string, arg1: Enum<any>[]) => {
  return arg1.find(v => v.name === value)!.text
})

//在后台使用@JsonFormat格式化时间
//Vue.filter("dateTime",(value:string)=>{
//  return value
//})
