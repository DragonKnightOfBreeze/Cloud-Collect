<template>
  <ElPopover v-model="visible" placement="bottom">
    <ElForm class="align-center" size="mini" inline>
      <ElFormItem size="mini" label="选择文件类型">
        <ElSelect class="app-export-select" size="mini" v-model="dataType" :value="dataType" placeholder="请选择">
          <ElOption v-for="type in dataTypes" :key="type.name" :label="type.text" :value="type.name"/>
        </ElSelect>
      </ElFormItem>

      <ElButton type="primary" size="small" @click="handleExport">确定</ElButton>
    </ElForm>

    <ElButton type="warning" slot="reference"><ElIcon name="top-right"/> 导出收藏</ElButton>
  </ElPopover>
</template>

<script lang="ts">
  import {dataTypes} from "@/enums"
  import * as dataSerializeService from "@/services/dataSerializeService"
  import {Component, Emit, Vue} from "vue-property-decorator"

  @Component
  export default class DataExportPopover extends Vue {
    private visible = false
    private dataType = "JSON"
    private dataTypes = dataTypes

    private get fileExtension() {
      let temp = this.dataType.toLowerCase()
      if (temp === "yaml") temp = "yml"
      return temp
    }

    @Emit("export")
    private async handleExport() {
      try {
        const blob = await dataSerializeService.exportData(this.dataType)
        if (!blob) return
        const link = document.createElement("a")
        link.style.display = "none"
        link.href = window.URL.createObjectURL(blob)
        link.setAttribute("download", `data.${this.fileExtension}`)
        document.body.appendChild(link)
        link.click()
        this.$message.success("导出收藏成功！")
      } catch (e) {
        this.$message.warning("导出收藏失败！")
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
  .app-export-select {
    width: 80px;
  }
</style>
