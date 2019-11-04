<template>
  <ElDialog title="编辑标签" :visible="syncVisible" @close="handleClose">
    <ElForm>
      <ElFormItem label="名字" :label-width="formLabelWidth">
        <ElInput v-model="tag.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" :label-width="formLabelWidth">
        <ElInput v-model="tag.summary"></ElInput>
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
    private formLabelWidth = "80px"

    @PropSync("visible") syncVisible!: boolean
    @Prop() tag!: Tag

    @Emit("submit")
    handleSubmit() {
      try {
        tagService.modify(this.tag.id!, this.tag)
        this.$message.success("编辑成功！")
        this.handleClose()
      } catch (e) {
        this.$message.warning("编辑失败！")
      }
    }

    handleClose() {
      this.syncVisible = false
    }
  }
</script>

<style scoped>

</style>
