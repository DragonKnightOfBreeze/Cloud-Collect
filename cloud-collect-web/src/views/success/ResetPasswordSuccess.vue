<template>
  <SuccessPage>
    <template v-slot:title>重置密码成功</template>

    你的密码已重置。
  </SuccessPage>
</template>

<script lang="ts">
  import SuccessPage from "@/components/success/SuccessPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {SuccessPage}
  })
  export default class ResetPasswordSuccess extends Vue {
  }
</script>

<style scoped>

</style>
