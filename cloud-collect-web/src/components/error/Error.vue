<template>
  <div class="cc-error align-center color-warn">
    <h1 class="cc-error-title">
      <slot name="title"></slot>
    </h1>
    <el-divider></el-divider>
    <p class="cc-error-text">
      <slot name="default"></slot>
    </p>
    <el-link href="/">回到首页</el-link>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class Error extends Vue {
  }
</script>

<style scoped>

</style>
