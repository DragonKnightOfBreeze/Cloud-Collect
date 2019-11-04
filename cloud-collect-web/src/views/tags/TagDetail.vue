<template>
  <div>
    <h3 class="align-center">标签详情</h3>
    <ElDivider/>
    <TagDetailCard :tag="tag"/>
    <ElButtonGroup class="align-center">
      <ElButton type="success" v-show="isCurrentUser" @click="handleEdit">编辑</ElButton>
      <ElButton type="danger" v-show="isCurrentUser" @click="handleDelete">删除</ElButton>
    </ElButtonGroup>

    <EditTagDialog :visible.sync="editDialogVisible" @submit="handleSubmit"/>

    <ElCollapse v-model="activeNames" @change="handleChange">
      <ElCollapseItem name="1" title="查看相关收藏">
        <ElCardGroup v-if="showRelativeCollects">
          <CollectOverviewCard v-for="collect in collectList" :key=collect.id
                               :collect="collect"/>

          <ThePagination :pageable-param.sync="collectPageableParam" :total-pages="collectPage.totalPages"
                         :total-elements="collectPage.totalElements"/>
        </ElCardGroup>
      </ElCollapseItem>
    </ElCollapse>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import TagDetailCard from "@/components/card/TagDetailCard.vue"
  import EditTagDialog from "@/components/dialog/EditTagDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ElText from "@/components/public/ElText.vue"
  import ThePagination from "@/components/ThePagination.vue"
  import * as tagService from "@/services/tagService"
  import {Collect, Page, PageableParam, Tag, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {EditTagDialog, CollectOverviewCard, TagDetailCard, ElCardGroup, ThePagination, ElText}
  })
  export default class TagDetail extends Vue {
    private tag: Tag | null = null
    private activeNames = []
    private collectPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private collectPage: Page<Collect> | null = null
    private editDialogVisible = false

    get tagId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }

    get isCurrentUser() {
      return this.currentUser && this.tag && this.tag.user && this.currentUser.id == this.tag.user.id
    }

    get showRelativeCollects() {
      return this.collectPage != null
    }

    get collectList() {
      return this.collectPage && this.collectPage.content || []
    }

    created() {
      this.getTag()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getTag()
    }

    @Watch("collectPageableParam")
    private onCollectPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      this.getCollectPage()
    }

    handleChange() {
      if (this.showRelativeCollects) return //仅需加载一次
      this.getCollectPage()
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

    //DONE 当用户编辑标签并提交更改成功后，需要从后台重新得到标签数据
    handleSubmit() {
      this.getTag()
    }

    private async getTag() {
      this.tag = await tagService.findById(this.tagId)
    }

    private async getCollectPage() {
      try {
        this.collectPage = await tagService.getCollectPage(this.tagId, this.collectPageableParam)
      } catch (e) {
        this.$message("查询失败!")
      }
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
  .float-right {
    float: right;
    padding: 3px 0;
  }
</style>
