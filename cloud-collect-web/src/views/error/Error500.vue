<template>
  <Error>
    <template v-slot:title>内部错误</template>

    <ElIcon name="warning"/>
    这片领域崩坏了……
    <ElIcon name="warning"/>
  </Error>
</template>

<script lang="ts">
  import ErrorPage from "@/components/error/ErrorPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error: ErrorPage}
  })
  export default class Error500 extends Vue {
  }
</script>

<style scoped>

</style>
