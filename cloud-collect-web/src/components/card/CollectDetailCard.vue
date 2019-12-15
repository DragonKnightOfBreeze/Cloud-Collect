<template>
  <ElCard class="app-collect-detail-card">
    <template v-slot:header>
      <ElRow class="align-items-center">
        <ElCol :span="1">
          <ElAvatar shape="square" size="small" :src="collect.logoUrl"/>
        </ElCol>
        <ElCol :span="11">
          {{collect.name}}
        </ElCol>
        <ElCol :span="4">
          <UrlCopyDropdown :collect="collect"></UrlCopyDropdown>
        </ElCol>
        <ElCol :span="4">
          <ElLink type="primary" :href="collect.url"><ElIcon name="link"/> 转到链接</ElLink>
        </ElCol>
        <ElCol :span="4">
          <PraiseButton :collect="collect"></PraiseButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-item-list">
      <ElCol :span="4">分类</ElCol>
      <ElCol :span="20">
        <ElRouterLink type="info" v-if="collect.category" :href="'/categories/'+collect.id">{{collect.category.name}}</ElRouterLink>
        <ElRouterLink type="info" v-else disabled>未分类</ElRouterLink>
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">标签</ElCol>
      <ElCol :span="20">
        <ElTag size="small" v-for="tag in collect.tags" :key="tag.id">
          <ElRouterLink type="info" :href="'/tags/'+tag.id">{{tag.name}}</ElRouterLink>
        </ElTag>
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">类型</ElCol>
      <ElCol :span="20">
        <!--根据不同的收藏类型，显示不同的图标-->
        <template>
          <ElIcon v-if="collect.type==='DELAY'" name="time"/>
          <ElIcon v-if="collect.type==='IMPORT'" name="warning-outline"/>
          <ElIcon v-if="collect.type==='LOVE'" name="star-off"/>
          <ElIcon v-if="collect.type==='TODO'" name="edit"/>
        </template>
        {{collect.type | enumText(collectTypes)}}
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">创建者</ElCol>
      <ElCol :span="20">
        <ElRouterLink v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElRouterLink>
        <ElRouterLink v-else>未知</ElRouterLink>
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">创建时间</ElCol>
      <ElCol :span="20">{{collect.createdTime}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">修改时间</ElCol>
      <ElCol :span="20">{{collect.lastModifiedTime}}</ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">评论数量</ElCol>
      <ElCol :span="16">{{collect.commentCount}}</ElCol>
      <ElCol :span="4"><ElLink type="info" href="#comments">查看评论</ElLink></ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">收藏家数量</ElCol>
      <ElCol :span="16">{{collect.praiseByUserCount}}</ElCol>
      <ElCol :span="4">
        <ElRouterLink type="info" :href="'/collects/'+collect.id+'/stargazers'">查看收藏家</ElRouterLink>
      </ElCol>
    </ElRow>
    <ElBlankLine :height="12"/>
    <ElRow class="app-summary">
      <ElCol>{{collect.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import PraiseButton from "@/components/button/PraiseButton.vue"
  import UrlCopyDropdown from "@/components/menu/UrlCopyDropdown.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Collect, User} from "@/domain"
  import {collectTypes} from "@/enums"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine, ElRouterLink, PraiseButton, UrlCopyDropdown}
  })
  export default class CollectDetailCard extends Vue {
    @Prop({required: true}) collect!: Collect

    private collectTypes = collectTypes

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }
  }
</script>

<style scoped>

</style>
