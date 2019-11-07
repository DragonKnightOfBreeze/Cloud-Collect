<template>
  <div>
    <ElButton type="primary" v-if="!isFollowed" @click="handleFollow">关注</ElButton>
    <ElButton type="info" v-else @click="handleUnfollow">已关注</ElButton>
  </div>
</template>

<script lang="ts">
  import * as userService from "@/services/userService"
  import {User} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class FollowButton extends Vue {
    @Prop({required: true}) user!: User

    isFollowed: boolean = false

    created() {
      this.getIsFollowed()
    }

    private async getIsFollowed() {
      this.isFollowed = await userService.isFollowed(this.user.id!)
    }

    private async handleFollow() {
      try {
        await userService.follow(this.user.id!)
        this.isFollowed = true
      } catch (e) {
        this.$message.warning("关注失败！")
      }
    }

    private async handleUnfollow() {
      try {
        await userService.unfollow(this.user.id!)
        this.isFollowed = false
      } catch (e) {
        this.$message.warning("取消关注失败！")
      }
    }
  }
</script>

<style scoped>

</style>
