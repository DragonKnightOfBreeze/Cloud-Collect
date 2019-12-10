<template>
  <div>
    <ElButton type="info" size="small" plain v-if="collect.praised===true" @click="handleUnpraise">
      <ElIcon name="star-on"/> 已点赞
    </ElButton>
    <ElButton type="primary" size="small" plain v-else-if="collect.praised===false" @click="handlePraise">
      <ElIcon name="star-off"/> 点赞
    </ElButton>
  </div>
</template>

<script lang="ts">
  import * as collectService from "@/services/collectService"
  import {Collect} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class PraiseButton extends Vue {
    @Prop({required: true}) collect!: Collect

    private async handlePraise() {
      try {
        await collectService.praise(this.collect.id!)
        this.collect.praised = true
      } catch (e) {
        this.$message.warning("点赞失败！")
      }
    }

    private async handleUnpraise() {
      try {
        await collectService.unpraise(this.collect.id!)
        this.collect.praised = false
      } catch (e) {
        this.$message.warning("取消点赞失败！")
      }
    }
  }
</script>

<style scoped>

</style>
