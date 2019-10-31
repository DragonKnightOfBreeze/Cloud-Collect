<template>
  <Error>
    <template v-slot:title>功能未实现</template>

    <ElIcon name="warning"/>
    这片领域有待发掘……
    <ElIcon name="warning"/>
  </Error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error501 extends Vue {
  }
</script>

<style scoped>

</style>
