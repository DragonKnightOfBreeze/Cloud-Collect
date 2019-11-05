<template>
  <!--TODO 前端表单验证-->
  <!--注册对话框-->
  <ElDialog title="注册" center :visible="syncDialogType==='register'" @close="handleClose">
    <!--注册表单-->
    <ElForm>
      <ElFormItem label="用户名" :label-width="formLabelWidth">
        <ElInput v-model="form.username" placeholder="请输入用户名。"/>
      </ElFormItem>
      <ElFormItem label="密码" :label-width="formLabelWidth">
        <ElInput type="password" v-model="form.password" placeholder="请输入密码。"/>
      </ElFormItem>
      <ElFormItem label="确认密码" :label-width="formLabelWidth">
        <ElInput type="password" v-model="rePassword" placeholder="请再次输入密码。"/>
      </ElFormItem>
      <ElFormItem label="昵称" :label-width="formLabelWidth">
        <ElInput v-model="form.nickname" placeholder="请输入昵称。"/>
      </ElFormItem>
      <ElFormItem label="邮箱" :label-width="formLabelWidth">
        <ElInput type="email" v-model="form.email" placehold="请输入邮箱。"/>
      </ElFormItem>
      <ElFormItem>
        <el-button type="text" @click="handleForgotPassword">忘记密码？</el-button>
      </ElFormItem>
    </ElForm>

    <ForgotPasswordDialog :visible.sync="forgotPasswordDialogVisible"></ForgotPasswordDialog>

    <!--提交按钮-->
    <template v-slot:footer>
      <ElButton type="success" :loading="buttonLoading" @click="handleRegister">注 册</ElButton>
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
    @PropSync("dialogType") syncDialogType: DialogType | null

    private buttonLoading = false
    private formLabelWidth = "120px"
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
      this.syncDialogType = "login"
    }

    handleClose() {
      this.syncDialogType = null
    }
  }
</script>

<style scoped>

</style>
