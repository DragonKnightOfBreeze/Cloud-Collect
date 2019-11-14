<template>
  <ElDialog title="编辑标签" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px">
      <ElFormItem label="名字">
        <ElInput v-model="savedTag.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述">
        <ElInput v-model="savedTag.summary"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成编辑</el-button>
        <el-button type="info" @click="handleClose">取消编辑</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import * as tagService from "@/services/tagService"
  import {Tag} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditTagDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) tag!: Tag

    private savedTag = this.tag

    @Emit("submit")
    async handleSubmit() {
      try {
        await tagService.modify(this.savedTag.id!, this.savedTag)
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
