<template>
  <div>
    <ElPageHeader title="返回首页" content="收藏总览" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>收藏和管理你所钟情的网页和文章！</div>
    </div>
    <ElBlankLine/>
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="8">
        <ElForm inline class="align-center">
          <ElFormItem>
            <ElInput v-model="searchTerm1" placeholder="按名字搜索"></ElInput>
          </ElFormItem>
          <ElFormItem>
            <ElButton type="primary" @click="handleSearch('name')"><ElIcon name="search" /></ElButton>
          </ElFormItem>
        </ElForm>
      </ElCol>
      <ElCol :span="8">
        <ElForm inline class="align-center">
          <ElFormItem>
            <ElInput v-model="searchTerm2" placeholder="按分类搜索"></ElInput>
          </ElFormItem>
          <ElFormItem>
            <ElButton type="primary" @click="handleSearch('categoryName')"><ElIcon name="search" /></ElButton>
          </ElFormItem>
        </ElForm>
      </ElCol>
      <ElCol :span="8">
        <ElForm inline class="align-center">
          <ElFormItem>
            <ElInput v-model="searchTerm3" placeholder="按标签搜索"></ElInput>
          </ElFormItem>
          <ElFormItem>
            <ElButton type="primary" @click="handleSearch('tagName')"><ElIcon name="search" /></ElButton>
          </ElFormItem>
        </ElForm>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="searchPage">
      <CollectOverviewCard v-for="collect in searchPage.content" :key="collect.id" :collect="collect"/>
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
    <NoContentCard v-else>
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
  import * as collectService from "@/services/collectService"
  import {CollectSearchType, Page, PageableParam, Tag} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {NoContentCard, ElBlankLine, ThePagination, CollectOverviewCard, ElCardGroup}
  })
  export default class CollectOverview extends Vue {
    private searchTerm1: string = ""
    private searchTerm2: string = ""
    private searchTerm3: string = ""
    private searchType: CollectSearchType = "name"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Tag> | null = null

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchCollect()
    }

    private handleGoBack() {
      this.$router.push("/")
    }

    private handleSearch(type: CollectSearchType) {
      this.searchType = type
      this.searchCollect()
    }

    async searchCollect() {
      try {
        switch (this.searchType) {
          case "none":
            this.searchPage = await collectService.findAll(this.pageableParam)
            break
          case "name":
            this.searchPage = await collectService.findAllByNameContains(this.searchTerm1, this.pageableParam)
            break
          case "categoryName":
            this.searchPage = await collectService.findAllByCategoryNameContains(this.searchTerm2, this.pageableParam)
            break
          case "tagName":
            this.searchPage = await collectService.findAllByTagNameContains(this.searchTerm3, this.pageableParam)
            break
          case "type":
            this.$message.error("Cannot search collects by type here.")
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
