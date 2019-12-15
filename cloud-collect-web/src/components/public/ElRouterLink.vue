<template>
  <!--没有什么好说的，完美好不好！-->
  <router-link v-if="!disabled"
               :to="href" :replace="replace" :append="append" :active-class="activeClass" :exact="exact"
               :exact-active-class="exactActiveClass" :event="event"
               v-slot="{ href, route, navigate, isActive, isExactActive }">
    <ElLink :type="type" :underline="underline" :href="href" @click.native="navigate">
      <slot></slot>
    </ElLink>
  </router-link>
  <ElLink v-else :type="type" disabled :underline="underline">
    <slot></slot>
  </ElLink>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator"
  import {Location} from "vue-router"

  @Component
  export default class ElRouterLink extends Vue {
    @Prop({default: "default"}) type!: "primary" | "success" | "warning" | "danger" | "info" | "default"
    @Prop({default: false, type: Boolean}) underline!: boolean
    @Prop({default: false, type: Boolean}) disabled!: boolean

    @Prop({default: ""}) href!: string | Location
    @Prop({default: false, type: Boolean}) replace!: boolean
    @Prop({default: false, type: Boolean}) append!: boolean
    @Prop({default: "router-link-active"}) activeClass!: string
    @Prop({default: false, type: Boolean}) exact!: boolean
    @Prop({default: "router-link-exact-active"}) exactActiveClass!: string
    @Prop({default: "click"}) event!: string | string[]
  }
</script>

<style scoped>

</style>
