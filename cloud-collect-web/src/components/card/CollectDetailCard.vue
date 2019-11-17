<template>
  <ElCard class="app-collect-detail-card">
    <template v-slot:header>
      <ElRow type="flex">
        <ElCol :span="1">
          <ElAvatar size="small" :src="collect.logoUrl"/>
        </ElCol>
        <ElCol :span="11">
          {{collect.name}}
        </ElCol>
        <ElCol :span="3">
          <UrlCopyDropdown :collect="collect"></UrlCopyDropdown>
        </ElCol>
        <ElCol :span="3">
          <ElLink type="info" :href="collect.url">转到链接</ElLink>
        </ElCol>
        <ElCol :span="3">
          <PraiseButton v-if="currentUser" :collect="collect"></PraiseButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-item-list">
      <ElCol :span="4">分类</ElCol>
      <ElCol :span="20">
        <ElLink type="info" v-if="collect.category" :href="'/categories/'+collect.id">{{collect.category.name}}</ElLink>
        <ElLink type="info" disabled v-else>未分类</ElLink>
      </ElCol>
    </ElRow>
    <ElRow class="app-item-list">
      <ElCol :span="4">标签</ElCol>
      <ElCol :span="20">
        <ElTag v-for="tag in collect.tags" :key="tag.id">
          <ElLink type="info" :href="'/tags/'+tag.id">{{tag.name}}</ElLink>
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
        <ElLink v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElLink>
        <ElLink disabled v-else>未知</ElLink>
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
    <ElDivider/>
    <ElRow class="app-item-list">
      <ElCol>{{collect.summary}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import PraiseButton from "@/components/button/PraiseButton.vue"
  import UrlCopyDropdown from "@/components/menu/UrlCopyDropdown.vue"
  import {collectTypes} from "@/enums"
  import {Collect, User} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {PraiseButton, UrlCopyDropdown}
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
