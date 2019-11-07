<template>
  <el-card class="app-user-overview-card">
    <template v-slot:header>
      <el-row>
        <el-col :span="1">
          <el-avatar size="small" :src="user.avatarUrl"/>
        </el-col>
        <el-col :span="11">
          <el-link type="primary" :href="'/profile/'+user.id">{{user.nickname}}</el-link>
        </el-col>
        <el-col :span="4" :offset="8">
          <follow-button v-show="currentUser" :user="user"></follow-button>
        </el-col>
      </el-row>
    </template>

    <el-row class="app-meta-small">
      <el-col :span="6">
        用户名 {{user.username}}
      </el-col>
      <el-col :span="3">
        <el-badge type="primary" :value="user.collectCount">
          <el-link type="info" :href="'/profile'+user.id+'/collects'">收藏</el-link>
        </el-badge>
      </el-col>
      <el-col :span="3">
        <el-badge type="primary" :value="user.praiseToCollectList">
          <el-link type="info" :href="'/profile'+user.id+'/stars'">喜爱</el-link>
        </el-badge>
      </el-col>
      <el-col :span="3">
        <el-badge :value="user.followToUserCount">
          <el-link type="info" :href="'/profile/'+user.id+'/followers'">关注</el-link>
        </el-badge>
      </el-col>
      <el-col :span="3">
        <el-badge :value="user.followByUserCount">
          <el-link type="info" :href="'/profile/'+user.id+'/following'">粉丝</el-link>
        </el-badge>
      </el-col>
    </el-row>
  </el-card>
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

    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }
  }
</script>

<style scoped>

</style>
