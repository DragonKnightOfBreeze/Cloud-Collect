<template>
  <div>
    <ElPageHeader title="返回总览页面" content="收藏详情" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <CollectDetailCard :collect="collect"/>

    <ElButtonGroup class="align-center" v-show="isCurrentUser">
      <ElButton type="success" @click="handleEdit">编辑</ElButton>
      <ElButton type="danger" @click="handleDelete">删除</ElButton>
    </ElButtonGroup>

    <EditCollectDialog :collect="collect" :visible.sync="editDialogVisible" @submit="handleSubmit"/>

    <el-collapse v-model="activeNames">
      <el-collapse-item name="0">
        <template v-slot:title>
          <el-row>
            <el-col :span="12">查看评论</el-col>
            <el-col :span="4" :offset="8">
              <el-button type="success" @click="handleNewComment">新评论</el-button>
            </el-col>
          </el-row>
        </template>

        <el-card-group v-if="commentPage && !commentPage.empty">
          <comment-overview-card v-for="comment in commentPage.content" :key="comment.id" :comment="comment"
                                 @reply="handleReplyComment(comment)"/>
          <the-pagination :page="commentPage" :pageable-param.sync="commentPageableParam"/>
        </el-card-group>
        <div v-else>
          没有评论。
        </div>
      </el-collapse-item>
    </el-collapse>

    <new-comment-dialog :visible.sync="newCommentDialogVisible" :collect="collect" :replyToComment="replyToComment"
                        @submit="handleSubmitComment"/>
  </div>
</template>

<script lang="ts">
  import CollectDetailCard from "@/components/card/CollectDetailCard.vue"
  import CommentOverviewCard from "@/components/card/CommentOverviewCard.vue"
  import EditCollectDialog from "@/components/dialog/EditCollectDialog.vue"
  import NewCommentDialog from "@/components/dialog/NewCommentDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as collectService from "@/services/collectService"
  import {Collect, Comment, Page, PageableParam, User} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {
      CommentOverviewCard,
      NewCommentDialog,
      ThePagination,
      ElCardGroup,
      CollectDetailCard,
      EditCollectDialog
    }
  })
  export default class CollectDetailOverview extends Vue {
    private collect!: Collect
    private editDialogVisible = false
    private activeNames = ["0"]
    private commentPage: Page<Comment> | null = null
    private commentPageableParam: PageableParam = {page: 0, size: 20, sort: []}
    private newCommentDialogVisible = false
    private replyToComment: Comment | null = null

    get collectId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }

    get isCurrentUser() {
      return this.currentUser && this.collect.user && this.currentUser.id == this.collect.user.id
    }

    created() {
      this.getCollect()
      this.getCommentPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getCollect()
      this.getCommentPage()
    }

    @Watch("commentPageableParam")
    private onCommentPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      this.getCommentPage()
    }

    handleEdit() {
      this.editDialogVisible = true
    }

    handleGoBack() {
      this.$router.push("/collects")
    }

    async handleDelete() {
      try {
        await this.$confirm("此操作将永久删除该收藏, 是否继续?", {type: "warning"})
        await this.deleteCollect()
        await this.$router.push(`/profile/${this.currentUser!.id}`)
      } catch (e) {
        this.$message.info("已取消删除。")
      }
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
      this.commentPage = await collectService.getCommentPage(this.collectId, this.commentPageableParam)
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
