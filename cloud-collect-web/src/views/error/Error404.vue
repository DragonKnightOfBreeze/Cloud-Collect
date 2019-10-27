<template>
  <error>
    <template v-slot:title>页面未找到</template>

    <i class="el-icon-warning"></i>
    这片领域尚未开拓……
    <i class="el-icon-warning"></i>
  </error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error404 extends Vue {
  }
</script>

<style scoped>

</style>
