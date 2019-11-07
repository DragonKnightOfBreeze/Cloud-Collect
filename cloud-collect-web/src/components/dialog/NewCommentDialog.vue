<template>
  <el-dialog center :visible="syncVisible" @close="handleClose">
    <el-form>
      <el-form-item>
        <el-input type="textarea" v-model="comment.content" placeholder="请输入回复内容"
                  maxlength="255" show-word-limit :autosize="autoSize"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">发送</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts">
  import * as commentService from "@/services/commentService"
  import {Collect, Comment, User} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewCommentDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) collect!: Collect
    @Prop({required: true}) replyToComment: Comment | undefined

    private autoSize = {minRows: 3, maxRows: 6}
    private comment: Comment = {
      content: "",
      collect: this.collect,
      replyToComment: this.replyToComment
    }

    get currentUser(): User | undefined {
      return this.$store.getters.currentUser
    }

    @Emit("submit")
    async handleSubmit() {
      //不允许回复内容为空
      if (!this.comment.content) return

      //提交时判断用户是否已登录
      if (!this.currentUser) return await this.$router.replace("/login")

      try {
        //创建和回复评论的后台逻辑是不一样的
        if (!this.replyToComment) {
          await commentService.create(this.comment)
        } else {
          await commentService.reply(this.comment)
        }
        this.$message.success("发送成功！")
        this.syncVisible = false
      } catch (e) {
        const validationMessage = this.$store.getters.validationMessage
        this.$message.warning(`发送失败！${validationMessage}`)
      }
    }

    handleClose() {
      this.syncVisible = false
    }
  }
</script>

<style scoped>

</style>
