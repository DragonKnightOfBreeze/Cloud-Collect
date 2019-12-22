<template functional>
  <ElCard class="no-content-card">
    <ElRow>
      <ElCol class="justify-content-center">
        <slot></slot>
      </ElCol>
    </ElRow>
  </ElCard>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class NoContentCard extends Vue {
  }
</script>

<style scoped>
  .no-content-card {
    border: 1px double orangered;
    border-radius: 4px;
  }
</style>
