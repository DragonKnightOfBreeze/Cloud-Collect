<template>
  <error>
    <template v-slot:title>内部错误</template>

    <i class="el-icon-warning"></i>
    这片领域崩坏了……
    <i class="el-icon-warning"></i>
  </error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error500 extends Vue {
  }
</script>

<style scoped>

</style>
