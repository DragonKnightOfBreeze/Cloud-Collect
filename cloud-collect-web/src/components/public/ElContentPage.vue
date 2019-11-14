<template>
  <div class="el-content-page" :class="cssClass">
    <h2>
      <slot name="title"></slot>
    </h2>
    <ElDivider/>
    <p>
      <slot name="default"></slot>
    </p>
    <ElLink href="/" v-if="showHomeLink">回到首页</ElLink>
  </div>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class ElContentPage extends Vue {
    @Prop() color!: string
    @Prop() bgColor!: string
    @Prop() align!: string

    @Prop({default: true}) showHomeLink!: boolean

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
