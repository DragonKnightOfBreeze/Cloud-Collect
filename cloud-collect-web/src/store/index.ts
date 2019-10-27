import {ObjectError, UserData} from "@/types"
import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)

//NOTE 不要使用解构语法，为了完整的代码提示
//NOTE vuex的Store定义并不能很好地结合typescript

export default new Vuex.Store({
  state: {
    //当前用户
    _currentUser: null,
    //参数校验的错误列表
    _validationErrors: []
  },
  getters: {
    currentUser(state) {
      //TODO 可能需要用到cookie或者storage
      return state._currentUser
    },
    validationErrors(state) {
      const result = state._validationErrors
      //每次读取错误列表时，需要同时清空所有数据
      state._validationErrors = []
      return result
    }
  },
  mutations: {
    setCurrentUser(state, currentUser: UserData) {
      state._currentUser = currentUser as any
    },
    addValidationErrors(state, validationErrors: ObjectError[]) {
      state._validationErrors.push(...validationErrors as [])
    }
  },
  actions: {},
  modules: {}
})
