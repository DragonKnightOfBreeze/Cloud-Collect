<template>
  <ElCard class="app-comment-overview-card">
    <ElRow type="flex" class="app-title align-items-center">
      <ElCol :span="1">
        <ElAvatar size="small" :src="comment.sponsorByUser.avatarUrl"/>
      </ElCol>
      <ElCol :span="11">
        <ElLink type="primary" :href="'/profile/'+comment.sponsorByUser.id">{{comment.sponsorByUser.nickname}}</ElLink>
      </ElCol>
      <ElCol :span="4" :offset="8">
        <ElButton @click="handleReply">回复</ElButton>
      </ElCol>
    </ElRow>
    <ElRow type="flex" class="app-meta-small align-items-center">
      <ElCol :span="6">创建时间 {{comment.createdTime}}</ElCol>
    </ElRow>
    <ElRow class="app-content">
      <ElCol>{{comment.content}}</ElCol>
    </ElRow>

    <ElCard class="app-reply-overview-card" v-if="hasReply">
      <ElRow type="flex" class="app-title align-items-center">
        <ElCol :span="1">
          <ElAvatar size="small" :src="reply.sponsorByUser.avatarUrl"/>
        </ElCol>
        <ElCol :span="11">
          <ElLink type="primary" :href="'/profile/'+reply.sponsorByUser.id">{{reply.sponsorByUser.nickname}}</ElLink>
        </ElCol>
      </ElRow>
      <ElRow type="flex" class="app-meta-small align-items-center">
        <ElCol :span="6">创建时间 {{reply.createdTime}}</ElCol>
      </ElRow>
      <ElRow class="app-content">
        <ElCol>{{reply.content}}</ElCol>
      </ElRow>
    </ElCard>
  </ElCard>
</template>

<script lang="ts">
  import {Comment} from "@/types"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CommentOverviewCard extends Vue {
    @Prop({required: true}) comment!: Comment

    reply = this.comment.replyToComment

    get hasReply() {
      return !!this.reply
    }

    //传递当前收藏的发起用户到父组件
    @Emit("reply")
    handleReply() {
    }
  }
</script>

<style scoped>
  .app-comment-overview-card {

  }
  .app-reply-overview-card {
    background-color: #cccccc;
  }
</style>
