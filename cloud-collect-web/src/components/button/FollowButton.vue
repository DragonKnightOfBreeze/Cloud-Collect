<template>
  <div>
    <ElButton type="info" size="small" plain v-if="user.isFollowed===true" @click="handleUnfollow">已关注</ElButton>
    <ElButton type="primary" size="small" plain v-else-if="user.isFollowed===false" @click="handleFollow">关注</ElButton>
  </div>
</template>

<script lang="ts">
  import * as userService from "@/services/userService"
  import {User} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class FollowButton extends Vue {
    //经测试，只是单纯地更改prop的属性的值是可行的
    @Prop({required: true}) user!: User

    private async handleFollow() {
      try {
        await userService.follow(this.user.id!)
        this.user.isFollowed = true
      } catch (e) {
        this.$message.warning("关注失败！")
      }
    }

    private async handleUnfollow() {
      try {
        await userService.unfollow(this.user.id!)
        this.user.isFollowed = false
      } catch (e) {
        this.$message.warning("取消关注失败！")
      }
    }
  }
</script>

<style scoped>

</style>
