<template>
  <div>
    <ElPageHeader title="返回搜索页面" content="搜索收藏" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>搜索指定的收藏。</div>
    </div>
    <ElBlankLine/>
    <ElInput v-model="searchTerm" placeholder="关键词">
      <template v-slot:prepend>
        <ElSelect v-model="searchType" :value="searchType" placeholder="请选择">
          <ElOption v-for="option in searchOptions" :key="option.value" :label="option.label" :value="option.value"></ElOption>
        </ElSelect>
      </template>

      <template v-slot:append>
        <ElButton><ElIcon name="search"/></ElButton>
      </template>
    </ElInput>

    <ElCardGroup v-if="searchPage">
      <CollectOverviewCard v-for="collect in searchPage.content" :key="collect.id" :collect="collect"/>
      <ThePagination :page="searchPage" :pageable-param.sync="searchPageableParam"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as collectService from "@/services/collectService"
  import {Collect, CollectSearchType, Option, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ThePagination, CollectOverviewCard, ElCardGroup, ElBlankLine}
  })
  export default class SearchCollect extends Vue {
    private searchTerm: string = ""
    private searchType: CollectSearchType = "name"
    private searchPageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Collect> | null = null
    private searchOptions: Option<CollectSearchType>[] = [
      {label: "按名字搜索", value: "name"},
      {label: "按分类搜索", value: "categoryName"},
      {label: "按标签搜索", value: "tagName"}
    ]

    @Watch("searchPageableParam")
    onSearchPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchCollect()
    }

    handleGoBack() {
      this.$router.push("/search")
    }

    async searchCollect() {
      try {
        switch (this.searchType) {
          case "name":
            this.searchPage = await collectService.findAllByNameContains(this.searchTerm, this.searchPageableParam)
            break
          case "categoryName":
            this.searchPage = await collectService.findAllByCategoryNameContains(this.searchTerm, this.searchPageableParam)
            break
          case "tagName":
            this.searchPage = await collectService.findAllByTagNameContains(this.searchTerm, this.searchPageableParam)
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
