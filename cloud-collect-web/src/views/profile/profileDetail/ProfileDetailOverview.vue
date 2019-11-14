<template>
  <div>
    <h3>Ta的主页</h3>
    <ElDivider/>
    <UserDetailCard :user="user"/>

    <ElButtonGroup class="align-center" v-show="isCurrentUser">
      <ElButton type="success" @click="handleEdit">编辑个人资料</ElButton>
    </ElButtonGroup>

    <EditProfileDialog :visible.sync="editDialogVisible" :user="user" @submit="handleSubmit"/>
  </div>
</template>

<script lang="ts">
  import UserDetailCard from "@/components/card/UserDetailCard.vue"
  import EditProfileDialog from "@/components/dialog/EditProfileDialog.vue"

  import * as userService from "@/services/userService"
  import {User} from "@/types"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {UserDetailCard, EditProfileDialog}
  })
  export default class ProfileDetailOverview extends Vue {
    private user: User | null = null
    private editDialogVisible = false

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getUser()
    }

    handleEdit() {
      this.editDialogVisible = true
    }

    handleSubmit() {
      this.getUser()
    }

    private async getUser() {
      this.user = await userService.findById(this.userId)
    }
  }
</script>

<style scoped>

</style>
