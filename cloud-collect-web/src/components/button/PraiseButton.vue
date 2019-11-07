<template>
  <div>
    <ElButton type="primary" v-if="!isPraised" @click="handlePraise">点赞</ElButton>
    <ElButton type="info" v-else @click="handleUnpraise">已点赞</ElButton>
  </div>
</template>

<script lang="ts">
  import * as collectService from "@/services/collectService"
  import {Collect} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class PraiseButton extends Vue {
    @Prop({required: true}) collect!: Collect

    isPraised: boolean = false

    created() {
      this.getIsPraised()
    }

    private async getIsPraised() {
      this.isPraised = await collectService.isPraised(this.collect.id!)
    }

    private async handlePraise() {
      try {
        await collectService.praise(this.collect.id!)
        this.isPraised = true
      } catch (e) {
        this.$message.warning("点赞失败！")
      }
    }

    private async handleUnpraise() {
      try {
        await collectService.unpraise(this.collect.id!)
        this.isPraised = false
      } catch (e) {
        this.$message.warning("取消点赞失败！")
      }
    }
  }
</script>

<style scoped>

</style>
