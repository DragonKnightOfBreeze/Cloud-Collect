<template>
  <ElCard class="app-comment-overview-card">
    <ElRow class="align-items-center">
      <ElCol :span="1" class="align-items-center">
        <ElAvatar size="small" v-if="comment.sponsorByUser.avatarUrl" :src="comment.sponsorByUser.avatarUrl"/>
        <ElAvatar size="small" v-else icon="el-icon-user-solid"/>
      </ElCol>
      <ElCol :span="11" class="app-comment-title align-items-center">
        <ElRouterLink :href="'/profile/'+comment.sponsorByUser.id">{{comment.sponsorByUser.nickname}}</ElRouterLink>
      </ElCol>
      <ElCol :span="8" class="app-meta align-items-center">
        {{comment.createdTime}}
      </ElCol>
      <ElCol :span="4" class="align-items-center">
        <ElButton type="text" size="small" @click="handleReply"><ElIcon name="chat-dot-square"/> 回复</ElButton>
      </ElCol>
    </ElRow>
    <ElRow class="app-content">
      <ElCol>{{comment.content}}</ElCol>
    </ElRow>

    <ElCard class="app-reply-overview-card" v-if="hasReply" :body-style="replyOverviewCardStyle">
      <ElRow class="align-items-center">
        <ElCol :span="1" class="align-items-center">
          <ElAvatar size="small" v-if="reply.sponsorByUser.avatarUrl" :src="reply.sponsorByUser.avatarUrl"/>
          <ElAvatar size="small" v-else icon="el-icon-user-solid"/>
        </ElCol>
        <ElCol :span="11" class="app-comment-title align-items-center">
          <ElRouterLink :href="'/profile/'+reply.sponsorByUser.id">{{reply.sponsorByUser.nickname}}</ElRouterLink>
        </ElCol>
        <ElCol :span="8" class="app-meta align-items-center">
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
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Comment} from "@/domain"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink}
  })
  export default class CommentOverviewCard extends Vue {
    @Prop({required: true}) comment!: Comment

    private reply = this.comment.replyToComment
    private replyOverviewCardStyle = {
      padding: "10px"
    }

    private get hasReply() {
      return !!this.reply
    }

    //传递当前收藏的发起用户到父组件
    @Emit("reply")
    private handleReply() {
    }
  }
</script>

<style scoped>
  .app-comment-title {
    font-size: 14px;
  }
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
