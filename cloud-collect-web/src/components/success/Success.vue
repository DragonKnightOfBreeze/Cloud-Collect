<template>
  <div class="cc-success align-center">
    <h1 class="cc-success-title">
      <slot name="title"></slot>
    </h1>
    <el-divider></el-divider>
    <p class="cc-success-text">
      <slot name="default"></slot>
    </p>
    <el-link href="/">回到首页</el-link>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class Success extends Vue {
  }
</script>

<style scoped>

</style>
