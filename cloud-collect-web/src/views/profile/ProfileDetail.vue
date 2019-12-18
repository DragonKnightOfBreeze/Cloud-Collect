<template>
  <div>
    <template v-if="user">
      <ElRow type="flex" align="middle">
        <ElCol :span="2">
          <ElAvatar :size="72" v-if="user.avatarUrl" :src="user.avatarUrl"/>
          <ElAvatar :size="72" v-else icon="el-icon-user-solid"/>
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
      <ElMenuItem v-for="item in menuItemList" :key="item.path" :index="item.path">
        {{item.name}}
      </ElMenuItem>
    </ElMenu>

    <!--可以这样监听子组件的事件-->
    <router-view @submit="handleSubmit"></router-view>
  </div>
</template>

<script lang="ts">
  import FollowButton from "@/components/button/FollowButton.vue"
  import {MenuItem, User} from "@/domain"
  import * as userService from "@/services/userService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {FollowButton}
  })
  export default class ProfileDetail extends Vue {
    private user: User | null = null
    private activeIndex = ""

    get menuItemList(): MenuItem[] {
      return [
        {path: `/profile/${this.userId}`, name: "主页"},
        {path: `/profile/${this.userId}/collects`, name: `收藏 ${this.collectCount}`},
        {path: `/profile/${this.userId}/categories`, name: `分类 ${this.categoryCount}`},
        {path: `/profile/${this.userId}/stars`, name: `喜爱 ${this.praiseToCollectCount}`},
        {path: `/profile/${this.userId}/following`, name: `关注 ${this.followToUserCount}`},
        {path: `/profile/${this.userId}/followers`, name: `粉丝 ${this.followByUserCount}`},
        {path: `/profile/${this.userId}/history`, name: "历史"},
        {path: `/profile/${this.userId}/notices`, name: "通知"}
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
      this.changeActiveIndex()
      this.getUser()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      this.changeActiveIndex()
      if (value.params.id === oldValue.params.id) return
      this.getUser()
    }

    //监听当前路由，更改activeIndex
    private changeActiveIndex() {
      if (this.activeIndex != this.$route.path) {
        this.activeIndex = this.$route.path
        console.log(`更改次级导航：${this.activeIndex}`)
      }
    }

    //切换当前导航
    private handleSelect(index: string, indexPath: string) {
      this.activeIndex = index
    }

    handleSubmit() {
      this.getUser()
    }

    private async getUser() {
      this.user = await userService.findById(this.userId)
    }
  }
</script>

<style scoped>

</style>
