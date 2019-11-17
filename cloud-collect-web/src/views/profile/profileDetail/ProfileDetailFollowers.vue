<template>
  <div>
    <h3>Ta的粉丝</h3>
    <ElDivider/>

    <ElCardGroup>
      <UserOverviewCard v-for="user in followByUserPage.content" :key="user.id" :user="user"/>
      <ThePagination :page="followByUserPage" :pageable-param.sync="followByUserPageableParam"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import UserOverviewCard from "@/components/card/UserOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as userService from "@/services/userService"
  import {Page, PageableParam, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ThePagination, UserOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailFollowers extends Vue {
    private followByUserPageableParam: PageableParam = {page: 0, size: 20}
    private followByUserPage: Page<User> | null = null

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    created() {
      this.getFollowByUserPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getFollowByUserPage()
    }

    @Watch("followByUserPageableParam")
    private onFollowByUserPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getFollowByUserPage()
    }

    private async getFollowByUserPage() {
      try {
        this.followByUserPage = await userService.getFollowByUserPage(this.userId, this.followByUserPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
