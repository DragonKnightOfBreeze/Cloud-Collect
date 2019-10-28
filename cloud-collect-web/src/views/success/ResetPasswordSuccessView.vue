<template>
  <success>
    <template v-slot:title>重置密码成功</template>

    你的密码已重置。
  </success>
</template>

<script lang="ts">
  import Success from "@/components/success/Success.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Success}
  })
  export default class ResetPasswordSuccessView extends Vue {
  }
</script>

<style scoped>

</style>
