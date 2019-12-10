<template>
  <ElPagination id="app-pagination" class="align-center"
                :layout="layout"
                :current-page="syncPageableParam.page+1"
                :page-size="syncPageableParam.size"
                :page-count="page.totalPages"
                :total="page.totalElements"
                :page-sizes="pageSizes"
                @prev-click="handlePrevClick"
                @next-click="handleNextClick"
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange">
  </ElPagination>
</template>

<script lang="ts">
  import {Page, PageableParam} from "@/types"
  import {Component, Prop, PropSync, Vue} from "vue-property-decorator"

  //接收分页参数和总页数，同步并在父组件中监听分页参数，当改变时再次发送查询请求，即可完成分页跳转功能
  @Component
  export default class ThePagination extends Vue {
    //因为要在子组件中改变这个prop，所以需要设为同步的
    //pageableParam是计算属性的名字，pageable是prop以及父组件data的名字
    //父组件的对应data需要添加.sync修饰符
    @Prop({required: true}) page!: Page<any>
    @PropSync("pageableParam", {required: true}) syncPageableParam!: PageableParam
    @Prop({default: "total, sizes, prev, pager, next, jumper"}) layout!: string
    @Prop({default: () => [10, 20, 30, 40, 50]}) pageSizes!: number[]

    //Vue监听器默认监听不到对象或数组内部的变化，所以需要使用这种取巧的方式使之能够监听

    private handlePrevClick() {
      this.syncPageableParam.page--
      this.syncPageableParam = {...this.syncPageableParam}
    }

    private handleNextClick() {
      this.syncPageableParam.page++
      this.syncPageableParam = {...this.syncPageableParam}
    }

    private handleCurrentChange(value: number) {
      this.syncPageableParam.page = value - 1 //page是从0开始的
      this.syncPageableParam = {...this.syncPageableParam}
    }

    private handleSizeChange(value: number) {
      this.syncPageableParam.size = value //size在SpringBoot里面默认为20
      this.syncPageableParam = {...this.syncPageableParam}
    }
  }
</script>

<style scoped>
  #app-pagination {
    margin: 5px 5px;
  }
</style>
