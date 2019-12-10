<template>
  <div>
    <ElPageHeader title="返回总览页面" content="收藏详情" @back="handleGoBack"></ElPageHeader>
    <ElDivider />
    <template v-if="collect">
      <CollectDetailCard :collect="collect" />

      <div class="align-center" v-if="isCurrentUser">
        <ElButton type="success" @click="handleEdit"><ElIcon name="edit"/> 编辑</ElButton>
        <ElButton type="danger" @click="handleDelete"><ElIcon name="delete"/> 删除</ElButton>
      </div>

      <div class="align-center" v-if="currentUser && !isCurrentUser">
        <ElButton type="primary" @click="handleFork"><ElIcon name="document-copy"/> 拷贝收藏</ElButton>
      </div>

      <EditCollectDialog :collect="collect" :visible.sync="editDialogVisible" @submit="handleSubmit" />

      <ElCollapse id="comments" v-model="activeNames">
        <ElCollapseItem name="0" title="查看评论">
          <ElRow class="app-title align-items-center">
            <ElCol :span="20">
              评论列表
            </ElCol>
            <ElCol :span="4">
              <ElButton type="success" @click="handleNewComment"><ElIcon name="chat-dot-square"/> 新评论</ElButton>
            </ElCol>
          </ElRow>

          <ElCardGroup v-if="commentPage && !commentPage.empty">
            <CommentOverviewCard v-for="comment in commentPage.content" :key="comment.id" :comment="comment"
                                 @reply="handleReplyComment(comment)" />
            <ThePagination :page="commentPage" :pageable-param.sync="pageableParam" />
          </ElCardGroup>
          <NoContentCard v-else>
            暂无评论。
          </NoContentCard>
        </ElCollapseItem>
      </ElCollapse>

      <NewCommentDialog :visible.sync="newCommentDialogVisible" :collect="collect" :replyToComment="replyToComment"
                        @submit="handleSubmitComment" />
    </template>
  </div>
</template>

<script lang="ts">
  import CollectDetailCard from "@/components/card/CollectDetailCard.vue"
  import CommentOverviewCard from "@/components/card/CommentOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import EditCollectDialog from "@/components/dialog/EditCollectDialog.vue"
  import NewCommentDialog from "@/components/dialog/NewCommentDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as collectService from "@/services/collectService"
  import * as commentService from "@/services/commentService"
  import {Collect, Comment, Page, PageableParam, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {
      NoContentCard,
      CommentOverviewCard,
      NewCommentDialog,
      ThePagination,
      ElCardGroup,
      CollectDetailCard,
      EditCollectDialog
    }
  })
  export default class CollectDetailOverview extends Vue {
    private collect: Collect | null = null
    private editDialogVisible = false
    private activeNames = ["0"]
    private commentPage: Page<Comment> | null = null
    private pageableParam: PageableParam = {page: 0, size: 20}
    private newCommentDialogVisible = false
    private replyToComment: Comment | null = null

    get collectId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }

    get isCurrentUser() {
      return this.currentUser && this.collect && this.collect.user && this.currentUser.id == this.collect.user.id
    }

    created() {
      this.getCollect()
      this.getCommentPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      this.getCollect()
      this.getCommentPage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getCommentPage()
    }

    handleGoBack() {
      this.$router.push("/collects")
    }

    handleEdit() {
      this.editDialogVisible = true
    }

    async handleDelete() {
      try {
        await this.$confirm("此操作将永久删除该收藏, 是否继续?", {type: "warning"})
        await this.deleteCollect()
        if (this.currentUser) {
          await this.$router.push("/collects")
        } else {
          await this.$router.push(`/profile/${this.currentUser!.id}`)
        }
      } catch (e) {
        this.$message.info("已取消删除。")
      }
    }

    handleFork() {
      this.forkCollect()
    }

    //当用户编辑标签并提交更改成功后，需要从后台重新得到标签数据
    handleSubmit() {
      this.getCollect()
    }

    handleNewComment() {
      this.replyToComment = null
      this.newCommentDialogVisible = true
    }

    handleReplyComment(comment: Comment) {
      this.replyToComment = comment
      this.newCommentDialogVisible = true
    }

    //当新建评论后，需要刷新评论列表
    handleSubmitComment() {
      this.getCommentPage()
    }

    private async getCollect() {
      this.collect = await collectService.findById(this.collectId)
    }

    private async getCommentPage() {
      this.commentPage = await commentService.findAllByCollectId(this.collectId, this.pageableParam)
    }

    private async forkCollect() {
      try {
        await collectService.createFrom(this.collect!)
        this.$message.success("拷贝成功！")
      } catch (e) {
        this.$message.warning("拷贝失败！")
      }
    }

    private async deleteCollect() {
      try {
        await collectService.deleteById(this.collectId)
        this.$message.success("删除成功！")
      } catch (e) {
        this.$message.warning("删除失败！")
      }
    }
  }
</script>

<style scoped>

</style>
