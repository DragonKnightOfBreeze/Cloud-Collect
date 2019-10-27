<template>
  <error-page>
    <template v-slot:title>页面未找到</template>

    <i class="el-icon-warning"></i>
    这片领域尚未开拓……
    <i class="el-icon-warning"></i>
  </error-page>
</template>

<script lang="ts">
  import ErrorPage from "@/components/error/ErrorPage.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ErrorPage}
  })
  export default class Error404 extends Vue {
  }
</script>

<style scoped>

</style>
