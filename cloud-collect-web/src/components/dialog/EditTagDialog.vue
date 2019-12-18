<template>
  <ElDialog title="编辑标签" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px" :model="savedTag" :rules="rules" ref="form">
      <ElFormItem label="名字" prop="name">
        <ElInput v-model="savedTag.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" prop="summary">
        <ElInput type="textarea" v-model="savedTag.summary"
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
  import {Tag} from "@/domain"
  import * as tagService from "@/services/tagService"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditTagDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) tag!: Tag

    private savedTag = {...this.tag}
    private rules = {
      name: [
        {required: true, message: "名字不能为空！"},
        {max: 32, message: "名字过长！"}
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
        await tagService.modify(this.savedTag.id!, this.savedTag)
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
