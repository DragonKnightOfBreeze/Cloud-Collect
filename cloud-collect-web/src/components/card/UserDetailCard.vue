<template>
  <ElCard class="app-user-detail-card">
    <ElRow class="app-item-list">
      <ElCol :span="4">昵称</ElCol>
      <ElCol :span="20">{{user.nickname}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">用户名</ElCol>
      <ElCol :span="20">{{user.username}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">邮箱</ElCol>
      <ElCol :span="20">{{user.email}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">注册时间</ElCol>
      <ElCol :span="20">{{user.registerTime}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">资料更新时间</ElCol>
      <ElCol :span="20">{{user.updateTime}}</ElCol>
    </ElRow>
    <ElBlankLine :height="12"/>
    <ElRow class="app-introduce">
      <ElCol>{{user.introduce}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import {User} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine}
  })
  export default class UserDetailCard extends Vue {
    @Prop({required: true}) user!: User
  }
</script>

<style scoped>

</style>
