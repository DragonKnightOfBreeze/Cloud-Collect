<template>
  <ElCard class="app-comment-overview-card">
    <ElRow class="app-title align-items-center">
      <ElCol :span="1">
        <ElAvatar size="small" :src="comment.sponsorByUser.avatarUrl"/>
      </ElCol>
      <ElCol :span="11">
        <ElLink :href="'/profile/'+comment.sponsorByUser.id">{{comment.sponsorByUser.nickname}}</ElLink>
      </ElCol>
      <ElCol :span="8" class="app-meta-small ">
        {{comment.createdTime}}
      </ElCol>
      <ElCol :span="4">
        <ElButton type="text" size="small" @click="handleReply">回复</ElButton>
      </ElCol>
    </ElRow>
    <ElRow class="app-content">
      <ElCol>{{comment.content}}</ElCol>
    </ElRow>

    <ElCard class="app-reply-overview-card" v-if="hasReply" :body-style="replyOverviewCardStyle">
      <ElRow class="app-title align-items-center">
        <ElCol :span="1">
          <ElAvatar size="small" :src="reply.sponsorByUser.avatarUrl"/>
        </ElCol>
        <ElCol :span="11">
          <ElLink :href="'/profile/'+reply.sponsorByUser.id">{{reply.sponsorByUser.nickname}}</ElLink>
        </ElCol>
        <ElCol :span="8" class="app-meta-small ">
          {{comment.createdTime}}
        </ElCol>
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

    private reply = this.comment.replyToComment
    private replyOverviewCardStyle = {
      padding: "15px"
    }

    private get hasReply() {
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
    border: 1px solid grey;
    border-radius: 4px;
  }
  .app-reply-overview-card {
    border: 1px solid darkgrey;
    border-radius: 4px;
    background-color: #e2e2e2;
  }
</style>
