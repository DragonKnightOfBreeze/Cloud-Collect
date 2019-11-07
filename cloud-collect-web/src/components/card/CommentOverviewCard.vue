<template>
  <ElCard class="app-comment-overview-card">
    <template v-slot:header>
      <ElRow>
        <ElCol :span="6">{{comment.sponsorByUser.nickname}}</ElCol>
        <ElCol :span="6">{{comment.createdTime}}</ElCol>
        <ElCol :span="4" :offset="8">
          <ElButton @click="handleReply">回复</ElButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-content">
      <ElCol>{{comment.content}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Comment} from "@/types"
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CommentOverviewCard extends Vue {
    @Prop() comment!: Comment

    //传递当前收藏的发起用户到父组件
    @Emit("reply")
    handleReply() {
    }
  }
</script>

<style scoped>

</style>
