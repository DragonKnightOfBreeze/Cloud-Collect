<template>
  <div>
    <ElPageHeader title="返回首页" @back="handleGoBack">
      <template v-slot:content>
        <ElRow>
          <ElCol :span="18">
            分类总览
          </ElCol>
          <ElCol :span="6" class="justify-content-end align-items-center">
            <ElDivider direction="vertical"/>
            <ElRouterLink href="/collects"><ElIcon name="arrow-right"/> 查看收藏</ElRouterLink>
          </ElCol>
        </ElRow>
      </template>
    </ElPageHeader>
    <ElDivider/>
    <div>
      <div>分类可为收藏进行唯一的归类。</div>
      <div>一个收藏只能属于一个分类。</div>
    </div>
    <ElBlankLine/>
    <ElRow class="align-items-center justify-content-center">
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
      <TheSorter type="category" :pageable-param.sync="pageableParam" />
      <CategoryOverviewCard v-for="category in searchPage.content" :key="category.id" :category="category"/>
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
    <NoContentCard v-else-if="searchPage && searchPage.empty">
      没有符合条件的分类。
    </NoContentCard>
  </div>
</template>

<script lang="ts">
  import CategoryOverviewCard from "@/components/card/CategoryOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Category, Page, PageableParam} from "@/domain"
  import * as categoryService from "@/services/categoryService"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink, TheSorter, NoContentCard, ElBlankLine, CategoryOverviewCard, ElCardGroup, ThePagination}
  })
  export default class CategoryOverview extends Vue {
    private searchTerm: string = ""
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Category> | null = null

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.searchCategoryByName()
    }

    private handleGoBack() {
      this.$router.push("/")
    }

    private async searchCategoryByName() {
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
