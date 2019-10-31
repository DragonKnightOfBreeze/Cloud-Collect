<template>
  <ElPagination
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="syncPageableParam.page"
      :page-size="syncPageableParam.size"
      :total="totalPages"
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

  //NOTE 父组件发送页面切换请求，调用changePage方法，改变查询参数
  //NOTE 父组件再监听查询参数，切换显示的分页
  @Component
  export default class ThePagination extends Vue {
    //NOTE 因为要在子组件中改变这个prop，所以需要设为同步的
    //NOTE pageableParam是计算属性的名字，pageable是prop以及父组件data的名字
    //NOTE 父组件的对应data需要添加.sync修饰符
    @PropSync("pageableParam") syncPageableParam!: PageableParam
    @Prop() totalPages!: number

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
