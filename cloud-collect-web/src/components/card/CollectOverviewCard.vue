<template>
  <ElCard class="app-collect-overview-card">
    <template v-slot:header>
      <ElRow class="align-items-center">
        <ElCol :span="1">
          <ElAvatar shape="square" size="small" :src="collect.logoUrl"/>
        </ElCol>
        <ElCol :span="11">
          <ElRouterLink :href="'/collects/'+collect.id">{{collect.name}}</ElRouterLink>
        </ElCol>
        <ElCol :span="4">
          <UrlCopyDropdown :collect="collect"></UrlCopyDropdown>
        </ElCol>
        <ElCol :span="4">
          <ElLink type="primary" size="small" :href="collect.url"><ElIcon name="link"/> 转到链接</ElLink>
        </ElCol>
        <ElCol :span="4">
          <PraiseButton :collect="collect"></PraiseButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta align-items-center">
      <ElCol :span="6" class="align-items-center">
        创建者：
        <ElRouterLink type="info" v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElRouterLink>
        <ElRouterLink type="info" disabled v-else>未知</ElRouterLink>
      </ElCol>
      <ElCol :span="7" class="align-items-center">
        创建时间：{{collect.createdTime}}
      </ElCol>
      <ElCol :span="7" class="align-items-center">
        修改时间：{{collect.lastModifiedTime}}
      </ElCol>
      <ElCol :span="2" class="align-items-center">
        <ElBadge :value="collect.commentCount">
          <ElRouterLink type="info" :href="'/collects/'+collect.id+'#comments'">评论</ElRouterLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="2" class="align-items-center">
        <ElBadge :value="collect.praiseByUserCount">
          <ElRouterLink type="info" :href="'/collects/'+collect.id+'/stargazers'">赞</ElRouterLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-meta align-items-center">
      <ElCol :span="6" class="align-items-center">
        分类：
        <ElRouterLink type="info" v-if="collect.category" :href="'/categories/'+ collect.category.id">
          {{collect.category.name}}
        </ElRouterLink>
        <ElRouterLink type="info" v-else disabled>未分类</ElRouterLink>
      </ElCol>
      <ElCol :span="18" class="align-items-center">
        标签：
        <ElTag size="small" type="info" v-for="tag in collect.tags" :key="tag.id">
          <ElRouterLink type="info" :href="'/tags/'+tag.id">{{tag.name}}</ElRouterLink>
        </ElTag>
        <ElRouterLink type="info" disabled v-if="collect.tags.length === 0">无标签</ElRouterLink>
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
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Collect} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink, PraiseButton, UrlCopyDropdown}
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
