<template>
  <ElCard class="app-category-overview-card">
    <template v-slot:header>
      <ElRouterLink :href="'/categories/'+category.id">{{category.name}}</ElRouterLink>
    </template>

    <ElRow class="app-meta align-items-center">
      <ElCol :span="6" class="align-items-center">
        创建者：
        <ElRouterLink type="info" v-if="category.user" :href="'/profile/'+category.user.id">{{category.user.nickname}}</ElRouterLink>
        <ElRouterLink type="info" disabled v-else>未知</ElRouterLink>
      </ElCol>
      <ElCol :span="6" class="align-items-center">
        创建时间：{{category.createdTime}}
      </ElCol>
      <ElCol :span="6" class="align-items-center">
        修改时间：{{category.lastModifiedTime}}
      </ElCol>
      <ElCol :span="3" class="align-items-center">
        <ElBadge type="primary" :value="category.collectCount">
          <ElRouterLink type="info" :href="'/categories/'+category.id">相关收藏</ElRouterLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-summary">
      <ElCol>{{category.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Category} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink}
  })
  export default class CategoryOverviewCard extends Vue {
    @Prop({required: true}) category!: Category
  }
</script>

<style scoped>

</style>
