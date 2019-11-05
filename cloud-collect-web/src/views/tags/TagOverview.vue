<template>
  <div>
    <h3 class="align-center">标签一览</h3>
    <ElDivider/>
    <div class="align-center">
      <div>标签可为收藏添加额外的说明信息。</div>
      <div>一个收藏可以附带多个标签。</div>
    </div>
    <ElDivider/>
    <ElForm inline class="align-center">
      <ElFormItem label="搜索选定的标签">
        <ElInput v-model="searchTerm" placeholder="关键字"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" @click="searchTagByName"><ElIcon name="search"/></ElButton>
      </ElFormItem>
    </ElForm>

    <ElCardGroup v-if="showSearchResult">
      <TagOverviewCard v-for="tag in searchList" :key="tag.id"
                       :tag="tag"/>

      <ThePagination :pageable-param.sync="searchPageableParam" :total-pages="searchPage.totalPages"
                     :total-elements="searchPage.totalElements"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import TagOverviewCard from "@/components/card/TagOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as tagService from "@/services/tagService"
  import {Page, PageableParam, Tag} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {TagOverviewCard, ElCardGroup, ThePagination}
  })
  export default class TagOverview extends Vue {
    private searchTerm: string = ""
    private searchPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private searchPage: Page<Tag> | null = null

    get showSearchResult() {
      return this.searchPage != null
    }

    get searchList() {
      return this.searchPage && this.searchPage.content || []
    }

    //DONE 当分页参数发生变化时，重新加载数据
    @Watch("searchPageableParam")
    onSearchPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchTagByName()
    }

    async searchTagByName() {
      try {
        this.searchPage = await tagService.findAllByNameContains(this.searchTerm, this.searchPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
