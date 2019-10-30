<template>
  <ElPagination
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="pageableParam.page"
      :page-size="pageableParam.size"
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
    @PropSync("pageable") pageableParam!: PageableParam

    @Prop() totalPages!: number
    @Prop({default: [10, 20, 30, 40, 50]}) pageSizes!: number[]

    handlePrevClick() {
      this.pageableParam.page--
      this.changePage()
    }

    handleNextClick() {
      this.pageableParam.page++
      this.changePage()
    }

    handleCurrentChange(value: number) {
      this.pageableParam.page = value
      this.changePage()
    }

    handleSizeChange(value: number) {
      this.pageableParam.size = value
      this.changePage()
    }

    //TODO 调用这个方法
    handleSortChange(value: string[]) {
      this.pageableParam.sort = value
      this.changePage()
    }

    private changePage() {
      const params = {
        page: this.pageableParam.page.toString(),
        size: this.pageableParam.size.toString(),
        sort: this.pageableParam.sort
      }
      this.$router.push({path: "./", query: {...params}})
    }
  }
</script>

<style scoped>

</style>
