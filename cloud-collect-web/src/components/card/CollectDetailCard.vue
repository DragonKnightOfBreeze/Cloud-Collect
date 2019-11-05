<template>
  <!--TODO-->
</template>

<script lang="ts">
  import {Collect} from "@/types"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CollectDetailCard extends Vue {
    @Prop() collect!: Collect
  }
</script>

<style scoped>

</style>
