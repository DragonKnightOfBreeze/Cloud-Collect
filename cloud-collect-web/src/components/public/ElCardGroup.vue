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
    margin: 5px 5px;
    padding: 5px 5px;
    border: 1px solid lightgrey;
    border-radius: 4px;
  }
</style>
