<template>
  <div>
    <ElPageHeader title="返回总览页面" content="分类详情" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <template v-if="category">
      <CategoryDetailCard :category="category" />

      <div class="app-button-group align-center" v-if="isCurrentUser">
        <ElButton type="success" @click="handleEdit"><ElIcon name="edit"/> 编辑</ElButton>
        <ElButton type="danger" @click="handleDelete"><ElIcon name="delete"/> 删除</ElButton>
      </div>

      <EditCategoryDialog :category="category" :visible.sync="editDialogVisible" @submit="handleSubmit" />

      <ElCollapse v-model="activeNames" @change="handleChange">
        <ElCollapseItem name="0" title="查看相关收藏">
          <ElRow>
            <ElCol :span="20">
              相关收藏列表
            </ElCol>
          </ElRow>

          <ElCardGroup v-if="collectPage && !collectPage.empty">
            <TheSorter type="category" :pageable-param.sync="pageableParam" />
            <CollectOverviewCard v-for="collect in collectPage.content" :key=collect.id :collect="collect" />
            <ThePagination :page="collectPage" :pageable-param.sync="pageableParam" />
          </ElCardGroup>
          <NoContentCard v-else>
            没有相关收藏。
          </NoContentCard>
        </ElCollapseItem>
      </ElCollapse>
    </template>
  </div>
</template>

<script lang="ts">
  import CategoryDetailCard from "@/components/card/CategoryDetailCard.vue"
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import EditCategoryDialog from "@/components/dialog/EditCategoryDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Category, Collect, Page, PageableParam, User} from "@/domain"
  import * as categoryService from "@/services/categoryService"
  import * as collectService from "@/services/collectService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {
      TheSorter,
      NoContentCard,
      EditCategoryDialog,
      CollectOverviewCard,
      CategoryDetailCard,
      ElCardGroup,
      ThePagination
    }
  })
  export default class CategoryDetail extends Vue {
    private category: Category | null = null
    private editDialogVisible = false
    private activeNames = []
    private collectPage: Page<Collect> | null = null
    private pageableParam: PageableParam = {page: 0, size: 20}

    get categoryId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }

    get isCurrentUser() {
      return this.currentUser && this.category && this.category.user && this.currentUser.id == this.category.user.id
    }

    created() {
      this.getCategory()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      if (value.params.id && value.params.id !== oldValue.params.id) {
        this.getCategory()
      }
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getCollectPage()
    }

    handleGoBack() {
      this.$router.push("/categories")
    }

    handleChange() {
      if (!this.collectPage) {
        this.getCollectPage()
      }
    }

    handleEdit() {
      this.editDialogVisible = true
    }

    async handleDelete() {
      try {
        await this.$confirm("此操作将永久删除该分类, 是否继续?", {type: "warning"})
        await this.deleteCategory()
      } catch (e) {
        //忽略
      }
    }

    //当用户编辑标签并提交更改成功后，需要从后台重新得到标签数据
    handleSubmit() {
      this.getCategory()
    }

    private async getCategory() {
      this.category = await categoryService.findById(this.categoryId)
    }

    private async getCollectPage() {
      this.collectPage = await collectService.findAllByCategoryId(this.categoryId, this.pageableParam)
    }

    private async deleteCategory() {
      try {
        await categoryService.deleteById(this.categoryId)
        this.$message.success("删除成功！")
        if (this.currentUser) {
          await this.$router.push(`/profile/${this.currentUser!.id}/categories`)
        } else {
          await this.$router.push("/categories")
        }
      } catch (e) {
        this.$message.warning("删除失败！")
      }
    }
  }
</script>

<style scoped>

</style>
