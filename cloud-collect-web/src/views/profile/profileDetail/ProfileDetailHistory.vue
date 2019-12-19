<template>
  <div>
    <h3>浏览历史</h3>
    <ElDivider/>
    <!--仅当用户为当前用户时，才会显示以下内容-->
    <template v-if="isCurrentUser">
      <ElRow :gutter="5">
        <ElCol :span="4" :offset="20" class="justify-content-end">
          <ElButton type="danger" @click="handleDeleteAll"><ElIcon name="close"/> 清空历史</ElButton>
        </ElCol>
      </ElRow>

      <ElTimeline>
        <!--避免与dom自带的history属性冲突-->
        <ElTimelineItem v-for="item in histories" :key="item.id" :timestamp="item.createdTime">
          <HistoryOverviewCard :history="item" @delete="handleDelete(item.id)"/>
        </ElTimelineItem>
      </ElTimeline>

      <!--不要使用无线滚动，可能会导致栈溢出-->
      <div class="app-button-group align-center">
        <ElButton :loading="loadingHistories" :disabled="!hasMoreHistories" @click="handleLoading">加载更多</ElButton>
      </div>
    </template>
    <template v-else>
      <NoContentCard>
        没有要显示的内容。
      </NoContentCard>
    </template>
  </div>
</template>

<script lang="ts">
  import HistoryOverviewCard from "@/components/card/HistoryOverviewCard.vue"
  import NoContentCard from "@/components/card/NoContentCard.vue"
  import {History, PageableParam} from "@/domain"
  import * as historyService from "@/services/historyService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {NoContentCard, HistoryOverviewCard}
  })
  export default class ProfileDetailHistory extends Vue {
    private pageableParam: PageableParam = {page: 0, size: 20}
    private histories: History[] = []
    private loadingHistories = false
    private hasMoreHistories = true

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
      console.log("路由发生了变化：", value)
      //总是更新数据
      this.getHistories()
    }

    private async handleDelete(id: number) {
      await this.deleteHistoryById(id)
      await this.getHistories()
    }

    private async handleDeleteAll() {
      try {
        await this.$confirm("确定清空通知？", {type: "warning"})
        await this.deleteAllHistory()
        this.histories = []
      } catch (e) {
        //忽略
      }
    }

    private async handleLoading() {
      console.log("加载更多浏览记录。")
      this.loadingHistories = true
      await this.addHistories()
      this.loadingHistories = false
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
        this.$message.success("清空成功！")
      } catch (e) {
        this.$message.warning("清空失败！")
      }
    }

    private async getHistories() {
      this.histories = []
      this.pageableParam = {page: 0, size: 20}
      try {
        const historyPage = await historyService.findAllByUserId(this.userId, this.pageableParam)
        if (historyPage.last) this.hasMoreHistories = false
        this.histories.push(...historyPage.content)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }

    private async addHistories() {
      try {
        this.pageableParam.page++
        const historyPage = await historyService.findAllByUserId(this.userId, this.pageableParam)
        if (historyPage.last) this.hasMoreHistories = false
        this.histories.push(...historyPage.content)
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
