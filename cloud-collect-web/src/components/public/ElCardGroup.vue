<template>
  <div class="el-card-group">
    <slot></slot>
  </div>
</template>d

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class ElCardGroup extends Vue {
  }
</script>

<style scoped>
  .el-card-group {
    margin: 10px 10px;
  }
</style>
