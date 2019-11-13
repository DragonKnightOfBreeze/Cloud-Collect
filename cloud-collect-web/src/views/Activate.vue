<template>
  <div>
    <h3 class="align-center">激活中……</h3>
  </div>
</template>

<script lang="ts">
  import * as indexService from "@/services/indexService"
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class Activate extends Vue {
    private username: string = ""
    private activateCode: string = ""

    created() {
      this.getParams()
      this.handleActivate()
    }

    //DONE 得到对应的查询参数，如果不存在，则认为是非法的访问
    private getParams() {
      if (!this.$route.query["username"] || !this.$route.query["activateCode"]) {
        console.warn("权限错误")
        this.$router.push("/")
      }

      this.username = this.$route.query["username"].toString()
      this.activateCode = this.$route.query["activateCode"].toString()
    }

    private async handleActivate() {
      try {
        await indexService.activate(this.username, this.activateCode)

        this.$message.success("激活用户成功！请登录。")
        await this.$router.push("/success/activate")
      } catch (e) {
        this.$message.warning("激活用户失败！")
      }
    }
  }
</script>

<style scoped>

</style>
