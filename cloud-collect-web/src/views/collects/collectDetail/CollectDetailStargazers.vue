<template>
  <ElPageHeader title="返回详情页面" content="收藏家" @back="handleGoBack"></ElPageHeader>
  <ElDivider/>
  <div>查看该收藏的收藏家</div>

  <el-card-group>
    <user-overview-card v-for="user in praiseByUserList" :key="user.id" :user="user"/>

    <the-pagination :pageable-param.sync="praiseByUserPageableParam" :total-pages="praiseByUserPage.totalPages"
                    :total-elements="praiseByUserPage.totalElements"/>
  </el-card-group>
</template>

<script lang="ts">
  import UserOverviewCard from "@/components/card/UserOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as collectService from "@/services/collectService"
  import {Page, PageableParam, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ThePagination, UserOverviewCard, ElCardGroup}
  })
  export default class CollectDetailStargazers extends Vue {
    private praiseByUserPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private praiseByUserPage: Page<User> | null = null

    get collectId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get praiseByUserList() {
      return this.praiseByUserPage && this.praiseByUserPage.content || []
    }

    created() {
      this.getPraiseByUserPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getPraiseByUserPage()
    }

    @Watch("praiseByUserPageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      this.getPraiseByUserPage()
    }

    private async getPraiseByUserPage() {
      try {
        this.praiseByUserPage = await collectService.getPraiseByUserPage(this.collectId, this.praiseByUserPageableParam)
      } catch (e) {
        this.$message("查询失败！")
      }
    }

    handleGoBack() {
      this.$router.push(`/collects/${this.collectId}`)
    }
  }
</script>

<style scoped>

</style>
