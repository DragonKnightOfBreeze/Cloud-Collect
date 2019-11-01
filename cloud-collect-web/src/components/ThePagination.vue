<template>
  <ElPagination
      :layout="layout"
      :current-page="syncPageableParam.page"
      :page-size="syncPageableParam.size"
      :page-count="totalPages"
      :total="totalElements"
      :page-sizes="pageSizes"
      @prev-click="handlePrevClick"
      @next-click="handleNextClick"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange">
  </ElPagination>
</template>

<script lang="ts">
  import {PageableParam} from "@/types"
  import {Component, Prop, PropSync, Vue} from "vue-property-decorator"

  //NOTE 接收分页参数和总页数，同步并在父组件中监听分页参数，当改变时再次发送查询请求，即可完成分页跳转功能
  @Component
  export default class ThePagination extends Vue {
    //NOTE 因为要在子组件中改变这个prop，所以需要设为同步的
    //NOTE pageableParam是计算属性的名字，pageable是prop以及父组件data的名字
    //NOTE 父组件的对应data需要添加.sync修饰符
    @PropSync("pageableParam") syncPageableParam!: PageableParam
    @Prop() totalPages!: number
    @Prop() totalElements!: number

    @Prop({default: "total, sizes, prev, pager, next, jumper"}) layout!: string
    @Prop({default: [10, 20, 30, 40, 50]}) pageSizes!: number[]

    handlePrevClick() {
      this.syncPageableParam.page--
    }

    handleNextClick() {
      this.syncPageableParam.page++
    }

    handleCurrentChange(value: number) {
      this.syncPageableParam.page = value
    }

    handleSizeChange(value: number) {
      this.syncPageableParam.size = value
    }
  }
</script>

<style scoped>

</style>
