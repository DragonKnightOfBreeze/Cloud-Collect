<template>
  <div>
    <ElPageHeader title="返回搜索页面" content="搜索收藏" @back="handleGoBack"></ElPageHeader>
    <ElDivider />
    <div>
      <div>搜索指定的收藏。</div>
    </div>
    <ElBlankLine />
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="12">
        <ElInput v-model="searchTerm" placeholder="关键词">
          <template v-slot:prepend>
            <!--这里需要为内嵌的el-select指定宽度-->
            <ElSelect class="app-input-select" v-model="searchType" :value="searchType" placeholder="请选择">
              <ElOption v-for="option in searchOptions" :key="option.value" :label="option.label" :value="option.value"></ElOption>
            </ElSelect>
          </template>

          <template v-slot:append>
            <ElButton @click="handleSearch"><ElIcon name="search" /></ElButton>
          </template>
        </ElInput>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="searchPage">
      <TheSorter type="collect" :pageable-param.sync="pageableParam" />
      <CollectOverviewCard v-for="collect in searchPage.content" :key="collect.id" :collect="collect" />
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
    <NoContentCard v-else-if="searchPage && searchPage.empty">
      没有符合条件的收藏。
    </NoContentCard>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import * as collectService from "@/services/collectService"
  import {Collect, CollectSearchType, Option, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {TheSorter, NoContentCard, ThePagination, CollectOverviewCard, ElCardGroup, ElBlankLine}
  })
  export default class SearchCollect extends Vue {
    private searchTerm: string = ""
    private searchType: CollectSearchType = "name"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Collect> | null = null
    private searchOptions: Option<CollectSearchType>[] = [
      {label: "按名字模糊搜索", value: "name"},
      {label: "按分类名搜索", value: "category"},
      {label: "按分类名模糊搜索", value: "categoryName"},
      {label: "按标签名搜索", value: "tag"},
      {label: "按标签名模糊搜索", value: "tagName"}
    ]

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.searchCollect()
    }

    private handleGoBack() {
      this.$router.push("/search")
    }

    private handleSearch() {
      this.searchCollect()
    }

    private async searchCollect() {
      try {
        switch (this.searchType) {
          case "name":
            this.searchPage = await collectService.findAllByNameContains(this.searchTerm, this.pageableParam)
            break
          case "category":
            this.searchPage = await collectService.findAllByCategoryName(this.searchTerm, this.pageableParam)
            break
          case "categoryName":
            this.searchPage = await collectService.findAllByCategoryNameContains(this.searchTerm, this.pageableParam)
            break
          case "tag":
            this.searchPage = await collectService.findAllByTagName(this.searchTerm, this.pageableParam)
            break
          case "tagName":
            this.searchPage = await collectService.findAllByTagNameContains(this.searchTerm, this.pageableParam)
            break
          default:
            this.$message.error(`Cannot search collects by ${this.searchType} here.`)
            break
        }
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
