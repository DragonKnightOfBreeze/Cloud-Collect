<template>
  <ElDialog title="创建标签" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px" :model="tag" :rules="rules" ref="form">
      <ElFormItem label="名字" prop="name">
        <ElInput v-model="tag.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" prop="summary">
        <ElInput type="textarea" v-model="tag.summary"
                 maxlength="255" show-word-limit :autosize="{minRows: 3, maxRows: 6}"></ElInput>
      </ElFormItem>
    </ElForm>

    <template v-slot:footer>
      <el-button type="primary" @click="handleSubmit">完成创建</el-button>
      <el-button type="info" @click="handleClose">取消创建</el-button>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import {Tag, User} from "@/domain"
  import * as tagService from "@/services/tagService"
  import {Component, Emit, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewTagDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean

    private tag: Tag = {
      name: "",
      summary: "",
      user: this.currentUser
    }
    private rules = {
      name: [
        {required: true, message: "名字不能为空！"},
        {max: 64, message: "名字过长！"}
      ],
      summary: [
        {max: 255, message: "概述过长！"}
      ]
    }

    private get currentUser(): User {
      return this.$store.getters.currentUser
    }

    @Emit("submit")
    async handleSubmit() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      try {
        await tagService.create(this.tag)
        this.$message.success("创建成功！")
        this.syncVisible = false
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`创建失败！${errorMessage}`)
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
