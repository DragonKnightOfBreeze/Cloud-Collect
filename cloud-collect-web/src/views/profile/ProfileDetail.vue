<template>
  <div>
    <ElRow>
      <ElCol :span="2">
        <ElAvatar :src="user.avatarUrl"/>
      </ElCol>
      <ElCol :span="18">
        <div class="app-title">{{user.nickname}}</div>
        <div class="app-introduce">{{user.introduce}}</div>
      </ElCol>
      <ElCol :span="4">
        <FollowButton v-show="hasCurrentUser && !isCurrentUser" :user="user"></FollowButton>
      </ElCol>
    </ElRow>

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
    private user: User | null = null
    private activeIndex = "0"
    private menuItemList: MenuItem[] = [
      {index: "0", path: "", name: "主页"},
      {index: "1", path: "collects", name: `收藏 ${this.user ? this.user.collectCount : 0}`},
      {index: "2", path: "categories", name: `分类 ${this.user ? this.user.categoryCount : 0}`},
      {index: "5", path: "stars", name: `喜爱 ${this.user ? this.user.praiseToCollectCount : 0}`},
      {index: "7", path: "following", name: `关注 ${this.user ? this.user.followToUserCount : 0}`},
      {index: "6", path: "followers", name: `粉丝 ${this.user ? this.user.followByUserCount : 0}`},
      {index: "3", path: "history", name: "历史"},
      {index: "4", path: "notices", name: "通知"}
    ]

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get hasCurrentUser() {
      return !!this.$store.getters.currentUser
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getUser()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
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
