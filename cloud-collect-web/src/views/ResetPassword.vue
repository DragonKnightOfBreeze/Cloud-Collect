<template>
  <div>
    <h1>重置你的密码</h1>

    <!--重置密码表单-->
    <ElForm>
      <ElFormItem label="新的密码" :label-width="formLabelWidth">
        <ElInput type="password" v-model="form.password" placeholder="请输入新的密码。"/>
      </ElFormItem>
      <ElFormItem label="确认新的密码" :label-width="formLabelWidth">
        <ElInput type="password" v-model="rePassword" placeholder="请再次确认新的密码。"/>
      </ElFormItem>
    </ElForm>

    <!--提交按钮-->
    <template>
      <ElButton type="success" :loading="buttonLoading" @click="handleResetPassword">重置密码</ElButton>
      <ElButton @click="handleGoBack">返回上一页</ElButton>
    </template>
  </div>
</template>

<script lang="ts">
  import * as indexService from "@/services/indexService"
  import {ResetPasswordForm} from "@/types"
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class ResetPassword extends Vue {
    private buttonLoading = false
    private formLabelWidth = "120px"
    private form: ResetPasswordForm = {
      username: "",
      password: ""
    }
    private rePassword = ""
    private resetPasswordCode = ""

    created() {
      this.getParams()
    }

    //DONE 得到对应的查询参数，如果不存在，则认为是非法的访问
    private getParams() {
      if (!this.$route.query["username"] || !this.$route.query["resetPasswordCode"]) {
        console.warn("权限错误")
        this.$router.push("/")
      }
      this.form.username = this.$route.query["username"].toString()
      this.resetPasswordCode = this.$route.query["resetPasswordCode"].toString()
    }

    async handleResetPassword() {
      try {
        await indexService.resetPassword(this.form, this.resetPasswordCode)

        this.$message.success("重置密码成功！请登录。")
        await this.$router.push("/success/resetPassword")
      } catch (e) {
        this.$message.warning("重置密码失败！")
      }
    }

    handleGoBack() {
      this.$router.back()
    }
  }
</script>

<style scoped>

</style>
