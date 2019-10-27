<template>
  <error-page>
    <template v-slot:title>内部错误</template>

    <i class="el-icon-warning"></i>
    这片领域崩坏了……
    <i class="el-icon-warning"></i>
  </error-page>
</template>

<script lang="ts">
  import ErrorPage from "@/components/error/ErrorPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ErrorPage}
  })
  export default class Error500 extends Vue {
  }
</script>

<style scoped>

</style>
