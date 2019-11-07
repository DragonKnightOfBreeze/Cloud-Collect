import {AxiosInstance} from "axios"

//需要声明vue实例下存在这些属性
//不能写在shims-vue.d.ts中，会报错
declare module "vue/types/vue" {
  interface Vue {
    $http: AxiosInstance;
  }
}
