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
        <ElCol :span="3">
          <!--TODO-->
          <ElDropdown>
            <ElLink type="info">复制链接<ElIcon name="arrow-down"/></ElLink>
            <ElDropdownMenu slot="dropdown">
            </ElDropdownMenu>
          </ElDropdown>
        </ElCol>
        <ElCol :span="3">
          <ElLink type="info" :href="collect.url">转到链接</ElLink>
        </ElCol>
        <ElCol :span="3">
          <!--TODO-->
          <ElButton type="primary">点赞</ElButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta-small">
      <ElCol :span="6">
        创建者
        <ElLink type="info" v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElLink>
        <ElLink type="info" disabled v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6">创建时间 {{collect.createdTime}}</ElCol>
      <ElCol :span="6">修改时间 {{collect.lastModifiedTime}}</ElCol>
      <ElCol :span="3"><ElBadge :value="collect.commentCount">评论</ElBadge></ElCol>
      <ElCol :span="3"><ElBadge :value="collect.praiseByUserCount">赞</ElBadge></ElCol>
    </ElRow>
    <ElRow class="app-meta">
      <ElCol :span="6">
        分类
        <ElLink type="info" v-if="collect.category" :href="'/categories/'+collect.id">{{collect.category.name}}</ElLink>
        <ElLink type="info" disabled v-else>未分类</ElLink>
      </ElCol>
      <ElCol :span="18">
        标签
        <ElTag size="small" v-for="tag in collect.tags" :key="tag.id">
          <ElLink type="info" :href="'/tags/'+tag.id">{{tag.name}}</ElLink>
        </ElTag>
      </ElCol>
    </ElRow>
    <ElRow class="app-summary">
      <ElCol>{{collect.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Collect} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CollectOverviewCard extends Vue {
    @Prop() collect!: Collect
  }
</script>

<style scoped>
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>
