<template>
  <!--TODO 前端表单验证-->
  <!--登录对话框-->
  <ElDialog title="登录" center :visible="syncDialogType==='login'" @close="handleClose">
    <!--登录表单-->
    <ElForm label-width="120px" :model="form" :rules="rules" ref="form">
      <ElFormItem label="用户名" prop="username">
        <ElInput v-model="form.username" placeholder="请输入用户名。"/>
      </ElFormItem>
      <ElFormItem label="密码" prop="password">
        <ElInput type="password" v-model="form.password" placeholder="请输入密码。"/>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="text" @click="handleForgotPassword">忘记密码？</ElButton>
      </ElFormItem>
    </ElForm>

    <ForgotPasswordDialog :visible.sync="forgotPasswordDialogVisible"></ForgotPasswordDialog>

    <!--提交按钮-->
    <template v-slot:footer>
      <ElButton type="success" @click="handleLogin">登 录</ElButton>
      <ElButton type="primary" @click="handleRegister">注 册</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import ForgotPasswordDialog from "@/components/dialog/ForgotPasswordDialog.vue"
  import {DialogType, LoginForm} from "@/domain"
  import * as indexService from "@/services/indexService"
  import {Component, PropSync, Vue} from "vue-property-decorator"

  @Component({
    components: {ForgotPasswordDialog}
  })
  export default class LoginDialog extends Vue {
    //NOTE 需要配合.sync修饰符使用，添加到父组件的对应位置
    @PropSync("dialogType", {required: true}) syncDialogType!: DialogType

    private forgotPasswordDialogVisible = false
    private form: LoginForm = {
      username: "",
      password: ""
    }
    private rules = {
      username: [
        {required: true, message: "请输入用户名！"}
      ],
      password: [
        {required: true, message: "请输入密码！"}
      ]
    }

    handleForgotPassword() {
      this.forgotPasswordDialogVisible = true
    }

    async handleLogin() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      //如果用户存在，则存储用户信息，弹出成功提示框，并关闭对话框。否则弹出错误提示框
      //除此之外，还需存储jwt令牌
      try {
        const userDetailsVo = await indexService.login(this.form)
        const user = userDetailsVo.delegateUser
        localStorage.setItem("currentUser", JSON.stringify(user))
        this.$store.commit("setCurrentUser", user)
        const jwtToken = await indexService.generateToken(user.username)
        this.$store.commit("setJwtToken", jwtToken)
        this.$message.success("登录成功！")
        this.syncDialogType = "none"
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`登录失败！${errorMessage}`)
      }
    }

    handleRegister() {
      console.log("注册用户。")
      this.syncDialogType = "register"
    }

    handleClose() {
      this.syncDialogType = "none"
    }
  }
</script>

<style scoped>

</style>
