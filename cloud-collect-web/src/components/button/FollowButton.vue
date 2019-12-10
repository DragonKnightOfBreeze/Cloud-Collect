<template>
  <div>
    <ElButton type="info" size="small" plain v-if="user.followed===true" @click="handleUnfollow">
      <ElIcon name="star-on"/> 已关注
    </ElButton>
    <ElButton type="primary" size="small" plain v-else-if="user.followed===false" @click="handleFollow">
      <ElIcon name="star-off"/> 关注
    </ElButton>
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
        this.user.followed = true
      } catch (e) {
        this.$message.warning("关注失败！")
      }
    }

    private async handleUnfollow() {
      try {
        await userService.unfollow(this.user.id!)
        this.user.followed = false
      } catch (e) {
        this.$message.warning("取消关注失败！")
      }
    }
  }
</script>

<style scoped>

</style>
