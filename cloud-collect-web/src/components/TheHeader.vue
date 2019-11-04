<template>
  <div id="app-header">
    <!--导航栏-->
    <ElRow id="app-navbar" type="flex" align="center" justify="center" :gutter="20">
      <!--导航头部-->
      <ElCol :span="4">
        <router-link to="/">
          <!--NOTE 使用ElImage会找不到图片-->
          <img id="app-logo" src="../assets/logo.png" alt="云收藏"/>
        </router-link>
      </ElCol>
      <!--导航内容，到各个分页-->
      <ElCol :span="12">
        <ElMenu mode="horizontal" :router="true" :default-active="activeIndex" @select="handleSelect">
          <ElMenuItem v-for="item in menuItemList" :key="item.index"
                      :route="item.path" :index="item.index">
            {{item.name}}
          </ElMenuItem>
        </ElMenu>
      </ElCol>
      <!--导航侧边栏，显示用户信息，或者登录注册按钮-->
      <ElCol :span="2">
        <template v-if="currentUser">
          <ElAvatar id="app-user-avatar" fit="fill" :src="currentUser.avatarUrl"/>
        </template>
      </ElCol>
      <ElCol :span="6">
        <!--用户信息，点击跳转到档案页，点击下拉项跳转到对应页-->
        <template v-if="currentUser">
          <ElDropdown split-button type="primary" @click="handleGoProfile" @command="handleProfileCommand">
            {{currentUser.nickname}}
            <ElDropdownMenu slot="dropdown">
              <ElDropdownItem v-for="item in dropdownItemList" :key="item.command"
                              :command="item.command">
                {{item.name}}
              </ElDropdownItem>
            </ElDropdownMenu>
          </ElDropdown>
        </template>
        <!--登录注册按钮-->
        <template v-else>
          <ElButtonGroup>
            <ElButton size="small" type="primary" @click="handleOpenDialog('login')">登录</ElButton>
            <ElButton size="small" type="info" @click="handleOpenDialog('register')">注册</ElButton>
          </ElButtonGroup>
        </template>
      </ElCol>
    </ElRow>

    <!--对话框-->
    <template>
      <LoginDialog v-if="dialogType==='login'" :dialog-type.sync="dialogType"></LoginDialog>
      <RegisterDialog v-if="dialogType==='register'" :dialog-type.sync="dialogType"></RegisterDialog>
    </template>
  </div>
</template>

<script lang="ts">
  import LoginDialog from "@/components/dialog/LoginDialog.vue"
  import RegisterDialog from "@/components/dialog/RegisterDialog.vue"
  import {DialogType, DropDownItem, MenuItem, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {RegisterDialog, LoginDialog}
  })
  export default class TheHeader extends Vue {
    private activeIndex = "0"
    private menuItemList: MenuItem[] = [
      {index: "0", path: "/", name: "首页"},
      {index: "1", path: "/collects", name: "收藏"},
      {index: "2", path: "/profile", name: "档案"},
      {index: "3", path: "/search", name: "搜索"},
      {index: "4", path: "/about", name: "关于"}
    ]
    private dropdownItemList: DropDownItem[] = [
      {command: "collects", name: "我的收藏"},
      {command: "categories", name: "我的分类"},
      {command: "history", name: "浏览记录"},
      {command: "notices", name: "系统通知"},
      {command: "logout", name: "注销"}
    ]
    private dialogType: DialogType | null = null

    //DONE 得到当前用户信息
    get currentUser(): User | null {
      let currentUser = null
      //尝试从storage中得到当前用户信息，并提交到store
      if (window.sessionStorage["currentUser"]) {
        currentUser = JSON.parse(window.sessionStorage["currentUser"]) as User
        this.$store.commit("setCurrentUser", currentUser)
      }
      //尝试从store中得到当前用户信息
      if (this.$store.getters.currentUser) {
        currentUser = this.$store.getters.currentUser
      }
      return currentUser
    }

    //NOTE 这里只能监听当前路由，不能使用beforeRouteUpdate回调。
    //NOTE 这个方法不需要在mounted()等钩子函数中主动调用。
    @Watch("$route")
    onRouteChange(value: Route, oldValue: Route) {
      this.changeActiveIndex(value, oldValue)
      this.changeOperation(value, oldValue)
    }

    //DONE 监听当前路由，更改activeIndex。
    private changeActiveIndex(value: Route, oldValue: Route) {
      for (let navItem of this.menuItemList) {
        if (navItem.path == value.path) {
          console.log(`更改当前导航索引：${navItem.index}`)
          this.activeIndex = navItem.index
          break
        }
      }
    }

    //DONE 监听当前路由，得到查询参数operation，尝试进行相应的操作
    private changeOperation(value: Route, oldValue: Route) {
      const operation = value.query["operation"]
      console.log(`更改当前操作：${operation}`)
      if (operation == "login" || operation == "register") {
        this.handleOpenDialog(operation)
      } else if (operation == "logout") {
        this.handleLogout()
      }
    }

    //切换当前导航
    handleSelect(index: string, indexPath: string) {
      this.activeIndex = index
    }

    handleGoProfile() {
      if (this.currentUser != null && this.currentUser.id != null) {
        console.log("转到档案页")
        this.$router.push(`/profile/${this.currentUser.id}`)
      }
    }

    handleProfileCommand(command: string) {
      if (this.currentUser != null && this.currentUser.id != null) {
        //如果命令为logout，则注销用户
        if (command == "logout") {
          this.handleLogout()
        }

        console.log(`执行档案命令：${command}`)
        this.$router.push(`/profile/${this.currentUser.id}/${command}`)
      }
    }

    //打开对话框，点击对应按钮时调用
    handleOpenDialog(dialogType: DialogType) {
      console.log(`打开对话框：${dialogType}`)
      this.dialogType = dialogType
    }

    //注销用户
    handleLogout() {
      console.log("注销用户")
      window.sessionStorage["currentUser"] = null
      this.$store.commit("setCurrentUser", null)
    }
  }
</script>

<style scoped>
  #app-navbar {
    position: fixed;
    width: 1000px;
    height: 61px;
    line-height: 61px;
    z-index: 1000;
    background-color: #fff;
  }
  #app-logo {
    height: 51px;
    line-height: 51px;
    vertical-align: middle;
  }
  #app-user-avatar {
    line-height: 51px;
    vertical-align: middle;
  }
</style>
