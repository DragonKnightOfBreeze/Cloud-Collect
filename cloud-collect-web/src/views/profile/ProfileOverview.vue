<template>
  <div>
    <h3 class="align-center" v-if="currentUser">跳转中……</h3>
    <h3 class="align-center" v-else>请登录</h3>
  </div>
</template>

<script lang="ts">
  import {User} from "@/types"
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class ProfileOverview extends Vue {
    get currentUser(): User | null {
      return this.$store.getters.currentUser
    }

    created() {
      //如果当前用户已登录，则重定向到档案详情页，否则要求用户登录
      if (this.currentUser) {
        this.$router.push(`/profile/${this.currentUser.id}`)
      } else {
        this.$router.push({path: "", query: {operation: "login"}})
      }
    }
  }
</script>

<style scoped>

</style>
