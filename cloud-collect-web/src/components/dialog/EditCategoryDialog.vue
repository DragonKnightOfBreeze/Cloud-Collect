<template>
  <ElDialog title="编辑分类" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px">
      <ElFormItem label="名字">
        <ElInput v-model="savedCategory.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述">
        <ElInput v-model="savedCategory.summary"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成编辑</el-button>
        <el-button type="info" @click="handleClose">取消编辑</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import * as categoryService from "@/services/categoryService"
  import {Category} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditCategoryDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) category!: Category

    private savedCategory = this.category

    @Emit("submit")
    async handleSubmit() {
      try {
        await categoryService.modify(this.savedCategory.id!, this.savedCategory)
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
