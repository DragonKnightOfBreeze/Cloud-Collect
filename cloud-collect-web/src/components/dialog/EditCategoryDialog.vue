<template>
  <ElDialog title="编辑分类" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px" :model="savedCategory" :rules="rules" ref="form">
      <ElFormItem label="名字" prop="name">
        <ElInput v-model="savedCategory.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" prop="summary">
        <ElInput type="textarea" v-model="savedCategory.summary"
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
  import * as categoryService from "@/services/categoryService"
  import {Category} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditCategoryDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) category!: Category

    private savedCategory = {...this.category}
    private rules = {
      name: [
        {required: true, message: "名字不能为空！"},
        {max: 64, message: "名字过长！"}
      ],
      summary: [
        {max: 255, message: "概述过长！"}
      ]
    }

    @Emit("submit")
    async handleSubmit() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      try {
        await categoryService.modify(this.savedCategory.id!, this.savedCategory)
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
