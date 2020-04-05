<template>
  <ElContentPage color="warn" align="center">
    <template v-slot:title>功能未实现</template>

    <ElIcon name="warning"/> 这片领域有待发掘…… <ElIcon name="warning"/>
  </ElContentPage>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class Error501 extends Vue {
  }
</script>

<style scoped>

</style>
