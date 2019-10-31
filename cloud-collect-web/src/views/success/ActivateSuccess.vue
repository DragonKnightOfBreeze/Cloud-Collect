<template>
  <SuccessPage>
    <template v-slot:title>激活成功</template>

    你的账户已激活。
  </SuccessPage>
</template>

<script lang="ts">
  import SuccessPage from "@/components/success/SuccessPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {SuccessPage}
  })
  export default class ActiveSuccess extends Vue {
  }
</script>

<style scoped>

</style>
