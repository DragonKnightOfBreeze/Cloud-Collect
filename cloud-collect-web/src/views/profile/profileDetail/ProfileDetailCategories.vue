<template>
  <div>
    <h3>Ta的分类</h3>
    <ElDivider/>

    <!--仅当用户为当前用户时，才会显示以下内容-->
    <ElRow v-show="isCurrentUser">
      <ElCol :span="4" :offset="20">
        <ElButton type="primary" @click="handleCreate">创建分类</ElButton>
      </ElCol>
    </ElRow>

    <ElCardGroup>
      <CategoryOverviewCard v-for="category in categories" :key="category.id" :category="category"/>
      <ThePagination :page="categoryPage" :pageable-param.sync="pageableParam"/>
    </ElCardGroup>

    <NewCategoryDialog :visible.sync="newDialogVisible" @submit="handleSubmit" />
  </div>
</template>

<script lang="ts">
  import CategoryOverviewCard from "@/components/card/CategoryOverviewCard.vue"
  import NewCategoryDialog from "@/components/dialog/NewCategoryDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as categoryService from "@/services/categoryService"
  import {Category, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {NewCategoryDialog, ThePagination, CategoryOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailCategories extends Vue {
    private pageableParam: PageableParam = {page: 0, size: 20}
    private categoryPage: Page<Category> | null = null
    private newDialogVisible = false

    private get categories() {
      return this.categoryPage ? this.categoryPage.content : []
    }

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
      this.getCategoryPage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getCategoryPage()
    }

    private handleCreate() {
      this.newDialogVisible = true
    }

    private handleSubmit() {
      this.getCategoryPage()
    }

    private async getCategoryPage() {
      try {
        this.categoryPage = await categoryService.findAllByUserId(this.userId, this.pageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
