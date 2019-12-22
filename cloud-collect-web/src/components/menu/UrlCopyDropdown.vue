<template>
  <ElDropdown size="small" @command="handleCommand">
    <ElButton type="text">复制链接
      <ElIcon name="arrow-down"/>
    </ElButton>

    <template v-slot:dropdown>
      <ElDropdownMenu>
        <ElDropdownItem v-for="item in dropdownItemList" :key="item.command" :command="item.command">
          {{item.name}}
        </ElDropdownItem>
      </ElDropdownMenu>
    </template>
  </ElDropdown>
</template>

<script lang="ts">
  import {Collect, DropDownItem} from "@/domain"
  import * as urlCopyService from "@/services/urlCopyService"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class UrlCopyDropdown extends Vue {
    @Prop({required: true}) collect!: Collect

    private dropdownItemList: DropDownItem[] = [
      {command: "copyUrl", name: "复制为普通链接"},
      {command: "copyAsHtmlUrl", name: "复制为Html链接"},
      {command: "copyAsMdUrl", name: "复制为Markdown链接"},
      {command: "copyAsMdImgUrl", name: "复制为Markdown图片链接"},
      {command: "copyAsMdRefUrl", name: "复制为Markdown引用链接"},
      {command: "copyAsMdRefImgUrl", name: "复制为Markdown图片引用链接"}
    ]

    private async handleCommand(value: string) {
      try {
        const text = await this.getUrl(value)
        await this.$copyText(text)
        this.$message.success("已复制到剪切板！")
      } catch (e) {
        this.$message.warning("复制失败！")
      }
    }

    private async getUrl(type: string) {
      const collect = this.collect
      if (type == "copyUrl") {
        return collect.url
      } else {
        return await urlCopyService.copyAsUrl(type, collect)
      }
    }
  }
</script>

<style scoped>

</style>
