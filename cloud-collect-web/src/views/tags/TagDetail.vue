<template>
  <div>
    <ElPageHeader title="返回总览页面" content="标签详情" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <template v-if="tag">
      <TagDetailCard :tag="tag"/>

      <div class="app-button-group align-center" v-if="isCurrentUser">
        <ElButton type="success" @click="handleEdit"><ElIcon name="edit"/> 编辑</ElButton>
        <ElButton type="danger" @click="handleDelete"><ElIcon name="delete"/> 删除</ElButton>
      </div>

      <EditTagDialog :tag="tag" :visible.sync="editDialogVisible" @submit="handleSubmit"/>

      <ElCollapse v-model="activeNames" @change="handleChange">
        <ElCollapseItem name="1" title="查看相关收藏">
          <ElRow class="align-items-center">
            <ElCol :span="20">
              相关收藏列表
            </ElCol>
          </ElRow>

          <ElCardGroup v-if="collectPage && !collectPage.empty">
            <TheSorter type="collect" :pageable-param.sync="pageableParam"/>
            <CollectOverviewCard v-for="collect in collectPage.content" :key=collect.id :collect="collect"/>
            <ThePagination :page="collectPage" :pageable-param.sync="pageableParam"/>
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
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import TagDetailCard from "@/components/card/TagDetailCard.vue"
  import EditTagDialog from "@/components/dialog/EditTagDialog.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Collect, Page, PageableParam, Tag, User} from "@/domain"
  import * as collectService from "@/services/collectService"
  import * as tagService from "@/services/tagService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {
      TheSorter,
      NoContentCard,
      EditTagDialog,
      CollectOverviewCard,
      TagDetailCard,
      ElCardGroup,
      ThePagination
    }
  })
  export default class TagDetail extends Vue {
    private tag: Tag | null = null
    private editDialogVisible = false
    private activeNames = []
    private collectPage: Page<Collect> | null = null
    private pageableParam: PageableParam = {page: 0, size: 20}

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
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      if (value.params.id === oldValue.params.id) return
      this.getTag()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getCollectPage()
    }

    private handleGoBack() {
      this.$router.push("/tags")
    }

    private handleChange() {
      if (!this.getCollectPage()) {
        this.getCollectPage()
      }
    }

    private handleEdit() {
      this.editDialogVisible = true
    }

    private async handleDelete() {
      try {
        await this.$confirm("此操作将永久删除该标签, 是否继续?", {type: "warning"})
        await this.deleteTag()
        if (this.currentUser) {
          await this.$router.push("/tags")
        } else {
          await this.$router.push(`/profile/${this.currentUser!.id}`)
        }
      } catch (e) {
        //忽略
      }

    }

    //当用户编辑标签并提交更改成功后，需要从后台重新得到标签数据
    private handleSubmit() {
      this.getTag()
    }

    private async getTag() {
      this.tag = await tagService.findById(this.tagId)
    }

    private async getCollectPage() {
      this.collectPage = await collectService.findAllByTagId(this.tagId, this.pageableParam)
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
