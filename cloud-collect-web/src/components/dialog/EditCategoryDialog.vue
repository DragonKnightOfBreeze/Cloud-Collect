<template>
  <ElDialog title="编辑分类" center :visible="syncVisible" @close="handleClose">
    <ElForm>
      <ElFormItem label="名字" :label-width="formLabelWidth">
        <ElInput v-model="category.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" :label-width="formLabelWidth">
        <ElInput v-model="category.summary"></ElInput>
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
    private formLabelWidth = "80px"

    @PropSync("visible") syncVisible!: boolean
    @Prop({required: true}) category!: Category

    @Emit("submit")
    handleSubmit() {
      try {
        categoryService.modify(this.category.id!, this.category)
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
