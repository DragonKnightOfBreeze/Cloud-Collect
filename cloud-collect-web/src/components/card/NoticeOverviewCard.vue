<template>
  <ElCard class="app-notice-overview-card">
    <ElRow type="flex" class="app-title align-items-center">
      <ElCol :span="12">
        {{notice.title}}
      </ElCol>
      <ElCol :span="4" :offset="8">
        <ElButton type="danger" v-if="isCurrentUser" @click="handleDelete">删除</ElButton>
      </ElCol>
    </ElRow>
    <ElRow type="flex" class="app-meta-small align-items-center">
      <ElCol :span="6">
        类型 {{notice.type | enumText(noticeTypes)}}
      </ElCol>
      <ElCol :span="6">
        创建时间 {{notice.createdTime}}
      </ElCol>
    </ElRow>
    <ElRow class="app-content">
      <ElCol>{{notice.content}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {noticeTypes} from "@/enums"
  import {Notice} from "@/types"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class NoticeOverviewCard extends Vue {
    @Prop({required: true}) notice!: Notice

    private noticeTypes = noticeTypes

    get isCurrentUser() {
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
