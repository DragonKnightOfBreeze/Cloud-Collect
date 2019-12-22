<template>
  <ElCard class="app-category-detail-card">
    <template v-slot:header>{{category.name}}</template>

    <ElRow class="app-item-list">
      <ElCol :span="4">创建者</ElCol>
      <ElCol :span="20">
        <ElRouterLink v-if="category.user" :href="'/profile/'+category.user.id">{{category.user.nickname}}</ElRouterLink>
        <ElRouterLink disabled v-else>未知</ElRouterLink>
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
    <ElBlankLine :height="12"/>
    <ElRow class="app-summary">
      <ElCol>{{category.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Category} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine, ElRouterLink}
  })
  export default class CategoryDetailCard extends Vue {
    @Prop({required: true}) category!: Category
  }
</script>

<style scoped>

</style>
