<template>
  <div class="app-success align-center color-success">
    <h2 class="app-success-title">
      <slot name="title"></slot>
    </h2>
    <ElDivider/>
    <p class="app-success-text">
      <slot name="default"></slot>
    </p>
    <ElLink href="/">回到首页</ElLink>
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
