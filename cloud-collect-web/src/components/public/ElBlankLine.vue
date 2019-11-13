<template>
  <div class="el-blank-line">
    &nbsp;
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class ElBlankLine extends Vue {
  }
</script>

<style scoped>
  .el-blank-line {
    margin: 24px 0;
    height: 1px;
  }
</style>
