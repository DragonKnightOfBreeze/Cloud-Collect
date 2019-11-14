<template>
  <div>
    <h3>Ta的关注</h3>
    <ElDivider/>

    <ElCardGroup>
      <UserOverviewCard v-for="user in followToUserPage.content" :key="user.id" :user="user"/>
      <ThePagination :page="followToUserPage" :pageable-param.sync="followToUserPageableParam"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import UserOverviewCard from "@/components/card/UserOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import ProfileSidebar from "@/components/sidebar/ProfileSidebar.vue"
  import * as userService from "@/services/userService"
  import {Page, PageableParam, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ThePagination, UserOverviewCard, ElCardGroup, ProfileSidebar}
  })
  export default class ProfileDetailFollowing extends Vue {
    private followToUserPageableParam: PageableParam = {page: 0, size: 20}
    private followToUserPage: Page<User> | null = null

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    created() {
      this.getFollowToUserPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getFollowToUserPage()
    }

    @Watch("followToUserPageableParam")
    private onFollowToUserPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getFollowToUserPage()
    }

    private async getFollowToUserPage() {
      try {
        this.followToUserPage = await userService.getFollowToUserPage(this.userId, this.followToUserPageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
