<template>
  <div>
    <h3 class="align-center">收藏一览</h3>
    <ElDivider/>
    <div class="align-center">
      <div>收藏和管理你所钟情的网页和文章！</div>
    </div>
    <ElDivider/>
    <ElForm inline class="align-center">
      <ElFormItem label="搜索选定的收藏">
        <ElInput v-model="searchTerm1" placeholder="关键字"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" @click="searchCollectByName"><ElIcon name="search"/></ElButton>
      </ElFormItem>
    </ElForm>
    <ElForm inline class="align-center">
      <ElFormItem label="根据分类搜索选定的收藏">
        <ElInput v-model="searchTerm2" placeholder="关键字"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" @click="searchCollectByCategoryName"><ElIcon name="search"/></ElButton>
      </ElFormItem>
    </ElForm>
    <ElForm inline class="align-center">
      <ElFormItem label="根据标签搜索选定的收藏">
        <ElInput v-model="searchTerm3" placeholder="关键字"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" @click="searchCollectByTagName"><ElIcon name="search"/></ElButton>
      </ElFormItem>
    </ElForm>

    <ElCardGroup v-if="showSearchResult">
      <CollectOverviewCard v-for="collect in searchList" :key="collect.id"
                           :collect="collect"/>

      <ThePagination :pageable-param.sync="searchPageableParam" :total-pages="searchPage.totalPages"
                     :total-elements="searchPage.totalElements"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as collectService from "@/services/collectService"
  import {Page, PageableParam, Tag} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ThePagination, CollectOverviewCard, ElCardGroup}
  })
  export default class CollectOverview extends Vue {
    private searchTerm1: string = ""
    private searchTerm2: string = ""
    private searchTerm3: string = ""
    private searchType: "name" | "categoryName" | "tagName" = "name"
    private searchPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private searchPage: Page<Tag> | null = null

    get showSearchResult() {
      return this.searchPage != null
    }

    get searchList() {
      return this.searchPage && this.searchPage.content || []
    }

    @Watch("searchPageableParam")
    onSearchPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchCollect()
    }

    searchCollect() {
      if (this.searchType == "name") {
        this.searchCollectByName()
      } else if (this.searchType == "categoryName") {
        this.searchCollectByCategoryName()
      } else {
        this.searchCollectByTagName()
      }
    }

    async searchCollectByName() {
      this.searchType = "name"
      try {
        this.searchPage = await collectService.findAllByNameContains(this.searchTerm1, this.searchPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }

    async searchCollectByCategoryName() {
      this.searchType = "categoryName"
      try {
        this.searchPage = await collectService.findAllByCategoryNameContains(this.searchTerm2, this.searchPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }

    async searchCollectByTagName() {
      this.searchType = "tagName"
      try {
        this.searchPage = await collectService.findAllByCategoryNameContains(this.searchTerm3, this.searchPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
