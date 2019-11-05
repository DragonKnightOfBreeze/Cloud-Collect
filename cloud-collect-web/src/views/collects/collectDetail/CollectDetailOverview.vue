<template>
  <div>
    <ElPageHeader title="返回总览页面" content="收藏详情" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <CollectDetailCard :collect="collect"/>

    <ElButtonGroup class="align-center" v-show="isCurrentUser">
      <ElButton type="success" @click="handleEdit">编辑</ElButton>
      <ElButton type="danger" @click="handleDelete">删除</ElButton>
    </ElButtonGroup>

    <EditCollectDialog :visible.sync="editDialogVisible" @submit="handleSubmit"/>

    <!--TODO 评论列表-->
  </div>
</template>

<script lang="ts">
  import CollectDetailCard from "@/components/card/CollectDetailCard.vue"
  import EditCollectDialog from "@/components/dialog/EditCollectDialog.vue"
  import * as collectService from "@/services/collectService"
  import {Collect, User} from "@/types"
  import {Component, Prop, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {CollectDetailCard, EditCollectDialog}
  })
  export default class CollectDetailOverview extends Vue {
    @Prop() collect!: Collect

    private editDialogVisible = false

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
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getCollect()
    }

    handleEdit() {
      this.editDialogVisible = true
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

    handleGoBack() {
      this.$router.push("/collects")
    }

    private async getCollect() {
      this.collect = await collectService.findById(this.collectId)
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
