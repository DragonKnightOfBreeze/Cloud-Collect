import {User} from "@/domain"
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
    //错误消息
    _errorMessage: ""
  },
  getters: {
    currentUser(state): User | null {
      return state._currentUser as User | null
    },
    jwtToken(state): string {
      return state._jwtToken
    },
    errorMessage(state): string {
      //每次读取时需要清空消息
      const result = state._errorMessage
      state._errorMessage = ""
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
    setErrorMessage(state, errorMessage: string) {
      state._errorMessage = errorMessage
    }
  },
  actions: {},
  modules: {}
})
