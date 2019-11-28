<template>
  <div class="el-blank-line" :style="style">
    &nbsp;
  </div>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class ElBlankLine extends Vue {
    @Prop({default: 24}) height: number

    private style = {
      marginTop: `${this.height}px`,
      marginBottom: `${this.height}px`,
      height: "1px"
    }
  }
</script>

<style scoped>

</style>
