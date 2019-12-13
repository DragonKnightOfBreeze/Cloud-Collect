<template>
  <div class="el-content-page" :class="cssClass">
    <h2>
      <slot name="title"></slot>
    </h2>
    <ElDivider/>
    <p>
      <slot name="default"></slot>
    </p>
    <ElRouterLink href="/" v-if="showHomeLink">回到首页</ElRouterLink>
  </div>
</template>

<script lang="ts">
  import ElRouterLink from "@/components/public/ElRouterLink.vue"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component({
    components: {ElRouterLink}
  })
  export default class ElContentPage extends Vue {
    @Prop() color!: string
    @Prop() bgColor!: string
    @Prop() align!: string

    @Prop({default: true, type: Boolean}) showHomeLink!: boolean

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
