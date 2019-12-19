<template>
  <ElPopover v-model="visible" placement="bottom">
    <ElForm class="align-center" size="mini" inline>
      <ElFormItem size="mini" label="选择文件类型">
        <ElSelect class="app-import-select" size="mini" v-model="dataType" :value="dataType" placeholder="请选择">
          <ElOption v-for="type in dataTypes" :key="type.name" :label="type.text" :value="type.name"/>
        </ElSelect>
      </ElFormItem>
      <ElFormItem size="mini" label="选择文件">
        <ElUpload size="mini" action="" :http-request="handleUpload" :show-file-list="false"
                  :before-upload="handleBeforeUpload" :on-success="handleSuccess" :on-error="handleError">
          <ElButton size="mini">上传</ElButton>
        </ElUpload>
      </ElFormItem>

      <ElButton type="primary" size="small" :disabled="!file" @click="handleImport">确定</ElButton>
    </ElForm>

    <ElButton type="warning" slot="reference"><ElIcon name="bottom-left"/> 导入收藏</ElButton>
  </ElPopover>
</template>

<script lang="ts">
  import {dataTypes} from "@/enums"
  import * as dataSerializeService from "@/services/dataSerializeService"
  import {Component, Emit, Vue} from "vue-property-decorator"

  @Component
  export default class DataImportPopover extends Vue {
    private visible = false
    private dataType = "JSON"
    private dataTypes = dataTypes
    private file: File | null = null

    get fileExtension() {
      let temp = this.dataType.toLowerCase()
      if (temp === "yaml") temp = "yml"
      return temp
    }

    handleUpload(file: any) {
      this.file = file.file
    }

    handleBeforeUpload(file: any) {
      const condition1 = file.name.endsWith("xml") || file.name.endsWith("json") || file.name.endsWith("yml")

      if (!condition1) this.$message.warning("上传文件格式不合法！")

      return condition1
    }

    handleSuccess(resource: any, file: any) {
      this.$message.success("上传文件成功！")
    }

    handleError() {
      this.$message.warning("上传文件失败！")
    }

    @Emit("import")
    async handleImport() {
      try {
        if (!this.file) this.$message.warning("请上传要导入的文件！")
        if (!this.file!.name.endsWith(this.fileExtension)) this.$message.warning("文件格式不一致！")

        await dataSerializeService.importData(this.dataType, this.file!)
        this.$message.success("导入收藏成功！")
      } catch (e) {
        this.$message.warning("导入收藏失败！")
      } finally {
        this.visible = false
      }
    }
  }
</script>

<style scoped>
  .el-form-item {
    margin-bottom: 0;
  }
  .app-import-select {
    width: 80px;
  }
</style>
