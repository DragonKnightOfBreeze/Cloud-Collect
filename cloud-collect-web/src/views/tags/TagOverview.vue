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
        <ElButton type="primary" @click="onSearchTagByName">
          <ElIcon name="search"/>
        </ElButton>
      </ElFormItem>
    </ElForm>

    <!--TODO-->
    <template v-if="isSearchResultNotEmpty">
      <ElCard>

      </ElCard>

      <ElPagination></ElPagination>
    </template>
  </div>
</template>

<script lang="ts">
  import ThePagination from "@/components/ThePagination.vue"
  import * as tagService from "@/services/collectTagService"
  import {CollectTag, Page, PageableParam} from "@/types"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ThePagination}
  })
  export default class TagOverview extends Vue {
    private searchTerm: string
    private searchPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private searchResultPage: Page<CollectTag> | null = null

    get isSearchResultNotEmpty() {
      return this.searchResultPage && !this.searchResultPage.empty
    }

    async onSearchTagByName() {
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
