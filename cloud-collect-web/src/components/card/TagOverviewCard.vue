<template>
  <ElCard class="app-tag-overview-card">
    <template v-slot:header>
      <ElLink type="primary" :href="'/tags/'+tag.id">{{tag.name}}</ElLink>
    </template>

    <ElRow type="flex" class="app-meta-small align-items-center">
      <ElCol :span="6">
        创建者&nbsp;
        <ElLink type="info" v-if="tag.user" :href="'/profile/'+tag.user.id">{{tag.user.nickname}}</ElLink>
        <ElLink type="info" disabled v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6">
        创建时间 {{tag.createdTime}}
      </ElCol>
      <ElCol :span="6">
        修改时间 {{tag.lastModifiedTime}}
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="tag.collectCount">
          <ElLink type="info" :href="'/tags/'+tag.id">相关收藏</ElLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-summary">
      <ElCol>{{tag.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Tag} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class TagOverviewCard extends Vue {
    @Prop({required: true}) tag!: Tag
  }
</script>

<style scoped>

</style>
