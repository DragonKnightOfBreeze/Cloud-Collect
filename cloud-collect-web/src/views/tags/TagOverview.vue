<template>
  <div>
    <ElPageHeader title="返回首页" content="标签总览" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>标签可为收藏添加额外的说明信息。</div>
      <div>一个收藏可以附带多个标签。</div>
    </div>
    <ElBlankLine/>
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="8">
        <ElForm inline class="align-center">
          <ElFormItem>
            <ElInput v-model="searchTerm" placeholder="按名字搜索"></ElInput>
          </ElFormItem>
          <ElFormItem>
            <ElButton type="primary" @click="searchTagByName"><ElIcon name="search"/></ElButton>
          </ElFormItem>
        </ElForm>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="searchPage">
      <TagOverviewCard v-for="tag in searchPage.content" :key="tag.id" :tag="tag"/>
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import TagOverviewCard from "@/components/card/TagOverviewCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as tagService from "@/services/tagService"
  import {Page, PageableParam, Tag} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine, TagOverviewCard, ElCardGroup, ThePagination}
  })
  export default class TagOverview extends Vue {
    private searchTerm: string = ""
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Tag> | null = null

    //当分页参数发生变化时，重新加载数据
    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchTagByName()
    }

    handleGoBack() {
      this.$router.push("/")
    }

    async searchTagByName() {
      try {
        this.searchPage = await tagService.findAllByNameContains(this.searchTerm, this.pageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
