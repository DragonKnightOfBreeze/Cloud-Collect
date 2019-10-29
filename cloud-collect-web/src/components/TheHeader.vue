<template>
  <div id="app-header">
    <!--导航栏-->
    <ElRow id="app-navbar" :gutter="20">
      <!--导航头部-->
      <ElCol id="app-navbar-header" :span="4">
        <router-link to="/">
          <!--NOTE 使用ElImage会找不到图片-->
          <img id="app-logo" src="../assets/logo.png" alt="云收藏"/>
        </router-link>
      </ElCol>
      <!--导航内容，到各个分页-->
      <ElCol id="app-navbar-content" :span="16">
        <ElMenu mode="horizontal"
                :router="true"
                :default-active="activeIndex"
                @select="handleSelect">
          <ElMenuItem v-for="navItem in navItemList"
                      :key="navItem.index"
                      :route="navItem.path"
                      :index="navItem.index">
            {{navItem.name}}
          </ElMenuItem>
        </ElMenu>
      </ElCol>
      <!--导航侧边栏，显示用户信息，或者登录注册按钮-->
      <ElCol id="app-navbar-side" :span="4">
        <!--用户信息，点击跳转到档案页，点击下拉项跳转到对应页-->
        <template v-if="currentUser">
          <ElDropdown split-button type="primary" @click="handleCommand('profile')" @command="handleCommand">
            <ElAvatar size="small" :src="currentUser.avatarUrl"/>
            {{currentUser.nickname}}
            <ElDropdownMenu slot="dropdown">
              <ElDropdownItem command="profile">我的资料</ElDropdownItem>
              <ElDropdownItem command="collect">我的收藏</ElDropdownItem>
              <ElDropdownItem command="logout">注销</ElDropdownItem>
            </ElDropdownMenu>
          </ElDropdown>
        </template>
        <!--登录注册按钮-->
        <template v-else>
          <ElButtonGroup>
            <ElButton size="small" type="primary" @click="handleOpenModal('login')">登录</ElButton>
            <ElButton size="small" type="info" @click="handleOpenModal('register')">注册</ElButton>
          </ElButtonGroup>
        </template>
      </ElCol>
    </ElRow>
    <!--模态框-->
    <div id="app-modal" v-show="showModal">
      <LoginModal :visible="modalType==='login'" @close="handleCloseModal"></LoginModal>
      <RegisterModal :visible="modalType==='register'" @close="handleCloseModal"></RegisterModal>
      <ResetPasswordModal :visible="modalType==='resetPassword'" @close="handleCloseModal"></ResetPasswordModal>
    </div>
  </div>
</template>

<script lang="ts">
  import LoginModal from "@/components/home/LoginModal.vue"
  import RegisterModal from "@/components/home/RegisterModal.vue"
  import ResetPasswordModal from "@/components/home/ResetPasswordModal.vue"
  import {NavItem, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ResetPasswordModal, RegisterModal, LoginModal}
  })
  export default class TheHeader extends Vue {
    private activeIndex = "0"
    private navItemList: NavItem[] = [
      {index: "0", path: "/", name: "首页"},
      {index: "1", path: "/collect", name: "收藏"},
      {index: "2", path: "/history", name: "浏览历史"},
      {index: "3", path: "/notice", name: "通知"},
      {index: "4", path: "/search", name: "搜索"},
      {index: "5", path: "/about", name: "关于"}
    ]
    private showModal = false
    private modalType = ""

    //DONE 得到当前用户信息
    get currentUser(): User | null {
      let currentUser = null
      //尝试从storage中得到当前用户信息，并提交到store
      if (window.sessionStorage.currentUser) {
        currentUser = JSON.parse(window.sessionStorage.currentUser) as User
        this.$store.commit("setCurrentUser", currentUser)
      }
      //尝试从store中得到当前用户信息
      if (this.$store.getters.currentUser) {
        currentUser = this.$store.getters.currentUser
      }
      return currentUser
    }

    mounted() {
      this.routeChange(this.$route, this.$route)
    }

    //DONE 监听当前路由，更改activeNavIndex。
    //NOTE 这里只能监听当前路由，不能使用beforeRouteUpdate回调
    @Watch("$route")
    private routeChange(value: Route, oldValue: Route) {
      for (let navItem of this.navItemList) {
        if (navItem.path === value.path) {
          this.activeIndex = navItem.index
          break
        }
      }
    }

    //导航跳转
    handleOpenView(path: string) {
      this.$router.push(path)
    }

    //切换当前导航
    handleSelect(index: string, indexPath: string) {
      this.activeIndex = index
    }

    //处理下拉菜单项的命令
    handleCommand(command: string) {
      if (command === "logout") {
        this.handleLogout()
      } else {
        this.handleOpenView(`/${command}`)
      }
    }

    //打开模态框，点击对应按钮时调用
    handleOpenModal(modalType: string) {
      this.showModal = true
      this.modalType = modalType
    }

    //关闭模态框，在模态框的回调事件中调用
    handleCloseModal(value: boolean) {
      this.showModal = value
    }

    //注销用户
    handleLogout() {
      window.sessionStorage.currentUser = null
      this.$store.commit("setCurrentUser", null)
    }
  }
</script>

<style scoped>
  #app-header {
    text-align: center;
    font-weight: bold;
    line-height: 60px;
  }
  #app-logo {
    height: 50px;
    margin: 0;
  }
</style>
