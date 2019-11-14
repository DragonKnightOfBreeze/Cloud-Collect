<template>
  <!--TODO 前端表单验证-->
  <!--注册对话框-->
  <ElDialog title="注册" center :visible="syncDialogType==='register'" @close="handleClose">
    <!--注册表单-->
    <ElForm label-width="120px">
      <ElFormItem label="用户名">
        <ElInput v-model="form.username" placeholder="请输入用户名。"/>
      </ElFormItem>
      <ElFormItem label="密码">
        <ElInput type="password" v-model="form.password" placeholder="请输入密码。"/>
      </ElFormItem>
      <ElFormItem label="确认密码">
        <ElInput type="password" v-model="rePassword" placeholder="请再次输入密码。"/>
      </ElFormItem>
      <ElFormItem label="昵称">
        <ElInput v-model="form.nickname" placeholder="请输入昵称。"/>
      </ElFormItem>
      <ElFormItem label="邮箱">
        <ElInput type="email" v-model="form.email" placehold="请输入邮箱。"/>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="text" @click="handleForgotPassword">忘记密码？</ElButton>
      </ElFormItem>
    </ElForm>

    <ForgotPasswordDialog :visible.sync="forgotPasswordDialogVisible"></ForgotPasswordDialog>

    <!--提交按钮-->
    <template v-slot:footer>
      <ElButton type="success" @click="handleRegister">注 册</ElButton>
      <ElButton type="primary" @click="handleLogin">登录</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import ForgotPasswordDialog from "@/components/dialog/ForgotPasswordDialog.vue"
  import * as indexService from "@/services/indexService"
  import {DialogType, User} from "@/types"
  import {Component, PropSync, Vue} from "vue-property-decorator"

  @Component({
    components: {ForgotPasswordDialog}
  })
  export default class RegisterDialog extends Vue {
    @PropSync("dialogType", {required: true}) syncDialogType!: DialogType

    private forgotPasswordDialogVisible = false
    private form: User = {
      username: "",
      password: "",
      nickname: "",
      email: ""
    }
    private rePassword: string = ""

    handleForgotPassword() {
      this.forgotPasswordDialogVisible = true
    }

    async handleRegister() {
      //要求用户输入
      if (!this.form.username || !this.form.password || !this.rePassword || !this.form.nickname || !this.form.email) return

      try {
        await indexService.register(this.form)
        //如果注册成功，则弹出成功对话框，并弹出登录对话框。否则弹出错误对话框
        this.$message.success("注册成功，请登录！")
        this.handleLogin()
      } catch (e) {
        const validationMessage = this.$store.getters.validationMessage
        this.$message.warning(`注册失败！${validationMessage}`)
      }
    }

    handleLogin() {
      console.log("登录用户。")
      this.syncDialogType = "login"
    }

    //这里返回首页
    handleClose() {
      this.syncDialogType = "none"
      this.$router.push("/")
    }
  }
</script>

<style scoped>

</style>
