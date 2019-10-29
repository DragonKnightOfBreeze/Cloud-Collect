<template>
  <Success>
    <template v-slot:title>激活成功</template>

    你的账户已激活。
  </Success>
</template>

<script lang="ts">
  import Success from "@/components/success/Success.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Success}
  })
  export default class ActivateSuccessView extends Vue {
  }
</script>

<style scoped>

</style>
