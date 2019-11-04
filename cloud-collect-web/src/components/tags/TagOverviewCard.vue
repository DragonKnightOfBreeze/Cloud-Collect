<template>
  <ElCard class="app-tag-overview-item">
    <template v-slot:header>
      <ElLink type="primary" :href="'/tags/'+tag.id">{{tag.name}}</ElLink>
    </template>

    <div class="app-meta">
      <ElRow>
        <ElCol :span="6">创建时间 {{tag.createdTime}}</ElCol>
        <ElCol :span="6">修改时间 {{tag.lastModifiedTime}}</ElCol>
        <ElCol :span="2"><ElBadge :value="tag.collectCount">相关收藏</ElBadge></ElCol>
      </ElRow>
    </div>
    <div class="app-summary">
      <ElText>{{tag.summary}}</ElText>
    </div>
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
  .app-tag-overview-item {
    margin: 5px 5px;
  }
</style>
