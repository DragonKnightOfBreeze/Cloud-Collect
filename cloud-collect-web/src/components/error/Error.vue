<template>
  <div class="cc-error align-center">
    <h1 class="cc-error-title" :style="{color: color}">
      <slot name="title"></slot>
    </h1>
    <el-divider></el-divider>
    <p class="cc-error-text" :style="{color: color}">
      <slot name="default"></slot>
    </p>
    <el-link href="/">回到首页</el-link>
  </div>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class Error extends Vue {
    @Prop({default: "red"}) color!: String
  }
</script>

<style scoped>

</style>
