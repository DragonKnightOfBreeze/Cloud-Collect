<template>
  <span>
    <!--根据不同的收藏类型，显示不同的图标-->
    <ElIcon class="color-info" name="time" v-if="collectType==='DELAY'"/>
    <ElIcon class="color-primary" name="warning-outline" v-else-if="collectType==='IMPORTANT'"/>
    <ElIcon class="color-success" name="star-off" v-else-if="collectType==='LOVE'"/>
    <ElIcon class="color-warn" name="edit" v-else-if="collectType==='TODO'"/>
  </span>
</template>

<script lang="ts">
  import {CollectType} from "@/domain"
  import {Component, Prop, Vue} from "vue-property-decorator"

  @Component
  export default class CollectIcon extends Vue {
    @Prop({required: true}) collectType!: CollectType
  }
</script>

<style scoped>

</style>
