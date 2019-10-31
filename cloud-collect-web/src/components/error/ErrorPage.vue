<template>
  <div class="app-error align-center color-warn">
    <h2 class="app-error-title">
      <slot name="title"></slot>
    </h2>
    <ElDivider/>
    <p class="app-error-text">
      <slot name="default"></slot>
    </p>
    <el-link href="/">回到首页</el-link>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class ErrorPage extends Vue {
  }
</script>

<style scoped>

</style>
