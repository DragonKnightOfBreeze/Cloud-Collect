<template>
  <div>
    <ElPageHeader title="返回详情页面" content="收藏家" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>查看该收藏的收藏家。</div>
    <ElBlankLine />
    <ElCardGroup v-if="praiseByUserPage">
      <UserOverviewCard v-for="user in praiseByUserPage.content" :key="user.id" :user="user"/>
      <ThePagination :page="praiseByUserPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import UserOverviewCard from "@/components/card/UserOverviewCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as userService from "@/services/userService"
  import {Page, PageableParam, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ElBlankLine, ThePagination, UserOverviewCard, ElCardGroup}
  })
  export default class CollectDetailStargazers extends Vue {
    private pageableParam: PageableParam = {page: 0, size: 20}
    private praiseByUserPage: Page<User> | null = null

    private get collectId() {
      return parseInt(this.$route.params["id"] as string)
    }

    created() {
      this.getPraiseByUserPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getPraiseByUserPage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getPraiseByUserPage()
    }

    private async getPraiseByUserPage() {
      try {
        this.praiseByUserPage = await userService.findAllByPraiseToCollectId(this.collectId, this.pageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }

    private handleGoBack() {
      this.$router.push(`/collects/${this.collectId}`)
    }
  }
</script>

<style scoped>

</style>
