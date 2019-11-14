<template>
  <ElDialog title="创建分类" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px">
      <ElFormItem label="名字">
        <ElInput v-model="category.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述">
        <ElInput v-model="category.summary"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成创建</el-button>
        <el-button type="info" @click="handleClose">取消创建</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import * as categoryService from "@/services/categoryService"
  import {Category} from "@/types"
  import {Component, Emit, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewCategoryDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean

    private category: Category = {
      name: "",
      summary: ""
    }

    @Emit("submit")
    async handleSubmit() {
      //不允许分类名为空
      if (!this.category.name) return

      try {
        await categoryService.create(this.category)
        this.$message.success("创建成功！")
        this.syncVisible = false
      } catch (e) {
        const validationMessage = this.$store.getters.validationMessage
        this.$message.warning(`创建失败！${validationMessage}`)
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
