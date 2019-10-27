<template>
  <el-card class="cc-error">
    <template v-slot:header>
      <slot name="title" class="cc-error-title"></slot>
    </template>
    <el-row type="flex" justify="center">
      <el-col>
        <slot name="text" class="cc-error-text"></slot>
      </el-col>
    </el-row>
  </el-card>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class ErrorPage extends Vue {
  }
</script>

<style scoped>
  .cc-error-title {
    color: orangered;
  }
  .cc-error-text {
    color: orangered;
  }
</style>
