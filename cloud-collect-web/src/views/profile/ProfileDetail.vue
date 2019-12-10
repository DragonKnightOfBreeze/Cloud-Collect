<template>
  <div>
    <template v-if="user">
      <ElRow type="flex" align="middle">
        <ElCol :span="2">
          <ElAvatar size="large" :src="user.avatarUrl" />
        </ElCol>
        <ElCol :span="18">
          <div class="app-title">{{user.nickname}}</div>
          <div class="app-introduce">{{user.introduce}}</div>
        </ElCol>
        <ElCol :span="4">
          <FollowButton v-if="!isCurrentUser" :user="user"></FollowButton>
        </ElCol>
      </ElRow>
    </template>

    <ElMenu mode="horizontal" router :default-active="activeIndex" @select="handleSelect">
      <ElMenuItem v-for="item in menuItemList" :key="item.index" :route="item.path" :index="item.index">
        {{item.name}}
      </ElMenuItem>
    </ElMenu>

    <router-view></router-view>
  </div>
</template>

<script lang="ts">
  import FollowButton from "@/components/button/FollowButton.vue"
  import * as userService from "@/services/userService"
  import {MenuItem, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {FollowButton}
  })
  export default class ProfileDetail extends Vue {
    user: User | null = null
    activeIndex = "0"

    get menuItemList(): MenuItem[] {
      return [
        {index: "0", path: `/profile/${this.userId}`, name: "主页"},
        {index: "1", path: `/profile/${this.userId}/collects`, name: `收藏 ${this.collectCount}`},
        {index: "2", path: `/profile/${this.userId}/categories`, name: `分类 ${this.categoryCount}`},
        {index: "5", path: `/profile/${this.userId}/stars`, name: `喜爱 ${this.praiseToCollectCount}`},
        {index: "7", path: `/profile/${this.userId}/following`, name: `关注 ${this.followToUserCount}`},
        {index: "6", path: `/profile/${this.userId}/followers`, name: `粉丝 ${this.followByUserCount}`},
        {index: "3", path: `/profile/${this.userId}/history`, name: "历史"},
        {index: "4", path: `/profile/${this.userId}/notices`, name: "通知"}
      ]
    }

    get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser() {
      return this.$store.getters.currentUser
    }

    get collectCount() {
      return this.user ? this.user.collectCount : 0
    }

    get categoryCount() {
      return this.user ? this.user.categoryCount : 0
    }

    get praiseToCollectCount() {
      return this.user ? this.user.praiseToCollectCount : 0
    }

    get followToUserCount() {
      return this.user ? this.user.followToUserCount : 0
    }

    get followByUserCount() {
      return this.user ? this.user.followByUserCount : 0
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getUser()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      this.getUser()
      this.changeActiveIndex(value, oldValue)
    }

    //监听当前路由，更改activeIndex
    private changeActiveIndex(value: Route, oldValue: Route) {
      for (let navItem of this.menuItemList) {
        if (navItem.path == value.path) {
          this.activeIndex = navItem.index
          break
        }
      }
    }

    //切换当前导航
    private handleSelect(index: string, indexPath: string) {
      this.activeIndex = index
    }

    private async getUser() {
      this.user = await userService.findById(this.userId)
    }
  }
</script>

<style scoped>

</style>
