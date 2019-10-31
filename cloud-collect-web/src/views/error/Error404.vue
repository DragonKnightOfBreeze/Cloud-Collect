<template>
  <ElContentPage color="warn" align="center">
    <template v-slot:title>页面未找到</template>

    <ElIcon name="warning"/>
    这片领域尚未开拓……
    <ElIcon name="warning"/>
  </ElContentPage>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class Error404 extends Vue {
  }
</script>

<style scoped>

</style>
