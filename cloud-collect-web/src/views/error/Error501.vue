<template>
  <error-page>
    <template v-slot:title>功能未实现</template>

    <i class="el-icon-warning"></i>
    这片领域有待发掘……
    <i class="el-icon-warning"></i>
  </error-page>
</template>

<script lang="ts">
  import ErrorPage from "@/components/error/ErrorPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ErrorPage}
  })
  export default class Error501 extends Vue {
  }
</script>

<style scoped>

</style>
