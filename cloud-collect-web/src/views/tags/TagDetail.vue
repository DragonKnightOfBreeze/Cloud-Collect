<template>
  <div>
    <ElPageHeader title="返回总览页面" content="标签详情" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <TagDetailCard :tag="tag"/>

    <ElButtonGroup class="align-center" v-show="isCurrentUser">
      <ElButton type="success" @click="handleEdit">编辑</ElButton>
      <ElButton type="danger" @click="handleDelete">删除</ElButton>
    </ElButtonGroup>

    <EditTagDialog :tag="tag" :visible.sync="editDialogVisible" @submit="handleSubmit"/>

    <ElCollapse v-model="activeNames">
      <ElCollapseItem name="1" title="查看相关收藏">
        <ElCardGroup v-if="collectPage && !collectPage.empty">
          <CollectOverviewCard v-for="collect in collectPage.content" :key=collect.id :collect="collect"/>
          <ThePagination :page="collectPage" :pageable-param.sync="collectPageableParam"/>
        </ElCardGroup>
        <div v-else>
          没有相关收藏。
        </div>
      </ElCollapseItem>
    </ElCollapse>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import TagDetailCard from "@/components/card/TagDetailCard.vue"
  import EditTagDialog from "@/components/dialog/EditTagDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as tagService from "@/services/tagService"
  import {Collect, Page, PageableParam, Tag, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {EditTagDialog, CollectOverviewCard, TagDetailCard, ElCardGroup, ThePagination}
  })
  export default class TagDetail extends Vue {
    private tag: Tag | null = null
    private editDialogVisible = false
    private activeNames = []
    private collectPage: Page<Collect> | null = null
    private collectPageableParam: PageableParam = {page: 0, size: 20}

    get tagId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }

    get isCurrentUser() {
      return this.currentUser && this.tag && this.tag.user && this.currentUser.id == this.tag.user.id
    }

    created() {
      this.getTag()
      this.getCollectPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getTag()
      this.getCollectPage()
    }

    @Watch("collectPageableParam")
    private onCollectPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getCollectPage()
    }

    handleGoBack() {
      this.$router.push("/tags")
    }

    handleEdit() {
      this.editDialogVisible = true
    }

    async handleDelete() {
      try {
        await this.$confirm("此操作将永久删除该标签, 是否继续?", {type: "warning"})
        await this.deleteTag()
        await this.$router.push(`/profile/${this.currentUser!.id}`)
      } catch (e) {
        this.$message.info("已取消删除。")
      }
    }

    //当用户编辑标签并提交更改成功后，需要从后台重新得到标签数据
    handleSubmit() {
      this.getTag()
    }

    private async getTag() {
      this.tag = await tagService.findById(this.tagId)
    }

    private async getCollectPage() {
        this.collectPage = await tagService.getCollectPage(this.tagId, this.collectPageableParam)
    }

    private async deleteTag() {
      try {
        await tagService.deleteById(this.tagId)
        this.$message.success("删除成功！")
      } catch (e) {
        this.$message.warning("删除失败！")
      }
    }
  }
</script>

<style scoped>

</style>
