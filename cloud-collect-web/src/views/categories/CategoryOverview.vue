<template>
  <div>
    <ElPageHeader title="返回首页" content="分类总览" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>分类可为收藏进行唯一的归类。</div>
      <div>一个收藏只能属于一个分类。</div>
    </div>
    <ElBlankLine/>
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="8">
        <ElForm inline class="align-center">
          <ElFormItem>
            <ElInput v-model="searchTerm" placeholder="按名字搜索"></ElInput>
          </ElFormItem>
          <ElFormItem>
            <ElButton type="primary" @click="searchCategoryByName"><ElIcon name="search"/></ElButton>
          </ElFormItem>
        </ElForm>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="searchPage">
      <CategoryOverviewCard v-for="category in searchPage.content" :key="category.id" :category="category"/>
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
    <NoContentCard v-else>
      没有符合条件的分类。
    </NoContentCard>
  </div>
</template>

<script lang="ts">
  import CategoryOverviewCard from "@/components/card/CategoryOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as categoryService from "@/services/categoryService"
  import {Category, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {NoContentCard, ElBlankLine, CategoryOverviewCard, ElCardGroup, ThePagination}
  })
  export default class CategoryOverview extends Vue {
    private searchTerm: string = ""
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Category> | null = null

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchCategoryByName()
    }

    handleGoBack() {
      this.$router.push("/")
    }

    async searchCategoryByName() {
      try {
        this.searchPage = await categoryService.findAllByNameContains(this.searchTerm, this.pageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
