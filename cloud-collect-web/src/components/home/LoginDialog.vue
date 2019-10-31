<template>
  <!--TODO 前端表单验证-->
  <!--登录对话框-->
  <ElDialog title="登录" center :visible="syncDialogType==='login'" @close="handleClose">
    <!--登录表单-->
    <ElForm>
      <ElFormItem label="用户名" :label-width="formLabelWidth">
        <ElInput v-model="form.username" placeholder="请输入用户名。"/>
      </ElFormItem>
      <ElFormItem label="密码" :label-width="formLabelWidth">
        <ElInput type="password" v-model="form.password" placeholder="请输入密码。"/>
      </ElFormItem>
      <ElFormItem>
        <el-button type="text" @click="handleForgotPassword">忘记密码？</el-button>
      </ElFormItem>
    </ElForm>

    <ForgotPasswordDialog :visible.sync="forgotPasswordDialogVisible"></ForgotPasswordDialog>

    <!--提交按钮-->
    <template v-slot:footer>
      <ElButton type="success" :loading="buttonLoading" @click="handleLogin">登 录</ElButton>
      <ElButton type="primary" @click="handleRegister">注 册</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import ForgotPasswordDialog from "@/components/home/ForgotPasswordDialog.vue"
  import * as indexService from "@/services/indexService"
  import {DialogType, LoginForm, User} from "@/types"
  import {Component, PropSync, Vue} from "vue-property-decorator"

  @Component({
    components: {ForgotPasswordDialog}
  })
  export default class LoginDialog extends Vue {
    //NOTE 需要配合.sync修饰符使用，添加到父组件的对应位置
    @PropSync("dialogType") syncDialogType!: DialogType | null

    private buttonLoading = false
    private formLabelWidth = "120px"
    private forgotPasswordDialogVisible = false
    private form: LoginForm = {
      username: "",
      password: ""
    }

    handleForgotPassword() {
      this.forgotPasswordDialogVisible = true
    }

    async handleLogin() {
      //如果用户存在，则存储用户信息，弹出成功提示框，并关闭对话框。否则弹出错误提示框
      try {
        const userDetailsVo = await indexService.login(this.form)
        //TODO 测试
        //const user = userDetailsVo.delegateUser
        const user: User = {
          email: "dk@breeze.com",
          nickname: "微风的龙骑士",
          username: this.form.username,
          password: this.form.password
        }
        window.sessionStorage["currentUser"] = JSON.stringify(user)
        this.$store.commit("setCurrentUser", user)
        this.$message.success("登录成功！")
        this.handleClose()
      } catch (e) {
        this.$message.warning("登录失败！请重新填写登录信息。")
      }
    }

    handleRegister() {
      console.log("注册用户。")
      this.syncDialogType = "register"
    }

    handleClose() {
      this.syncDialogType = null
    }
  }
</script>

<style scoped>

</style>
