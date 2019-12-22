<template>
  <div>
    <h3>Ta的分类</h3>
    <ElDivider/>
    <!--仅当用户为当前用户时，才会显示以下内容-->
    <ElRow :gutter="5" v-if="isCurrentUser">
      <ElCol :span="4">
        <ElButton type="primary" @click="handleCreate"><ElIcon name="plus"/> 创建分类</ElButton>
      </ElCol>
    </ElRow>
    <ElBlankLine :height="12" v-if="isCurrentUser"/>
    <!--允许过滤-->
    <ElRow :gutter="5">
      <ElCol :span="6">
        <ElInput v-model="searchTerm" placeholder="按名字搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('name')"><ElIcon name="search" /></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="2" :offset="16">
        <ElButton type="info" @click="handleSearch('none')">重置</ElButton>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="categoryPage">
      <TheSorter type="category" :pageable-param.sync="pageableParam" />
      <CategoryOverviewCard v-for="category in categoryPage.content" :key="category.id" :category="category" />
      <ThePagination :page="categoryPage" :pageable-param.sync="pageableParam"/>
    </ElCardGroup>

    <NewCategoryDialog :visible.sync="newDialogVisible" @submit="handleSubmit" />
  </div>
</template>

<script lang="ts">
  import CategoryOverviewCard from "@/components/card/CategoryOverviewCard.vue"
  import NewCategoryDialog from "@/components/dialog/NewCategoryDialog.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Category, CategorySearchType, Page, PageableParam} from "@/domain"
  import * as categoryService from "@/services/categoryService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {TheSorter, ElBlankLine, NewCategoryDialog, ThePagination, CategoryOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailCategories extends Vue {
    private searchTerm: string = ""
    private searchType: CategorySearchType = "none"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private categoryPage: Page<Category> | null = null
    private newDialogVisible = false

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getCategoryPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      if (value.params.id && value.params.id !== oldValue.params.id) {
        this.getCategoryPage()
      }
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getCategoryPage()
    }

    private handleCreate() {
      this.newDialogVisible = true
    }

    private handleSubmit() {
      this.getCategoryPage()
    }

    private handleSearch(type: CategorySearchType) {
      this.searchType = type
      this.getCategoryPage()
    }

    private async getCategoryPage() {
      try {
        switch (this.searchType) {
          case "none":
            this.categoryPage = await categoryService.findAllByUserId(this.userId, this.pageableParam)
            break
          case "name":
            this.categoryPage = await categoryService.findAllByNameContainsAndUserId(this.searchTerm, this.userId, this.pageableParam)
            break
        }
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
