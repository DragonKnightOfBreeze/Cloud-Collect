<template>
  <Error>
    <template v-slot:title>页面未找到</template>

    <ElIcon name="warning"/>
    这片领域尚未开拓……
    <ElIcon name="warning"/>
  </Error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error404 extends Vue {
  }
</script>

<style scoped>

</style>
