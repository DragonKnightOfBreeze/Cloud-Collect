<template>
  <div>
    <h3>Ta的主页</h3>
    <ElDivider />
    <template v-if="user">
      <UserDetailCard :user="user" />

      <!--对齐不能直接作用在ElButtonGroup上，原因不明-->
      <div class="app-button-group align-center" v-if="isCurrentUser">
        <ElButton type="success" @click="handleEdit"><ElIcon name="edit"/> 编辑个人资料</ElButton>
      </div>

      <EditProfileDialog :visible.sync="editDialogVisible" :user="user" @submit="handleSubmit" />
    </template>
  </div>
</template>

<script lang="ts">
  import UserDetailCard from "@/components/card/UserDetailCard.vue"
  import EditProfileDialog from "@/components/dialog/EditProfileDialog.vue"
  import {User} from "@/domain"

  import * as userService from "@/services/userService"
  import {Component, Emit, Vue} from "vue-property-decorator"

  @Component({
    components: {UserDetailCard, EditProfileDialog}
  })
  export default class ProfileDetailOverview extends Vue {
    private user: User | null = null
    private editDialogVisible = false

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    get currentUser() {
      return this.$store.getters.currentUser
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

    @Emit("submit")
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
