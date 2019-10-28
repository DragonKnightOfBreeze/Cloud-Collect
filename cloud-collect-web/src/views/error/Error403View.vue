<template>
  <!--话说这里的语言注入到底是怎么定位的……-->
  <error>
    <template v-slot:title>权限错误</template>

    <i class="el-icon-warning"></i>
    这片领域不属于你……
    <i class="el-icon-warning"></i>
  </error>
</template>

<script lang="ts">
  import Error from "@/components/error/Error.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {Error}
  })
  export default class Error403View extends Vue {
  }
</script>

<style scoped>

</style>
