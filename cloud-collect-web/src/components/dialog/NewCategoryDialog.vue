<template>
  <ElDialog title="创建分类" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px" :model="category" :rules="rules" ref="form">
      <ElFormItem label="名字" prop="name">
        <ElInput v-model="category.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" prop="summary">
        <ElInput type="textarea" v-model="category.summary"
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
  import * as categoryService from "@/services/categoryService"
  import {Category, User} from "@/types"
  import {Component, Emit, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewCategoryDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean

    private category: Category = {
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
        await categoryService.create(this.category)
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
