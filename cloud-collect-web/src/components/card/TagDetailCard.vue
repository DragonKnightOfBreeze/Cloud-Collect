<template>
  <ElCard class="app-tag-detail-card">
    <template v-slot:header>{{tag.name}}</template>

    <ElRow class="app-item-list">
      <ElCol :span="4">创建者</ElCol>
      <ElCol :span="20">
        <ElRouterLink v-if="tag.user" :href="'/profile/'+tag.user.id">{{tag.user.nickname}}</ElRouterLink>
        <ElRouterLink disabled v-else>未知</ElRouterLink>
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
    <ElBlankLine :height="12"/>
    <ElRow class="app-summary">
      <ElCol>{{tag.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Tag} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine, ElRouterLink}
  })
  export default class TagDetailCard extends Vue {
    @Prop({required: true}) tag!: Tag
  }
</script>

<style scoped>

</style>
