<template>
  <ElContentPage color="success" align="center">
    <template v-slot:title>激活成功</template>

    你的账户已激活。
  </ElContentPage>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class ActiveSuccess extends Vue {
  }
</script>

<style scoped>

</style>
