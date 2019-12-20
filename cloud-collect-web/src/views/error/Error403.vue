<template functional>
  <ElContentPage color="warn" align="center">
    <template v-slot:title>权限错误</template>

    <ElIcon name="warning"/> 这片领域不属于你…… <ElIcon name="warning"/>
  </ElContentPage>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class Error403 extends Vue {
  }
</script>

<style scoped>

</style>
