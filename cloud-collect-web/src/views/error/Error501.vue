<template>
  <Error>
    <template v-slot:title>功能未实现</template>

    <ElIcon name="warning"/>
    这片领域有待发掘……
    <ElIcon name="warning"/>
  </Error>
</template>

<script lang="ts">
  import ErrorPage from "@/components/error/ErrorPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error: ErrorPage}
  })
  export default class Error501 extends Vue {
  }
</script>

<style scoped>

</style>
