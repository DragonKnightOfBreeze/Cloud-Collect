<template>
  <ElCard class="app-tag-overview-card">
    <template v-slot:header>
      <ElLink type="primary" :href="'/tags/'+tag.id">{{tag.name}}</ElLink>
    </template>

    <ElRow class="app-meta-small">
      <ElCol :span="6">
        创建者：
        <ElLink type="info" v-if="tag.user" :href="'/profile/'+tag.user.id">{{tag.user.nickname}}</ElLink>
        <ElLink type="info" disabled v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6">创建时间：{{tag.createdTime}}</ElCol>
      <ElCol :span="6">修改时间：{{tag.lastModifiedTime}}</ElCol>
      <ElCol :span="3"><ElBadge type="primary" :value="tag.collectCount">相关收藏</ElBadge></ElCol>
    </ElRow>
    <ElText class="app-summary">
      {{tag.summary}}
    </ElText>
  </ElCard>
</template>

<script lang="ts">
  import ElText from "@/components/public/ElText.vue"
  import {Tag} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElText}
  })
  export default class TagOverviewCard extends Vue {
    @Prop() tag!: Tag
  }
</script>

<style scoped>

</style>
