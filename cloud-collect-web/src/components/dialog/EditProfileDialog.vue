<template>
  <ElDialog title="编辑个人资料" center :visible="syncVisible" @close="handleClose">
      <!--TODO 允许上传用户头像-->
    <ElForm label-width="120px" :model="savedUser" :rules="rules" ref="form">
      <ElFormItem label="昵称" prop="nickname">
        <ElInput v-model="savedUser.nickname"></ElInput>
      </ElFormItem>
      <ElFormItem label="简介" prop="introduce">
        <ElInput type="textarea" v-model="savedUser.introduce"
                 maxlength="255" show-word-limit :autosize="{minRows: 3, maxRows: 6}"></ElInput>
      </ElFormItem>
      <ElFormItem label="用户名">
        <ElInput v-model="savedUser.username" disabled></ElInput>
      </ElFormItem>
      <ElFormItem label="邮箱">
        <ElInput v-model="savedUser.email" disabled></ElInput>
      </ElFormItem>
    </ElForm>

    <template v-slot:footer>
      <ElButton type="primary" @click="handleSubmit">完成编辑</ElButton>
      <ElButton type="info" @click="handleClose">取消编辑</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import {User} from "@/domain"
  import * as userService from "@/services/userService"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditProfileDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) user!: User

    //NOTE 这里的用户和传入的用户不应该是同一个对象
    private savedUser = {...this.user}
    private rules = {
      nickname: [
        {required: true, message: "昵称不能为空！"},
        {max: 64, message: "昵称过长！"}
      ],
      introduce: [
        {max: 255, message: "简介过长！"}
      ]
    }

    @Emit("submit")
    async handleSubmit() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      try {
        await userService.modify(this.savedUser.id!, this.savedUser)
        //NOTE 需要处理vuex中的数据
        this.$store.commit("setCurrentUser", this.savedUser)
        this.$message.success("编辑成功！")
        this.syncVisible = false
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`编辑失败！${errorMessage}`)
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
