<template>
  <ElCard class="app-collect-overview-card">
    <template v-slot:header>
      <ElRow>
        <ElCol :span="1">
          <ElAvatar size="small" :src="collect.logoUrl"/>
        </ElCol>
        <ElCol :span="11">
          <ElLink type="primary" :href="'/collects/'+collect.id">{{collect.name}}</ElLink>
        </ElCol>
        <ElCol :span="4" :offset="6">
          <ElLink :href="collect.url">源地址</ElLink>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta-small">
      <ElCol :span="6">
        创建者：
        <ElLink type="info" v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElLink>
        <ElLink type="info" disabled v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6">创建时间：{{collect.createdTime}}</ElCol>
      <ElCol :span="6">修改时间：{{collect.lastModifiedTime}}</ElCol>
      <ElCol :span="3"><ElBadge :value="collect.commentCount">评论</ElBadge></ElCol>
      <ElCol :span="3"><ElBadge :value="collect.praiseByUserCount">赞</ElBadge></ElCol>
    </ElRow>
    <ElRow class="app-meta">
      <ElCol :span="6">
        分类：
        {{collect.category | categoryName}}
      </ElCol>
      <ElCol :span="18">
        标签：
        <ElTag size="small" v-for="tag in collect.tags" :key="tag.id">{{tag.name}}</ElTag>
      </ElCol>
    </ElRow>
    <ElText class="app-summary">
      {{collect.summary}}
    </ElText>
  </ElCard>
</template>

<script lang="ts">
  import EditTagDialog from "@/components/dialog/EditTagDialog.vue"
  import ElText from "@/components/public/ElText.vue"
  import {Collect} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {EditTagDialog, ElText}
  })
  export default class CollectOverviewCard extends Vue {
    @Prop() collect!: Collect
  }
</script>

<style scoped>

</style>
