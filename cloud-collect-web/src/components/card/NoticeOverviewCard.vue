<template>
  <ElCard class="app-notice-overview-card">
    <template v-slot:header>
      <ElRow class="align-items-center">
        <ElCol :span="12">
          <ElRouterLink>{{notice.title}}</ElRouterLink>
        </ElCol>
        <ElCol :span="4" :offset="8" class="justify-content-end">
          <ElButton type="danger" size="small" v-if="isCurrentUser" @click="handleDelete">删除</ElButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta align-items-center">
      <ElCol :span="6">
        类型：{{notice.type | enumText(noticeTypes)}}
      </ElCol>
      <ElCol :span="7" class="align-items-center">
        创建时间：{{notice.createdTime}}
      </ElCol>
    </ElRow>
    <ElRow class="app-content">
      <!--通知的内容是Html文本，是来自后台的可信任的内容-->
      <ElCol v-html="notice.content"></ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Notice} from "@/domain"
  import {noticeTypes} from "@/enums"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink}
  })
  export default class NoticeOverviewCard extends Vue {
    @Prop({required: true}) notice!: Notice

    private noticeTypes = noticeTypes

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.notice.user && this.$store.getters.currentUser.id === this.notice.user.id
    }

    //删除操作委托给父组件
    @Emit("delete")
    private handleDelete() {
    }
  }
</script>

<style scoped>

</style>
