<template>
  <ElCard class="app-user-overview-card">
    <template v-slot:header>
      <ElRow>
        <ElCol :span="1">
          <ElAvatar size="small" :src="user.avatarUrl"/>
        </ElCol>
        <ElCol :span="11">
          <ElLink type="primary" :href="'/profile/'+user.id">{{user.nickname}}</ElLink>
        </ElCol>
        <ElCol :span="4" :offset="8">
          <FollowButton v-show="currentUser && !isCurrentUser" :user="user"></FollowButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta-small">
      <ElCol :span="6">
        用户名 {{user.username}}
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="user.collectCount">
          <ElLink type="info" :href="'/profile'+user.id+'/collects'">收藏</ElLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="user.categoryCount">
          <ElLink type="info" :href="'/profile'+user.id+'/categories'">分类</ElLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="user.praiseToCollectCount">
          <ElLink type="info" :href="'/profile'+user.id+'/stars'">喜爱</ElLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge :value="user.followToUserCount">
          <ElLink type="info" :href="'/profile/'+user.id+'/followers'">关注</ElLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge :value="user.followByUserCount">
          <ElLink type="info" :href="'/profile/'+user.id+'/following'">粉丝</ElLink>
        </ElBadge>
      </ElCol>
    </ElRow>
    <ElRow class="app-introduce">
      <ElCol>{{user.introduce}}</ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import FollowButton from "@/components/button/FollowButton.vue"
  import {User} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {FollowButton}
  })
  export default class UserOverviewCard extends Vue {
    @Prop({required: true}) user!: User

    get currentUser() {
      return !!this.$store.getters.currentUser
    }

    get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.user.id
    }
  }
</script>

<style scoped>

</style>
