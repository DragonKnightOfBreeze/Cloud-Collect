<template>
  <ElCard class="app-user-overview-card">
    <template v-slot:header>
      <ElRow class="align-items-center">
        <ElCol :span="1">
          <ElAvatar size="small" v-if="user.avatarUrl" :src="user.avatarUrl"/>
          <ElAvatar size="small" v-else icon="el-icon-user-solid"/>
        </ElCol>
        <ElCol :span="11">
          <ElRouterLink type="primary" :href="'/profile/'+user.id">{{user.nickname}}</ElRouterLink>
        </ElCol>
        <ElCol :span="4" :offset="8">
          <FollowButton v-if="!isCurrentUser" :user="user"></FollowButton>
        </ElCol>
      </ElRow>
    </template>

    <ElRow class="app-meta align-items-center">
      <ElCol :span="6">
        用户名：{{user.username}}
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="user.collectCount">
          <ElRouterLink type="info" :href="'/profile/'+user.id+'/collects'">收藏</ElRouterLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="user.categoryCount">
          <ElRouterLink type="info" :href="'/profile/'+user.id+'/categories'">分类</ElRouterLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge type="primary" :value="user.praiseToCollectCount">
          <ElRouterLink type="info" :href="'/profile/'+user.id+'/stars'">喜爱</ElRouterLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge :value="user.followToUserCount">
          <ElRouterLink type="info" :href="'/profile/'+user.id+'/following'">关注</ElRouterLink>
        </ElBadge>
      </ElCol>
      <ElCol :span="3">
        <ElBadge :value="user.followByUserCount">
          <ElRouterLink type="info" :href="'/profile/'+user.id+'/followers'">粉丝</ElRouterLink>
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
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {User} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink, FollowButton}
  })
  export default class UserOverviewCard extends Vue {
    @Prop({required: true}) user!: User

    private get currentUser() {
      return !!this.$store.getters.currentUser
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.user.id
    }
  }
</script>

<style scoped>

</style>
