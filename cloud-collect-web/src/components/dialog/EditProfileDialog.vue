<template>
  <ElDialog title="编辑个人资料" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="120px">
      <!--TODO 允许上传用户头像-->

      <ElFormItem label="昵称">
        <ElInput v-model="savedUser.nickname"></ElInput>
      </ElFormItem>
      <ElFormItem label="简介">
        <ElInput type="textarea" v-model="savedUser.introduce"
                 maxlength="255" show-word-limit :autosize="autoSize"></ElInput>
      </ElFormItem>
      <ElFormItem label="用户名">
        <ElInput v-model="savedUser.username" readonly></ElInput>
      </ElFormItem>
      <ElFormItem label="邮箱">
        <ElInput v-model="savedUser.email" readonly></ElInput>
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成编辑</el-button>
        <el-button type="info" @click="handleClose">取消编辑</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import * as userService from "@/services/userService"
  import {User} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditProfileDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) user!: User

    private autoSize = {minRows: 3, maxRows: 6}
    private savedUser = this.user

    @Emit("submit")
    async handleSubmit() {
      try {
        await userService.modify(this.savedUser.id!, this.savedUser)
        this.$message.success("编辑成功！")
        this.syncVisible = false
      } catch (e) {
        const validationMessage = this.$store.getters.validationMessage
        this.$message.warning(`编辑失败！${validationMessage}`)
      }
    }

    @Emit("close")
    handleClose() {
      this.syncVisible = false
    }
  }
</script>

<style scoped>

</style>
