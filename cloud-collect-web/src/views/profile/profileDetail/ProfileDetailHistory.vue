<template>
  <div>
    <h3>浏览历史</h3>
    <ElDivider/>

    <!--仅当用户为当前用户时，才会显示以下内容-->
    <ElRow v-show="isCurrentUser">
      <ElCol :span="4" :offset="20">
        <ElButton type="danger" @click="handleDeleteAll">清空通知</ElButton>
      </ElCol>
    </ElRow>

    <!--使用无限滚动加载，需要判断是否有更多数据-->
    <ElTimeline>
      <!--避免与dom自带的history属性冲突-->
      <ElTimelineItem v-for="item in histories" :key="item.id" :timestamp="item.createdTime"
                      v-infinite-scroll="handleInfiniteScroll">
        <HistoryOverviewCard :history="item" @delete="handleDelete(item.id)"/>
      </ElTimelineItem>
    </ElTimeline>
  </div>
</template>

<script lang="ts">
  import HistoryOverviewCard from "@/components/card/HistoryOverviewCard.vue"
  import * as historyService from "@/services/historyService"
  import {History, PageableParam} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {HistoryOverviewCard}
  })
  export default class ProfileDetailHistory extends Vue {
    private pageableParam: PageableParam = {page: 0, size: 20}
    private histories: History[] = []
    private hasMoreData = true

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getHistories()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      this.getHistories()
    }

    private async handleDelete(id: number) {
      await this.deleteHistoryById(id)
      await this.getHistories()
    }

    private async handleDeleteAll() {
      await this.$confirm("确定清空通知？", {type: "warning"})
      await this.deleteAllHistory()
      this.histories = []
    }

    private async handleInfiniteScroll() {
      if (this.hasMoreData) {
        await this.getHistories()
      }
    }

    private async deleteHistoryById(id: number) {
      try {
        await historyService.deleteById(id)
      } catch (e) {
        this.$message.warning("删除失败！")
      }
    }

    private async deleteAllHistory() {
      try {
        await historyService.deleteAllByUserId(this.userId)
      } catch (e) {
        this.$message.warning("清空失败！")
      }
    }

    private async getHistories() {
      try {
        const historyPage = await historyService.findAllByUserId(this.userId, this.pageableParam)
        if (historyPage.last) {
          this.hasMoreData = false
        }
        this.histories.push(...historyPage.content)
        this.pageableParam.page += 1
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
