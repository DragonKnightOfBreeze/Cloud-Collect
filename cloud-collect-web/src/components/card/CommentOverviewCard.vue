<template>
  <el-card class="app-comment-overview-card">
    <template v-slot:header>
      <el-row>
        <el-col :span="6">{{comment.sponsorByUser.nickname}}</el-col>
        <el-col :span="6">{{comment.createdTime}}</el-col>
        <el-col :span="4" :offset="8">
          <el-button @click="handleReply">回复</el-button>
        </el-col>
      </el-row>
    </template>

    <el-row class="app-content">
      <el-col>{{comment.content}}</el-col>
    </el-row>
  </el-card>
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
