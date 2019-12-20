<template functional>
  <ElContentPage color="success" align="center">
    <template v-slot:title>重置密码成功</template>

    你的密码已重置。
  </ElContentPage>
</template>

<script lang="ts">
  import ElContentPage from "@/components/public/ElContentPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElContentPage}
  })
  export default class ResetPasswordSuccess extends Vue {
  }
</script>

<style scoped>

</style>
