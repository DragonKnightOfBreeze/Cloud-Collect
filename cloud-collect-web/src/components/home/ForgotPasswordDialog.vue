<template>
  <ElDialog center width="30%" title="忘记密码？" :visible.sync="visible" append-to-body>
    <ElForm inline>
      <ElFormItem>
        <ElInput type="email" v-model="email" placeholder="请输入你的邮箱。"></ElInput>
      </ElFormItem>
      <ElButton type="success" @click="handleForgotPassword">发送重置密码邮件</ElButton>
    </ElForm>

    <template v-slot:footer>
      我们将向你的邮箱发送一条邮件，以重置你的密码。
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import * as indexService from "@/services/indexService"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class ForgotPasswordDialog extends Vue {
    @Prop() visible!: boolean

    private email: string

    async handleForgotPassword() {
      try {
        await indexService.forgotPassword(this.email)

        this.$message.info("邮件已发送。")
        this.handleClose()
      } catch (e) {
        this.$message.warning("用户不存在！")
      }
    }

    handleClose() {
      this.visible = false
    }
  }
</script>

<style scoped>

</style>
