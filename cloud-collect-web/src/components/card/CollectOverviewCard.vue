<template>
  <ElCard class="app-collect-overview-card">
    <template v-slot:header>
      <ElRow type="flex" class="align-items-center">
        <ElCol :span="1">
          <ElAvatar size="small" :src="collect.logoUrl" />
        </ElCol>
        <ElCol :span="11">
          <ElLink :href="'/collects/'+collect.id">{{collect.name}}</ElLink>
        </ElCol>
        <ElCol :span="4">
          <UrlCopyDropdown :collect="collect"></UrlCopyDropdown>
        </ElCol>
        <ElCol :span="4">
          <ElButton type="text" size="small" :href="collect.url">转到链接</ElButton>
        </ElCol>
        <ElCol :span="4">
          <PraiseButton v-if="currentUser" :collect="collect"></PraiseButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow type="flex" class="app-meta-small  align-items-center">
      <ElCol :span="6">
        创建者&nbsp;
        <ElLink type="info" v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElLink>
        <ElLink type="info" v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6">
        创建时间 {{collect.createdTime}}
      </ElCol>
      <ElCol :span="6">
        修改时间 {{collect.lastModifiedTime}}
      </ElCol>
      <ElCol :span="3">
        <ElBadge :value="collect.commentCount">
          <ElLink type="info" :href="'/collects/'+collect.id">评论</ElLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge :value="collect.praiseByUserCount">
          <ElLink type="info" :href="'/collects/'+collect.id+'/stargazers'">赞</ElLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow type="flex" class="app-meta align-items-center">
      <ElCol :span="6">
        分类
        <ElLink type="info" v-if="collect.category" :href="'/categories/'+collect.id">{{collect.category.name}}</ElLink>
        <ElLink type="info" v-else>未分类</ElLink>
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
  import PraiseButton from "@/components/button/PraiseButton.vue"
  import UrlCopyDropdown from "@/components/menu/UrlCopyDropdown.vue"
  import {Collect} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {PraiseButton, UrlCopyDropdown}
  })
  export default class CollectOverviewCard extends Vue {
    @Prop({required: true}) collect!: Collect

    get currentUser() {
      return this.$store.getters.currentUser
    }
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
