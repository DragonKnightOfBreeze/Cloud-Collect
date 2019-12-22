<template>
  <ElDialog title="编辑个人资料" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="120px" :model="savedUser" :rules="rules" ref="form">
      <ElFormItem label="头像">
        <ElUpload action="" :http-request="handleUpload" :show-file-list="false"
                  :before-upload="handleBeforeUpload" :on-success="handleSuccess" :on-error="handleError">
          <ElImage class="app-avatar-image" :src="avatarUrl" alt="预览用户头像" v-if="avatarUrl"/>
          <ElButton size="small"><ElIcon name="upload"/> 点击上传头像</ElButton>

          <template v-slot:tip>
            <div class="el-upload__tip"><ElIcon name="warning-outline"/> 只能上传JPG/PNG文件，且不超过3MB</div>
          </template>
        </ElUpload>
      </ElFormItem>
      <ElFormItem label="用户名">
        <ElInput v-model="savedUser.username" disabled></ElInput>
      </ElFormItem>
      <ElFormItem label="邮箱">
        <ElInput v-model="savedUser.email" disabled></ElInput>
      </ElFormItem>
      <ElFormItem label="昵称" prop="nickname">
        <ElInput v-model="savedUser.nickname"></ElInput>
      </ElFormItem>
      <ElFormItem label="简介" prop="introduce">
        <ElInput type="textarea" v-model="savedUser.introduce"
                 maxlength="255" show-word-limit :autosize="{minRows: 3, maxRows: 6}"></ElInput>
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

    //这里的用户和传入的用户不应该是同一个对象
    private savedUser = {...this.user}
    private rules = {
      nickname: [
        {required: true, message: "昵称不能为空！"},
        {max: 32, message: "昵称过长！"}
      ],
      introduce: [
        {max: 255, message: "简介过长！"}
      ]
    }
    private avatarUrl = this.user.avatarUrl || ""

    //这里作为参数的的file居然是File类型对象的封装对象，两者是不一样的……
    private async handleUpload(file: any) {
      const avatarUrl = await userService.uploadAvatar(this.user.id!, file.file)
      //需要更新要更改的用户对象中的头像地址
      this.savedUser.avatarUrl = avatarUrl
      const currentUser = {...this.user}
      currentUser.avatarUrl = avatarUrl
      localStorage.setItem("currentUser", JSON.stringify(currentUser))
      this.$store.commit("setCurrentUser", currentUser)
    }

    private handleBeforeUpload(file: any) {
      const condition1 = file.type === "image/jpeg" || file.type === "image/png"
      const condition2 = file.size / 1024 / 1024 < 3

      if (!condition1) this.$message.warning("上传头像图片只能是JPG/PNG格式!")
      if (!condition2) this.$message.warning("上传头像图片大小不能超过3MB!")

      return condition1 && condition2
    }

    private handleSuccess(resource: any, file: any) {
      this.avatarUrl = URL.createObjectURL(file.raw) //获取用于显示的图片地址
      this.$message.success("上传用户头像成功！")
    }

    private handleError() {
      this.$message.warning("上传用户头像失败！")
    }

    @Emit("submit")
    private async handleSubmit() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      try {
        await userService.modify(this.user.id!, this.savedUser)
        //需要重新从后台获取
        const currentUser = await userService.findById(this.user.id!)
        localStorage.setItem("currentUser", JSON.stringify(currentUser))
        this.$store.commit("setCurrentUser", currentUser)
        this.$message.success("编辑成功！")
        this.syncVisible = false
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`编辑失败！${errorMessage}`)
      }
    }

    @Emit("close")
    private handleClose() {
      this.syncVisible = false
    }
  }
</script>

<style scoped>
  .app-avatar-image {
    width: 178px;
    height: 178px;
    display: block;
    border: 1px solid grey;
  }
</style>
