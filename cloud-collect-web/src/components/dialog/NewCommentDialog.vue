<template>
  <ElDialog center :visible="syncVisible" @close="handleClose">
    <ElForm :model="comment" :rules="rules" ref="form">
      <ElFormItem prop="content">
        <ElInput type="textarea" v-model="comment.content" placeholder="请输入评论内容"
                 maxlength="255" show-word-limit :autosize="{minRows: 6, maxRows: 12}"></ElInput>
      </ElFormItem>
    </ElForm>

    <template v-slot:footer>
      <ElButton type="primary" @click="handleSubmit">发送</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import {Collect, Comment, User} from "@/domain"
  import * as commentService from "@/services/commentService"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewCommentDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) collect!: Collect
    @Prop({required: true}) replyToComment: Comment | undefined

    private comment: Comment = {
      content: "",
      collect: this.collect,
      sponsorByUser: this.currentUser
    }
    private rules = {
      content: [
        {required: true, message: "回复内容不能为空！"},
        {max: 255, message: "回复内容过长！"}
      ]
    }

    get currentUser(): User {
      return this.$store.getters.currentUser
    }

    @Emit("submit")
    async handleSubmit() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      //提交时判断用户是否已登录
      if (!this.currentUser) return await this.$router.replace("/login")

      try {
        //创建和回复评论的后台逻辑是不一样的
        this.comment.replyToComment = this.replyToComment
        if (!this.replyToComment) {
          await commentService.create(this.comment)
        } else {
          await commentService.reply(this.comment)
        }
        this.$message.success("发送成功！")
        this.syncVisible = false
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`发送失败！${errorMessage}`)
      }
    }

    handleClose() {
      this.syncVisible = false
    }
  }
</script>

<style scoped>

</style>
