<template>
  <div>
    <h3>Ta的粉丝</h3>
    <ElDivider />
    <!--允许过滤-->
    <ElRow :gutter="5" class="align-items-center">
      <ElCol :span="6">
        <ElInput v-model="searchTerm" placeholder="按昵称搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('nickname')"><ElIcon name="search" /></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="2" :offset="16">
        <ElButton type="info" @click="handleSearch('none')">重置</ElButton>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="followByUserPage">
      <TheSorter type="user" :pageable-param.sync="pageableParam" />
      <UserOverviewCard v-for="user in followByUserPage.content" :key="user.id" :user="user" />
      <ThePagination :page="followByUserPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import UserOverviewCard from "@/components/card/UserOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import * as userService from "@/services/userService"
  import {Page, PageableParam, User, UserSearchType} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {TheSorter, ThePagination, UserOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailFollowers extends Vue {
    private searchTerm: string = ""
    private searchType: UserSearchType = "none"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private followByUserPage: Page<User> | null = null

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    created() {
      this.getFollowByUserPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      this.getFollowByUserPage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getFollowByUserPage()
    }

    private handleSearch(type: UserSearchType) {
      this.searchType = type
      this.getFollowByUserPage()
    }

    private async getFollowByUserPage() {
      try {
        switch (this.searchType) {
          case "none":
            this.followByUserPage = await userService.findAllByFollowToUserId(this.userId, this.pageableParam)
            break
          case "nickname":
            this.followByUserPage = await userService.findAllByNicknameContainsAndFollowToUserId(this.searchTerm, this.userId, this.pageableParam)
            break
          default:
            this.$message.error("Cannot search collects by type here.")
            break
        }
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
