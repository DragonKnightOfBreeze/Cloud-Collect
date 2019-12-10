<template>
  <ElDialog title="创建收藏" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px" :model="collect" :rules="rules" ref="form">
      <ElFormItem label="名字" prop="name">
        <ElInput v-model="collect.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" prop="summary">
        <ElInput type="textarea" v-model="collect.summary"
                 maxlength="255" show-word-limit :autosize="{minRows: 3, maxRows: 6}"></ElInput>
      </ElFormItem>
      <ElFormItem label="地址">
        <ElInput v-model="collect.url" placeholder="收藏的地址，允许留空"></ElInput>
      </ElFormItem>
      <ElFormItem label="图标地址">
        <ElInput v-model="collect.logoUrl" placeholder="收藏的图标地址，允许留空，自动获取"></ElInput>
      </ElFormItem>
      <ElFormItem label="分类">
        <!--NOTE value-key是相对于option的value属性而言的，将其作为this关键字-->
        <!--NOTE 当option的value属性类型为object时，必须设置select的value-key以及option的对应key属性-->
        <ElSelect v-model="collect.category" :value="collect.category" value-key="id" placeholder="请选择分类"
                  filterable remote reserve-keyword
                  :loading="loadingCategories" :remote-method="searchCategoryByName">
          <ElOption v-for="category in categories" :key="category.id" :label="category.name" :value="category"></ElOption>
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="标签">
        <ElSelect v-model="collect.tags" :value="collect.tags" value-key="id" placeholder="请选择标签"
                  filterable remote reserve-keyword multiple clearable collapse-tags
                  :loading="loadingTags" :remote-method="searchTagByName">
          <ElOption v-for="tag in tags" :key="tag.id" :label="tag.name" :value="tag"></ElOption>
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="类型">
        <ElRadioGroup v-model="collect.type">
          <ElRadio v-for="type in collectTypes" :key="type.name" :label="type.name">
            {{type.text}}
          </ElRadio>
        </ElRadioGroup>
      </ElFormItem>
    </ElForm>

    <template v-slot:footer>
      <el-button type="primary" @click="handleSubmit">完成创建</el-button>
      <el-button type="info" @click="handleClose">取消创建</el-button>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import {collectTypes} from "@/enums"
  import * as categoryService from "@/services/categoryService"
  import * as collectService from "@/services/collectService"
  import * as tagService from "@/services/tagService"
  import {Category, Collect, PageableParam, Tag, User} from "@/types"
  import {Component, Emit, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class NewCollectDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean

    private collect: Collect = {
      name: "",
      summary: "",
      url: "",
      logoUrl: "",
      category: null,
      tags: [],
      type: "NONE",
      user: this.currentUser
    }
    private rules = {
      name: [
        {required: true, message: "名字不能为空！"},
        {max: 64, message: "名字过长！"}
      ],
      summary: [
        {max: 255, message: "概述过长！"}
      ]
    }
    private categories: Category[] = []
    private loadingCategories = false
    private tags: Tag[] = []
    private loadingTags = false
    private collectTypes = collectTypes

    private get currentUser(): User {
      return this.$store.getters.currentUser
    }

    //仅能搜索自己的分类
    private async searchCategoryByName(value: string) {
      console.log("加载分类 中...")
      this.loadingCategories = true
      const pageableParam: PageableParam = {page: 0, size: 100}
      this.categories = (await categoryService.findAllByNameContainsAndUserId(value, this.currentUser!.id!, pageableParam)).content
      this.loadingCategories = false
    }

    //可以搜索全部用户的标签
    private async searchTagByName(value: string) {
      console.log("加载标签中...")
      this.loadingTags = true
      const pageableParam: PageableParam = {page: 0, size: 100}
      this.tags = (await tagService.findAllByNameContains(value, pageableParam)).content
      this.loadingTags = false
    }

    @Emit("submit")
    async handleSubmit() {
      const isValid = await (this.$refs["form"] as any).validate()
      if (!isValid) return

      try {
        await collectService.create(this.collect)
        this.$message.success("创建成功！")
        this.syncVisible = false
      } catch (e) {
        const errorMessage = this.$store.getters.errorMessage
        this.$message.warning(`创建失败！${errorMessage}`)
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
