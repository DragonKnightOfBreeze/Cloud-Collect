import {User} from "@/types"
import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)

//NOTE 不要使用解构语法，为了完整的代码提示
//NOTE vuex的Store定义并不能很好地结合typescript

export default new Vuex.Store({
  state: {
    //当前用户
    _currentUser: null,
    //当前用户的jwt令牌
    _jwtToken: "",
    //最后浏览的收藏
    //_lastViewedCollect: null,
    //参数校验的错误列表
    //_validationErrors: [],
    //参数校验的首条消息
    _validationMessage: ""
  },
  getters: {
    currentUser(state): User | null {
      return state._currentUser as User | null
    },
    jwtToken(state): string {
      return state._jwtToken
    },
    //lastViewedCollect(state): Collect | null {
    //  return state._lastViewedCollect as Collect | null
    //},
    //validationErrors(state): ObjectError[] {
    //  const result = state._validationErrors
    //  //每次读取错误列表时，需要同时清空所有数据
    //  state._validationErrors = []
    //  return result
    //},
    validationMessage(state): string {
      const result = state._validationMessage
      //每次读取时需要清空消息
      state._validationMessage = ""
      return result
    }
  },
  mutations: {
    setCurrentUser(state, currentUser: User) {
      state._currentUser = currentUser as any
    },
    setJwtToken(state, jwtToken: string) {
      state._jwtToken = jwtToken
    },
    //setLastViewedCollect(state, lastViewedCollect: Collect) {
    //  state._lastViewedCollect = lastViewedCollect as any
    //},
    //setValidationErrors(state, validationErrors: ObjectError[]) {
    //  state._validationErrors.push(...validationErrors as [])
    //},
    setValidationMessage(state, validationMessage: string) {
      state._validationMessage = validationMessage
    }
  },
  actions: {},
  modules: {}
})
