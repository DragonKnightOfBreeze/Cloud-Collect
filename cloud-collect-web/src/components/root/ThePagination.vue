<template>
  <ElPagination class="app-pagination align-center"
                :layout="layout"
                :current-page="syncPageableParam.page+1"
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
    @Prop({default: () => [10, 20, 30, 40, 50]}) pageSizes!: number[]

    //NOTE Vue监听器监听不到数组内部的变化，为什么？

    handlePrevClick() {
      this.syncPageableParam.page--
      this.syncPageableParam = {...this.syncPageableParam}
    }

    handleNextClick() {
      this.syncPageableParam.page++
      this.syncPageableParam = {...this.syncPageableParam}
    }

    handleCurrentChange(value: number) {
      this.syncPageableParam.page = value - 1 //page是从0开始的
      this.syncPageableParam = {...this.syncPageableParam}
    }

    handleSizeChange(value: number) {
      this.syncPageableParam.size = value //size在SpringBoot里面默认为20
      this.syncPageableParam = {...this.syncPageableParam}
    }
  }
</script>

<style scoped>
  .app-pagination {
    margin: 5px 5px;
  }
</style>
