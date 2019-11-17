<template>
  <ElCard class="app-category-overview-card">
    <template v-slot:header>
      <ElLink type="primary" :href="'/categories/'+category.id">{{category.name}}</ElLink>
    </template>

    <ElRow type="flex" class="app-meta-small align-items-center">
      <ElCol :span="6">
        创建者&nbsp;
        <ElLink type="info" v-if="category.user" :href="'/profile/'+category.user.id">{{category.user.nickname}}</ElLink>
        <ElLink type="info" disabled v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6">
        创建时间 {{category.createdTime}}
      </ElCol>
      <ElCol :span="6">
        修改时间 {{category.lastModifiedTime}}
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="category.collectCount">
          <ElLink type="info" :href="'/categories/'+category.id">相关收藏</ElLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-summary">
      <ElCol>{{category.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Category} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CategoryOverviewCard extends Vue {
    @Prop({required: true}) category!: Category
  }
</script>

<style scoped>

</style>
