<template>
  <ElContentPage color="warn" align="center">
    <template v-slot:title>内部错误</template>

    <ElIcon name="warning"/> 这片领域崩坏了…… <ElIcon name="warning"/>
  </ElContentPage>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class Error500 extends Vue {
  }
</script>

<style scoped>

</style>
