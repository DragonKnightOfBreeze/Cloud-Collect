<template>
  <ElDialog title="编辑收藏" center :visible="syncVisible" @close="handleClose">
    <ElForm label-width="80px" :model="savedCollect" :rules="rules" ref="form">
      <ElFormItem label="名字" prop="name">
        <ElInput v-model="savedCollect.name"></ElInput>
      </ElFormItem>
      <ElFormItem label="概述" prop="summary">
        <ElInput type="textarea" v-model="savedCollect.summary"
                 maxlength="255" show-word-limit :autosize="{minRows: 3, maxRows: 6}"></ElInput>
      </ElFormItem>
      <ElFormItem label="地址">
        <ElInput v-model.trim="savedCollect.url" placeholder="收藏的地址，允许留空"></ElInput>
      </ElFormItem>
      <ElFormItem label="图标地址">
        <ElInput v-model.trim="savedCollect.logoUrl" placeholder="收藏的图标地址，允许留空，自动获取"></ElInput>
      </ElFormItem>
      <ElFormItem label="分类">
        <!--NOTE value-key是相对于option的value属性而言的，将其作为this关键字-->
        <!--NOTE 当option的value属性类型为object时，必须设置select的value-key以及option的对应key属性-->
        <ElSelect v-model="savedCollect.category" :value="savedCollect.category" value-key="id" placeholder="请选择分类"
                  filterable remote reserve-keyword
                  :loading="loadingCategories" :remote-method="searchCategoryByName">
          <ElOption v-for="category in categories" :key="category.id" :label="category.name" :value="category"></ElOption>
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="标签">
        <ElSelect v-model="savedCollect.tags" :value="savedCollect.tags" value-key="id" placeholder="请选择标签"
                  filterable remote reserve-keyword multiple clearable collapse-tags
                  :loading="loadingTags" :remote-method="searchTagByName">
          <ElOption v-for="tag in tags" :key="tag.id" :label="tag.name" :value="tag"></ElOption>
        </ElSelect>
      </ElFormItem>
      <ElFormItem label="类型">
        <ElRadioGroup v-model="savedCollect.type">
          <ElRadio v-for="type in collectTypes" :key="type.name" :label="type.name">
            {{type.text}}
          </ElRadio>
        </ElRadioGroup>
      </ElFormItem>
    </ElForm>

    <template v-slot:footer>
      <ElButton type="primary" @click="handleSubmit">完成编辑</ElButton>
      <ElButton type="info" @click="handleClose">取消编辑</ElButton>
    </template>
  </ElDialog>
</template>

<script lang="ts">
  import {collectTypes} from "@/enums"
  import * as categoryService from "@/services/categoryService"
  import * as collectService from "@/services/collectService"
  import * as tagService from "@/services/tagService"
  import {Category, Collect, PageableParam, Tag, User} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditCollectDialog extends Vue {
    @PropSync("visible", {required: true}) syncVisible!: boolean
    @Prop({required: true}) collect!: Collect

    private savedCollect = {...this.collect}
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

    get currentUser(): User {
      return this.$store.getters.currentUser
    }

    //仅能搜索自己的分类
    private async searchCategoryByName(value: string) {
      console.log("Loading categories...")
      this.loadingCategories = true
      const pageableParam: PageableParam = {page: 0, size: 100}
      this.categories = (await categoryService.findAllByNameContainsAndUserId(value, this.currentUser.id!, pageableParam)).content
      this.loadingCategories = false
    }

    //可以搜索全部用户的标签
    private async searchTagByName(value: string) {
      console.log("Loading tags...")
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
        await collectService.modify(this.savedCollect.id!, this.savedCollect)
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
