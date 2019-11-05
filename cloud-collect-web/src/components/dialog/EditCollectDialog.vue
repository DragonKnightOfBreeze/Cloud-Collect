<template>
  <ElDialog title="编辑收藏" center :visible="syncVisible" @close="handleClose">
    <ElForm>
      <ElFormItem label="名字" :label-width="formLabelWidth">
        <ElInput v-model="collect.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" :label-width="formLabelWidth">
        <ElInput v-model="collect.summary"></ElInput>
      </ElFormItem>
      <ElFormItem label="地址" :label-width="formLabelWidth">
        <ElInput v-model="collect.url"></ElInput>
      </ElFormItem>
      <ElFormItem label="图标地址" :label-width="formLabelWidth">
        <ElInput v-model="collect.logoUrl"></ElInput>
      </ElFormItem>
      <ElFormItem label="分类" :label-width="formLabelWidth">
        <!--TODO-->
      </ElFormItem>
      <ElFormItem label="标签" :label-width="formLabelWidth">
        <!--TODO-->
      </ElFormItem>
      <ElFormItem label="类型" :label-width="formLabelWidth">
        <!--TODO-->
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成编辑</el-button>
        <el-button type="info" @click="handleClose">取消编辑</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import * as collectService from "@/services/collectService"
  import {Collect} from "@/types"
  import {Component, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditCollectDialog extends Vue {
    private formLabelWidth = "80px"

    @PropSync("visible") syncVisible!: boolean
    @Prop() collect!: Collect

    handleSubmit() {
      try {
        collectService.modify(this.collect.id!, this.collect)
        this.$message.success("编辑成功！")
        this.handleClose()
      } catch (e) {
        const validationMessage = this.$store.getters.validationMessage
        this.$message.warning(`编辑失败！${validationMessage}`)
      }
    }

    handleClose() {
      this.syncVisible = false
    }
  }

</script>

<style scoped>

</style>
