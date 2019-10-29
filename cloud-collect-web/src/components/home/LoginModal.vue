<template>

</template>

<script lang="ts">
  import {Component, Emit, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class LoginModal extends Vue {
    @Prop({default: false}) visible!: boolean

    @Emit()
    close(): boolean {
      //TODO
      return false
    }
  }
</script>

<style scoped>

</style>
