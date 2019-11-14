<template>
  <div>
    <h3>Ta喜爱的收藏</h3>
    <ElDivider/>

    <ElCardGroup>
      <CollectOverviewCard v-for="collect in praiseToCollectPage.content" :key="collect.id" :collect="collect"/>
      <ThePagination :page="praiseToCollectPage" :pageable-param.sync="praiseToCollectPageableParam"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import ProfileSidebar from "@/components/sidebar/ProfileSidebar.vue"
  import * as userService from "@/services/userService"
  import {Collect, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ThePagination, CollectOverviewCard, ElCardGroup, ProfileSidebar}
  })
  export default class ProfileDetailStars extends Vue {
    private praiseToCollectPageableParam: PageableParam = {page: 0, size: 20}
    private praiseToCollectPage: Page<Collect> | null = null

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    created() {
      this.getPraiseToCollectPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getPraiseToCollectPage()
    }

    @Watch("praiseToCollectPageableParam")
    private onPraiseToCollectPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getPraiseToCollectPage()
    }

    private async getPraiseToCollectPage() {
      try {
        this.praiseToCollectPage = await userService.getPraiseToCollectPage(this.userId, this.praiseToCollectPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
