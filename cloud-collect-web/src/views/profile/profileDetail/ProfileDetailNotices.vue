<template>
  <div>
    <h3>全部通知</h3>
    <ElDivider/>

    <!--仅当用户为当前用户时，才会显示以下内容-->
    <ElRow v-if="isCurrentUser">
      <ElCol :span="4" :offset="20">
        <ElButton type="danger" @click="handleDeleteAll">清空通知</ElButton>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="isCurrentUser">
      <NoticeOverviewCard v-for="notice in notices" :key="notice.id" :notice="notice"
                          @delete="handleDelete(notice.id)"/>
      <ThePagination :page="noticePage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import NoticeOverviewCard from "@/components/card/NoticeOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as noticeService from "@/services/noticeService"
  import {Notice, Page, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {ThePagination, NoticeOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailNotices extends Vue {
    private pageableParam: PageableParam = {page: 0, size: 20}
    private noticePage: Page<Notice> | null = null

    private get notices() {
      return this.noticePage ? this.noticePage.content : []
    }

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
      this.getNoticePage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.getNoticePage()
    }

    private async handleDelete(id: number) {
      await this.deleteNoticeById(id)
      await this.getNoticePage()
    }

    private async handleDeleteAll() {
      await this.$confirm("确定清空通知？", {type: "warning"})
      await this.deleteAllNotice()
      await this.getNoticePage()
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
