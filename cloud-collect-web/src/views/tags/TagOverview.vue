<template>
  <div>
    <ElPageHeader title="返回首页" @back="handleGoBack">
      <template v-slot:content>
        <ElRow>
          <ElCol :span="18">
            标签总览
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
      <div>标签可为收藏添加额外的说明信息。</div>
      <div>一个收藏可以附带多个标签。</div>
    </div>
    <ElBlankLine/>
    <ElRow class="align-items-center justify-content-center">
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
      <TheSorter type="tag" :pageable-param.sync="pageableParam" />
      <TagOverviewCard v-for="tag in searchPage.content" :key="tag.id" :tag="tag"/>
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
    <NoContentCard v-else-if="searchPage && searchPage.empty">
      没有符合条件的标签。
    </NoContentCard>
  </div>
</template>

<script lang="ts">
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import TagOverviewCard from "@/components/card/TagOverviewCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Page, PageableParam, Tag} from "@/domain"
  import * as tagService from "@/services/tagService"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink, TheSorter, NoContentCard, ElBlankLine, TagOverviewCard, ElCardGroup, ThePagination}
  })
  export default class TagOverview extends Vue {
    private searchTerm: string = ""
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<Tag> | null = null

    //当分页参数发生变化时，重新加载数据
    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.searchTagByName()
    }

    private handleGoBack() {
      this.$router.push("/")
    }

    private async searchTagByName() {
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
