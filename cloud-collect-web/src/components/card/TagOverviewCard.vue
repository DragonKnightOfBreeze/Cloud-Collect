<template>
  <ElCard class="app-tag-overview-card">
    <template v-slot:header>
      <ElRouterLink :href="'/tags/'+tag.id">{{tag.name}}</ElRouterLink>
    </template>

    <ElRow class="app-meta align-items-center">
      <ElCol :span="6" class="align-items-center">
        创建者：
        <ElRouterLink type="info" v-if="tag.user" :href="'/profile/'+tag.user.id">{{tag.user.nickname}}</ElRouterLink>
        <ElRouterLink type="info" disabled v-else>未知</ElRouterLink>
      </ElCol>
      <ElCol :span="6" class="align-items-center">
        创建时间：{{tag.createdTime}}
      </ElCol>
      <ElCol :span="6" class="align-items-center">
        修改时间：{{tag.lastModifiedTime}}
      </ElCol>
      <ElCol :span="3" class="align-items-center">
        <ElBadge type="primary" :value="tag.collectCount">
          <ElRouterLink type="info" :href="'/tags/'+tag.id">相关收藏</ElRouterLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-summary">
      <ElCol>{{tag.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Tag} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink}
  })
  export default class TagOverviewCard extends Vue {
    @Prop({required: true}) tag!: Tag
  }
</script>

<style scoped>

</style>
