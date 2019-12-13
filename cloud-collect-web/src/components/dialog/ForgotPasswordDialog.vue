<template>
  <!--TODO 前端表单验证-->
  <ElDialog title="忘记密码？" center append-to-body width="30%" :visible="syncVisible" @close="handleClose">
    <ElForm inline>
      <ElFormItem>
        <ElInput type="email" v-model="email" placeholder="你的邮箱"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <ElButton type="success" @click="handleForgotPassword">发送邮件</ElButton>
      </ElFormItem>

      <ElText color="info" align="center">
        我们将向你的邮箱发送一条邮件，以重置你的密码。
      </ElText>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import ElText from "@/components/public/ElText.vue"
  import * as indexService from "@/services/indexService"
  import {Component, PropSync, Vue} from "vue-property-decorator"

  @Component({
    components: {ElText}
  })
  export default class ForgotPasswordDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean

    private email: string = ""

    async handleForgotPassword() {
      try {
        await indexService.forgotPassword(this.email)

        this.$message.info("邮件已发送。")
        this.syncVisible = false
      } catch (e) {
        this.$message.warning("用户不存在！")
      }
    }

    handleClose() {
      this.syncVisible = false
    }
  }
</script>

<style scoped>

</style>
