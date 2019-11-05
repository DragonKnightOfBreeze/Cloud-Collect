<template>
  <ElCard class="app-tag-detail-card">
    <template v-slot:header>{{tag.name}}</template>

    <ElRow class="app-item-list">
      <ElCol :span="4">创建者</ElCol>
      <ElCol :span="20">
        <ElLink v-if="tag.user" :href="'/profile/'+tag.user.id">{{tag.user.nickname}}</ElLink>
        <ElLink disabled v-else>未知</ElLink>
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">创建时间</ElCol>
      <ElCol :span="20">{{tag.createdTime}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">修改时间</ElCol>
      <ElCol :span="20">{{tag.lastModifiedTime}}</ElCol>
    </ElRow>
    <ElDivider/>
    <ElRow class="app-item-list">
      <ElCol>{{tag.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Tag} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class TagDetailCard extends Vue {
    @Prop() tag!: Tag
  }
</script>

<style scoped>

</style>
