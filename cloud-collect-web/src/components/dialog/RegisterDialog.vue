<template>
  <!--TODO 前端表单验证-->
  <!--注册对话框-->
  <ElDialog title="注册" center :visible="syncDialogType==='register'" @close="handleClose">
    <!--注册表单-->
    <ElForm label-width="120px" :model="form" :rules="rules" ref="form">
      <ElFormItem label="用户名" prop="username">
        <ElInput v-model="form.username" placeholder="请输入用户名。" />
      </ElFormItem>
      <ElFormItem label="密码" prop="password">
        <ElInput type="password" v-model="form.password" placeholder="请输入密码。" />
      </ElFormItem>
      <ElFormItem label="确认密码" prop="rePassword">
        <ElInput type="password" v-model="rePassword" placeholder="请再次输入密码。" />
      </ElFormItem>
      <ElFormItem label="邮箱" prop="email">
        <ElInput type="email" v-model="form.email" placehold="请输入邮箱。" />
      </ElFormItem>
      <ElFormItem label="昵称" prop="nickname">
        <ElInput v-model="form.nickname" placeholder="请输入昵称。" />
      </ElFormItem>
      <ElFormItem>
        <ElButton type="text" @click="handleForgotPassword">忘记密码？</ElButton>
      </ElFormItem>
    </ElForm>

    <ForgotPasswordDialog :visible.sync="forgotPasswordDialogVisible"></ForgotPasswordDialog>

    <!--提交按钮-->
    <template v-slot:footer>
      <ElButton type="success" @click="handleRegister">注册</ElButton>
      <ElButton type="primary" @click="handleLogin">登录</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import ForgotPasswordDialog from "@/components/dialog/ForgotPasswordDialog.vue"
  import {DialogType, User} from "@/domain"
  import * as indexService from "@/services/indexService"
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
      email: "",
      nickname: ""
    }
    private rePassword: string = ""
    private rules = {
      username: [
        {required: true, message: "用户名不能为空！"},
        {pattern: /[a-zA-Z]\w{5,15}/, message: "用户名格式不合法！应为6~16位的字母、数字和下划线，以字母开头。"}
      ],
      password: [
        {required: true, message: "密码不能为空！"},
        {pattern: /\w{6,16}/, message: "密码格式不合法！应为6~16位的字母、数字和下划线。"}
      ],
      email: [
        {required: true, message: "邮箱地址不能为空！"},
        {type: "email", message: "邮箱地址不合法！"}
      ],
      nickname: [
        {required: true, message: "昵称不能为空！"},
        {max: 64, message: "昵称过长！"}
      ]
    }

    handleForgotPassword() {
      this.forgotPasswordDialogVisible = true
    }

    async handleRegister() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      if (this.form.password !== this.rePassword) {
        this.$message.warning("密码不一致！")
      }

      try {
        await indexService.register(this.form)
        //如果注册成功，则弹出成功对话框，并弹出登录对话框。否则弹出错误对话框
        this.$message.success("注册成功，请登录！")
        this.handleLogin()
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`注册失败！${errorMessage}`)
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
