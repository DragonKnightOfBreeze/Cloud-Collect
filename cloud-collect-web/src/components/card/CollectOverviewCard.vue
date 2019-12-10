<template>
  <ElCard class="app-collect-overview-card">
    <template v-slot:header>
      <ElRow class="align-items-center">
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
          <ElButton type="text" size="small" :href="collect.url"><ElIcon name="link"/> 转到链接</ElButton>
        </ElCol>
        <ElCol :span="4">
          <PraiseButton :collect="collect"></PraiseButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta-small align-items-center">
      <ElCol :span="6" class="align-items-center">
        创建者：
        <ElLink type="info" v-if="collect.user" :href="'/profile/'+collect.user.id">{{collect.user.nickname}}</ElLink>
        <ElLink type="info" v-else>未知</ElLink>
      </ElCol>
      <ElCol :span="6" class="align-items-center">
        创建时间：{{collect.createdTime}}
      </ElCol>
      <ElCol :span="6" class="align-items-center">
        修改时间：{{collect.lastModifiedTime}}
      </ElCol>
      <ElCol :span="3" class="align-items-center">
        <ElBadge :value="collect.commentCount">
          <ElLink type="info" :href="'/collects/'+collect.id">评论</ElLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3" class="align-items-center">
        <ElBadge :value="collect.praiseByUserCount">
          <ElLink type="info" :href="'/collects/'+collect.id+'/stargazers'">赞</ElLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-meta-small align-items-center">
      <ElCol :span="6">
        分类：
        <ElTag size="small" type="primary" v-if="category" @click="handleGoCategory(category.id)">
          {{category.name}}
        </ElTag>
        <ElTag size="small" type="primary" v-else>
          未分类
        </ElTag>
      </ElCol>
      <ElCol :span="18">
        标签：
        <ElTag size="small" type="info" v-for="tag in collect.tags" :key="tag.id" @click="handleGoTag(tag.id)">
          {{tag.name}}
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

    get category() {
      return this.collect.category
    }

    get tags() {
      return this.collect.tags
    }

    private handleGoCategory(categoryId: number) {
      this.$router.replace(`/categories/${categoryId}`)
    }

    private handleGoTag(tagId: number) {
      this.$router.replace(`/tags/${tagId}`)
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
