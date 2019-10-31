<template>
  <Error>
    <template v-slot:title>权限错误</template>

    <ElIcon name="warning"/>
    这片领域不属于你……
    <ElIcon name="warning"/>
  </Error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error403 extends Vue {
  }
</script>

<style scoped>

</style>
