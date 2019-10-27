<template>
  <error>
    <template v-slot:title>功能未实现</template>

    <i class="el-icon-warning"></i>
    这片领域有待发掘……
    <i class="el-icon-warning"></i>
  </error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error501 extends Vue {
  }
</script>

<style scoped>

</style>
