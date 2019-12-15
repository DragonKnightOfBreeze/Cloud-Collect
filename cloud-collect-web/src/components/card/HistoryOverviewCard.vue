<template>
  <ElCard class="app-history-overview-card">
    <ElRow class="app-title align-items-center">
      <ElCol :span="12">
        <ElRouterLink :href="'/collects/'+history.collect.id">{{history.collect.name}}</ElRouterLink>
      </ElCol>
      <ElCol :span="4" :offset="8" class="justify-content-end">
        <ElButton type="danger" size="small" v-if="isCurrentUser" @click="handleDelete"><ElIcon name="delete"/> 删除</ElButton>
      </ElCol>
    </ElRow>
    <ElRow class="app-meta align-items-center">
      <ElCol :span="6" class="align-items-center">
        创建者：
        <ElRouterLink type="info" v-if="history.collect.user" :href="'/profile/'+history.collect.user.id">{{history.collect.user.nickname}}</ElRouterLink>
        <ElRouterLink type="info" disabled v-else>未知</ElRouterLink>
      </ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {History} from "@/domain"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink}
  })
  export default class HistoryOverviewCard extends Vue {
    @Prop({required: true}) history!: History

    get isCurrentUser() {
      return this.$store.getters.currentUser && this.history.user && this.$store.getters.currentUser.id === this.history.user.id
    }

    //删除操作委托给父组件
    @Emit("delete")
    private handleDelete() {
    }
  }
</script>

<style scoped>

</style>
