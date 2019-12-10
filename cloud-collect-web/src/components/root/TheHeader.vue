<template>
  <div id="app-header">
    <!--导航栏-->
    <ElRow id="app-navbar" class="justify-content-center align-items-center">
      <!--导航头部-->
      <ElCol :span="4">
        <router-link to="/">
          <!--NOTE 使用ElImage会找不到图片-->
          <img id="app-logo" src="../../assets/logo.png" alt="云收藏" />
        </router-link>
      </ElCol>
      <!--导航内容，到各个分页-->
      <ElCol :span="12">
        <ElMenu mode="horizontal" router :default-active="activeIndex" @select="handleSelect">
          <ElMenuItem v-for="item in menuItemList" :key="item.index" :route="item.path" :index="item.index">
            {{item.name}}
          </ElMenuItem>
        </ElMenu>
      </ElCol>
      <!--导航侧边栏，显示用户信息，或者登录注册按钮-->
      <ElCol :span="8" class="justify-content-end">
        <!--用户信息，点击跳转到档案页，点击下拉项跳转到对应页-->
        <template v-if="currentUser">
          <ElAvatar id="app-user-avatar" fit="fill" :src="currentUser.avatarUrl" />
          <ElDropdown split-button @click="handleGoProfile" @command="handleCommand">
            {{currentUser.nickname}}
            <template v-slot:dropdown>
              <ElDropdownMenu>
                <ElDropdownItem v-for="item in dropdownItemList" :key="item.command" :command="item.command">
                  {{item.name}}
                </ElDropdownItem>
              </ElDropdownMenu>
            </template>
          </ElDropdown>
        </template>
        <!--登录注册按钮-->
        <template v-else>
          <ElButtonGroup class="align-items-end justify-content-end">
            <ElButton type="primary" @click="handleOpenDialog('login')">登录</ElButton>
            <ElButton type="info" @click="handleOpenDialog('register')">注册</ElButton>
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
  import * as indexService from "@/services/indexService"
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
    private dialogType: DialogType = "none"

    get currentUser(): User | null {
      return this.$store.getters.currentUser || this.initUser()
    }

    //NOTE 这里只能监听当前路由，不能使用beforeRouteUpdate回调。
    //NOTE 这个方法不需要在mounted()等钩子函数中主动调用。
    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      this.changeActiveIndex(value, oldValue)
      this.changeOperation(value, oldValue)
    }

    private initUser() {
      console.log("Init user start.")
      let currentUser = null
      //尝试从storage中得到当前用户信息，并提交到store，并后台自动登录
      //如果得到了，还要从后台得到jwt令牌，并存储到store中
      const currentUserItem = window.localStorage.getItem("currentUser")
      if (currentUserItem) {
        currentUser = JSON.parse(currentUserItem) as User
        if (currentUser && currentUser.username && currentUser.password) {
          this.$store.commit("setCurrentUser", currentUser)
          this.generateToken(currentUser.username)
          console.log(`Init user: ${currentUser.username}`)
        }
      }
      return currentUser
    }

    private async generateToken(username: string) {
      const jwtToken = await indexService.generateToken(username)
      this.$store.commit("setJwtToken", jwtToken)
    }

    //监听当前路由，更改activeIndex，默认为0
    private changeActiveIndex(value: Route, oldValue: Route) {
      this.activeIndex = "0"
      for (let navItem of this.menuItemList) {
        //这里应当是包含，而非相等
        if (value.matched[0].path == navItem.path) {
          this.activeIndex = navItem.index
          break
        }
      }
    }

    //监听当前路由，得到查询参数operation，尝试进行相应的操作
    private changeOperation(value: Route, oldValue: Route) {
      const operation = value.query["operation"]
      if (!operation) return

      console.log(`更改当前操作：${operation}`)
      if (operation == "login" || operation == "register") {
        this.handleOpenDialog(operation)
      } else if (operation == "logout") {
        this.handleLogout()
      }
    }

    //切换当前导航
    private handleSelect(index: string, indexPath: string) {
      this.activeIndex = index
    }

    private handleGoProfile() {
      if (this.currentUser != null && this.currentUser.id != null) {
        this.$router.push(`/profile/${this.currentUser.id}`)
      }
    }

    private handleCommand(command: string) {
      if (this.currentUser == null || this.currentUser.id == null) return

      if (command == "logout") {
        this.handleLogout()
      }
      this.$router.push(`/profile/${this.currentUser.id}/${command}`)
    }

    //打开对话框，点击对应按钮时调用
    private handleOpenDialog(dialogType: DialogType) {
      console.log(`打开对话框：${dialogType}`)
      this.dialogType = dialogType
    }

    //注销用户
    private async handleLogout() {
      console.log("注销用户。")
      window.localStorage.removeItem("currentUser")
      this.$store.commit("setCurrentUser", null)
      this.$store.commit("setJwtToken", "")
      await indexService.logout()
    }
  }
</script>

<style scoped>
  #app-header {
    height: 60px;
    line-height: 60px;
  }
  #app-navbar {
    position: fixed;
    width: 960px;
    z-index: 1000;
  }
  #app-logo {
    height: 51px;
    vertical-align: middle;
  }
  #app-user-avatar {
    height: 40px;
    vertical-align: middle;
    margin-right: 5px;
  }
</style>
