<template>
  <div class="align-center">
    <h3>标签一览</h3>
    <ElDivider/>
    <p>
      标签用于为收藏添加额外的说明信息，进行额外的归类。<br>
      一个收藏可以附带多个标签。
    </p>
    <ElDivider/>
    <ElForm inline>
      <ElFormItem label="搜索选定的标签：">
        <ElInput v-model="searchTerm" placeholder="关键字"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="primary" @click="handleSearchTagByName">
          <ElIcon name="search"/>
        </ElButton>
      </ElFormItem>
    </ElForm>

    <!--TODO 第一行标题显示标签的名字（文字链接）、创建时间、更新时间、收藏数量（+徽章）。第二行显示标签的概述（默认色）。-->
    <template v-if="searchResultIsNotEmpty">
      <ElCard v-for="tag in searchResult" :key="tag.id">

      </ElCard>

      <ThePagination :pageable-param.sync="searchPageableParam" :total-pages="searchResultPage.totalPages"/>
    </template>
  </div>
</template>

<script lang="ts">
  import ThePagination from "@/components/ThePagination.vue"
  import * as tagService from "@/services/tagService"
  import {Page, PageableParam, Tag} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ThePagination}
  })
  export default class TagOverview extends Vue {
    private searchTerm: string
    private searchPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private searchResultPage: Page<Tag> | null = null

    //DONE 当用户提交查询且查询结果不为空时，才显示结果列表
    get searchResultIsNotEmpty() {
      return this.searchResultPage && !this.searchResultPage.empty || false
    }

    //DONE 为了便于IDEA进行类型推断
    get searchResult() {
      return this.searchResultPage && this.searchResultPage.content || []
    }

    //DONE 当分页参数发生变化时，重新加载数据
    @Watch("searchPageableParam")
    private onSearchPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      this.handleSearchTagByName()
    }

    async handleSearchTagByName() {
      try {
        this.searchResultPage = await tagService.findAllByNameContains(this.searchTerm, this.searchPageableParam)
      } catch (e) {
        this.$message("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
