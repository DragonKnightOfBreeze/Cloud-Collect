<template>
  <div>
    <h2 class="align-center">重置你的密码</h2>

    <!--重置密码表单-->
    <ElRow class="justify-content-center">
      <ElCol :span="12">
        <ElForm label-width="80px">
          <ElFormItem label="新的密码">
            <ElInput type="password" v-model="form.password" placeholder="请输入新的密码。"/>
          </ElFormItem>
          <ElFormItem label="确认密码">
            <ElInput type="password" v-model="rePassword" placeholder="请再次确认新的密码。"/>
          </ElFormItem>
        </ElForm>
      </ElCol>
    </ElRow>

    <!--提交按钮-->
    <div class="app-button-group align-center">
      <ElButton type="success" @click="handleResetPassword">重置密码</ElButton>
      <ElButton @click="handleGoBack">返回上一页</ElButton>
    </div>
  </div>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {ResetPasswordForm} from "@/domain"
  import * as indexService from "@/services/indexService"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class ResetPassword extends Vue {
    private form: ResetPasswordForm = {
      username: "",
      password: ""
    }
    private rePassword = ""
    private resetPasswordCode = ""

    created() {
      this.getParams()
    }

    //得到对应的查询参数，如果不存在，则认为是非法的访问
    private getParams() {
      if (!this.$route.query["username"] || !this.$route.query["resetPasswordCode"]) {
        console.warn("权限错误")
        this.$router.push("/")
      }
      this.form.username = this.$route.query["username"].toString()
      this.resetPasswordCode = this.$route.query["resetPasswordCode"].toString()
    }

    private async handleResetPassword() {
      try {
        await indexService.resetPassword(this.form, this.resetPasswordCode)

        this.$message.success("重置密码成功！请登录。")
        await this.$router.push("/success/resetPassword")
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`重置密码失败！${errorMessage}`)
      }
    }

    private handleGoBack() {
      this.$router.back()
    }
  }
</script>

<style scoped>

</style>
