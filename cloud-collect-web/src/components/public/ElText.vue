<template>
  <span :class="cssClass">
    <slot></slot>
  </span>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class ElText extends Vue {
    @Prop() color!: string
    @Prop() bgColor!: string
    @Prop() align!: string

    get cssClass() {
      return [
        this.color ? `color-${this.color}` : "",
        this.bgColor ? `bg-color-${this.color}` : "",
        this.align ? `align-${this.align}` : ""
      ]
    }
  }
</script>

<style scoped>

</style>
