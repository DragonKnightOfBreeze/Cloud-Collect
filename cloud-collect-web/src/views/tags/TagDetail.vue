<template>
  <div>
    <h3 class="align-center">标签详情</h3>
    <ElDivider/>
    <ElCard>
      <template v-slot:header>{{tag.name}}</template>

      <div>创建者：<ElLink :href="'/profile/'+tag.user.id">{{tag.user.nickname}}</ElLink></div>
      <div>创建时间：{{tag.createdTime}}</div>
      <div>修改时间：{{tag.lastModifiedTime}}</div>
      <ElText color="info">{{tag.summary}}</ElText>
    </ElCard>

    <ElCollapse v-model="activeNames" @change="handleChange">
      <ElCollapseItem name="1" title="查看相关收藏">
        <!--TODO 提取组件-->
        <ElCardGroup v-if="showRelativeCollects">
          <ElCard v-for="collect in collectList" :key="collect.id">
            <ElRow>
              <ElCol :span="8">
                <ElLink type="primary" :href="'/collects/'+collect.id">
                  <ElAvatar :src="collect.logoUrl"/>
                  {{collect.name}}
                </ElLink>
              </ElCol>
              <ElCol :span="6">创建时间：{{collect.createdTime}}</ElCol>
              <ElCol :span="6">修改时间：{{tag.lastModifiedTime}}</ElCol>
              <ElCol :span="4">
                <ElLink :href="collect.url">转到源地址</ElLink>
              </ElCol>
            </ElRow>
            <ElRow>
              <ElCol :span="4">{{collect.category | categoryName}}</ElCol>
              <ElCol :span="20">
                <ElTag v-for="tag in collect.tags" :key="tag.id">{{tag.name}}</ElTag>
              </ElCol>
            </ElRow>
            <ElRow>
              <ElCol :span="4">{{collect.user.nickname}}</ElCol>
              <ElCol :span="4"><ElBadge :value="collect.commentCount">评论</ElBadge></ElCol>
              <ElCol :span="4"><ElBadge :value="collect.praiseByUserCount">赞</ElBadge></ElCol>
            </ElRow>
            <ElText>
              {{collect.summary}}
            </ElText>
          </ElCard>

          <ThePagination :pageable-param.sync="collectPageableParam" :total-pages="collectPage.totalPages"
                         :total-elements="collectPage.totalElements"/>
        </ElCardGroup>
      </ElCollapseItem>
    </ElCollapse>
  </div>
</template>

<script lang="ts">
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ElText from "@/components/public/ElText.vue"
  import ThePagination from "@/components/ThePagination.vue"
  import * as tagService from "@/services/tagService"
  import {Collect, Page, PageableParam, Tag} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ElCardGroup, ThePagination, ElText}
  })
  export default class TagDetail extends Vue {
    private tag: Tag | null = null
    private activeNames = []
    private collectPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private collectPage: Page<Collect> | null = null

    get showRelativeCollects() {
      return this.collectPage != null
    }

    get collectList() {
      return this.collectPage && this.collectPage.content || []
    }

    created() {
      this.getTag()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getTag()
    }

    @Watch("collectPageableParam")
    private onCollectPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      this.getCollectPage()
    }

    handleChange() {
      this.getCollectPage()
    }

    private async getTag() {
      const id = parseInt(this.$route.params["id"] as string)
      this.tag = await tagService.findById(id)
    }

    private async getCollectPage() {
      try {
        const id = parseInt(this.$route.params["id"] as string)
        this.collectPage = await tagService.getCollectPage(id, this.collectPageableParam)
      } catch (e) {
        this.$message("查询失败!")
      }
    }
  }
</script>

<style scoped>
  .float-right {
    float: right;
    padding: 3px 0;
  }
</style>
