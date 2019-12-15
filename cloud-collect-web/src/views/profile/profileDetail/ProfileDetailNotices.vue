<template>
  <div>
    <h3>全部通知</h3>
    <ElDivider/>
    <!--仅当用户为当前用户时，才会显示以下内容-->
    <template v-if="isCurrentUser">
      <ElRow :gutter="5">
        <ElCol :span="4" :offset="20" class="justify-content-end">
          <ElButton type="danger" @click="handleDeleteAll"><ElIcon name="close"/> 清空通知</ElButton>
        </ElCol>
      </ElRow>

      <ElCardGroup v-if="isCurrentUser && noticePage">
        <NoticeOverviewCard v-for="notice in noticePage.content" :key="notice.id" :notice="notice"
                            @delete="handleDelete(notice.id)"/>
        <ThePagination :page="noticePage" :pageable-param.sync="pageableParam"/>
      </ElCardGroup>
    </template>
    <template v-else>
      <NoContentCard>
        没有要显示的内容。
      </NoContentCard>
    </template>
  </div>
</template>

<script lang="ts">
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import NoticeOverviewCard from "@/components/card/NoticeOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import {Notice, Page, PageableParam} from "@/domain"
  import * as noticeService from "@/services/noticeService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {NoContentCard, ThePagination, NoticeOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailNotices extends Vue {
    private pageableParam: PageableParam = {page: 0, size: 20}
    private noticePage: Page<Notice> | null = null

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getNoticePage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      if (value.params.id === oldValue.params.id) return
      this.getNoticePage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getNoticePage()
    }

    private async handleDelete(id: number) {
      await this.deleteNoticeById(id)
      await this.getNoticePage()
    }

    private async handleDeleteAll() {
      try {
        await this.$confirm("确定清空通知？", {type: "warning"})
        await this.deleteAllNotice()
        await this.getNoticePage()
      } catch (e) {
        //忽略
      }
    }

    private async deleteNoticeById(id: number) {
      try {
        await noticeService.deleteById(id)
      } catch (e) {
        this.$message.warning("删除失败！")
      }
    }

    private async deleteAllNotice() {
      try {
        await noticeService.deleteAllByUserId(this.userId)
      } catch (e) {
        this.$message.warning("清空失败！")
      }
    }

    private async getNoticePage() {
      try {
        this.noticePage = await noticeService.findAllByUserId(this.userId, this.pageableParam)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
