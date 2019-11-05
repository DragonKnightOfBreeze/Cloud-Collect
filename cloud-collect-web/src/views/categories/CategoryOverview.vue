<template>
  <div>
    <h3 class="align-center">分类一览</h3>
    <ElDivider/>
    <div class="align-center">
      <div>分类可为收藏进行唯一的归类。</div>
      <div>一个收藏只能属于一个分类。</div>
    </div>
    <ElDivider/>
    <ElForm inline class="align-center">
      <ElFormItem label="搜索选定的分类">
        <ElInput v-model="searchTerm" placeholder="关键字"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" @click="searchCategoryByName"><ElIcon name="search"/></ElButton>
      </ElFormItem>
    </ElForm>

    <ElCardGroup v-if="showSearchResult">
      <CategoryOverviewCard v-for="category in searchList" :key="category.id"
                            :category="category"/>

      <ThePagination :pageable-param.sync="searchPageableParam" :total-pages="searchPage.totalPages"
                     :total-elements="searchPage.totalElements"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import CategoryOverviewCard from "@/components/card/CategoryOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as categoryService from "@/services/categoryService"
  import {Category, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {CategoryOverviewCard, ElCardGroup, ThePagination}
  })
  export default class CategoryOverview extends Vue {
    private searchTerm: string = ""
    private searchPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private searchPage: Page<Category> | null = null

    get showSearchResult() {
      return this.searchPage != null
    }

    get searchList() {
      return this.searchPage && this.searchPage.content || []
    }

    @Watch("searchTerm")
    onSearchTermChange(value: string, oldValue: string) {
      console.log(`查询参数发生了变化：`, value)
    }

    @Watch("searchPageableParam")
    onSearchPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchCategoryByName()
    }

    async searchCategoryByName() {
      try {
        this.searchPage = await categoryService.findAllByNameContains(this.searchTerm, this.searchPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
