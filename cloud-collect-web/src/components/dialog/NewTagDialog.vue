<template>
  <ElDialog title="创建标签" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px">
      <ElFormItem label="名字">
        <ElInput v-model="tag.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述">
        <ElInput v-model="tag.summary"></ElInput>
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成创建</el-button>
        <el-button type="info" @click="handleClose">取消创建</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import * as tagService from "@/services/tagService"
  import {Tag} from "@/types"
  import {Component, Emit, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewTagDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean

    private tag: Tag = {
      name: "",
      summary: ""
    }

    @Emit("submit")
    async handleSubmit() {
      //不允许标签名为空
      if (!this.tag.name) return

      try {
        await tagService.create(this.tag)
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
