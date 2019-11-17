<template>
  <ElCard class="app-history-overview-card">
    <ElRow type="flex" class="app-title align-items-center">
      <ElCol :span="12">
        {{history.collect.name}}
      </ElCol>
      <ElCol :span="4" :offset="8">
        <ElButton type="danger" v-if="isCurrentUser" @click="handleDelete">删除</ElButton>
      </ElCol>
    </ElRow>
    <ElRow type="flex" class="app-meta-small align-items-center">
      <ElCol :span="6">
        创建者&nbsp;
        <ElLink type="info" v-if="history.collect.user" :href="'/profile/'+history.collect.user.id">{{history.collect.user.nickname}}</ElLink>
        <ElLink type="info" disabled v-else>未知</ElLink>
      </ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {History} from "@/types"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component
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
