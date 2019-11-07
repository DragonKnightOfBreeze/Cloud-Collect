<template>
  <ElCard class="app-category-detail-card">
    <template v-slot:header>{{category.name}}</template>

    <ElRow class="app-item-list">
      <ElCol :span="4">创建者</ElCol>
      <ElCol :span="20">
        <ElLink v-if="category.user" :href="'/profile/'+category.user.id">{{category.user.nickname}}</ElLink>
        <ElLink disabled v-else>未知</ElLink>
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">创建时间</ElCol>
      <ElCol :span="20">{{category.createdTime}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">修改时间</ElCol>
      <ElCol :span="20">{{category.lastModifiedTime}}</ElCol>
    </ElRow>
    <ElDivider/>
    <ElRow class="app-item-list">
      <ElCol :offset="1">{{category.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Category} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CategoryDetailCard extends Vue {
    @Prop({required: true}) category!: Category
  }
</script>

<style scoped>

</style>
