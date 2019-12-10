<template>
  <!--NOTE 参照Vue官网首页的排版-->
  <div class="app-home">
    <ElRow class="app-home-content">
      <ElCol :span="8">
        <img class="app-home-logo" alt="Vue logo" src="../assets/logo.png">
      </ElCol>
      <ElCol :span="16">
        <ElRow class="app-home-line">
          <ElCol class="justify-content-center align-items-center">
            <h1>云收藏</h1>
          </ElCol>
        </ElRow>
        <ElRow class="app-home-line">
          <ElCol :span="8" class="justify-content-center align-items-center">
            <ElButton type="primary" @click="handleLogin">登录</ElButton>
          </ElCol>
          <ElCol :span="8" class="justify-content-center align-items-center">
            <ElButton type="info" @click="handleRegister">注册</ElButton>
          </ElCol>
          <ElCol :span="8" class="justify-content-center align-items-center">
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
    private handleLogin() {
      this.$router.push("/login")
    }

    private handleRegister() {
      this.$router.push("/register")
    }

    private async handleLookAround() {
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
  }
  .app-home-line {
    height: 100px;
  }
  .app-home-line .el-col {
    height: 100%;
  }
  .app-home-line h1 {
    font-size: 36px;
  }
  .app-home-line button {
    font-size: 24px;
  }
</style>
