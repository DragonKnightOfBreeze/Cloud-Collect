<template>
  <!--NOTE 参照Vue官网首页的排版-->
  <div class="app-home">
    <ElRow class="app-home-content" type="flex">
      <ElCol :span="8">
        <img class="app-home-logo" alt="Vue logo" src="../assets/logo.png">
      </ElCol>
      <ElCol :span="16">
        <ElRow class="app-home-line">
          <ElCol>
            <h1>云收藏</h1>
          </ElCol>
        </ElRow>
        <ElRow class="app-home-line">
          <ElCol :span="8">
            <ElButton type="primary" @click="handleLogin">登录</ElButton>
          </ElCol>
          <ElCol :span="8">
            <ElButton type="info" @click="handleRegister">注册</ElButton>
          </ElCol>
          <ElCol :span="8">
            <ElButton type="success" @click="handleLookAround">随便看看</ElButton>
          </ElCol>
        </ElRow>
      </ElCol>
    </ElRow>
  </div>
</template>

<script lang="ts">
  import * as indexService from "@/services/indexService"
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class Home extends Vue {
    handleLogin() {
      this.$router.push("/login")
    }

    handleRegister() {
      this.$router.push("/register")
    }

    async handleLookAround() {
      const collect = await indexService.lookAroundCollect()
      const collectId = collect.id!
      await this.$router.push(`/collects/${collectId}`)
    }
  }
</script>

<style scoped>
  .app-home-content {
    width: 600px;
    height: 200px;
    margin: 30px auto;
    text-align: center;
    vertical-align: middle;
  }
  .app-home-line {
    height: 100px;
    vertical-align: middle;
  }
  .app-home-line .el-col {
    height: 100%;
    vertical-align: middle;
  }
  .app-home-line h1 {
    font-size: 36px;
  }
  .app-home-line button {
    font-size: 21px;
  }
</style>
