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
        <!--TODO 可行的写法？-->
        <!--TODO 允许创建新的分类-->
        <!--NOTE value-key是相对于option的value属性而言的，将其作为this关键字-->
        <!--NOTE 当option的value属性类型为object时，必须设置select的value-key以及option的对应key属性-->
        <ElSelect v-model="collect.category" :value="collect.category" value-key="id" placeholder="请选择分类"
                  filterable remote reserve-keyword
                  :remote-method="searchCategoryByName" :loading="loadingCategories">
          <ElOption v-for="category in categories" :key="category.id"
                    :label="category.name" :value="category"></ElOption>
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="标签" :label-width="formLabelWidth">
        <!--TODO 可行的写法？-->
        <!--TODO 允许创建新的标签-->
        <ElSelect v-model="collect.tags" :value="collect.tags" value-key="id" placeholder="请选择标签"
                  filterable remote reserve-keyword multiple clearable
                  :remote-method="searchTagByName" :loading="loadingTags">
          <ElOption v-for="tag in tags" :key="tag.id"
                    :label="tag.name" :value="tag"></ElOption>
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="类型" :label-width="formLabelWidth">
        <ElRadioGroup v-model="collect.type">
          <ElRadio v-for="type in collectTypes" :key="type.name" :label="type.name">
            {{type.text}}
          </ElRadio>
        </ElRadioGroup>
      </ElFormItem>
      <ElFormItem>
        <el-button type="primary" @click="handleSubmit">完成编辑</el-button>
        <el-button type="info" @click="handleClose">取消编辑</el-button>
      </ElFormItem>
    </ElForm>
  </ElDialog>
</template>

<script lang="ts">
  import {collectTypes} from "@/enums"
  import * as categoryService from "@/services/categoryService"
  import * as collectService from "@/services/collectService"
  import * as tagService from "@/services/tagService"
  import {Category, Collect, PageableParam, Tag} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditCollectDialog extends Vue {
    @PropSync("visible") syncVisible!: boolean
    @Prop({required: true}) collect!: Collect

    private formLabelWidth = "80px"
    private categories: Category[] = []
    private loadingCategories = false
    private tags: Tag[] = []
    private loadingTags = false
    private collectTypes = collectTypes

    private async searchCategoryByName(value: string) {
      const pageableParam: PageableParam = {page: 0, size: 100}
      this.categories = (await categoryService.findAllByNameContains(value, pageableParam)).content
    }

    private async searchTagByName(value: string) {
      const pageableParam: PageableParam = {page: 0, size: 100}
      this.tags = (await tagService.findAllByNameContains(value, pageableParam)).content
    }

    @Emit("submit")
    handleSubmit() {
      try {
        collectService.modify(this.collect.id!, this.collect)
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
