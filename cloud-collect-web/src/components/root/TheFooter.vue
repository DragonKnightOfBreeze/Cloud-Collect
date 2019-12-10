<template>
  <div id="app-footer">
    <ElIcon name="lollipop"/> 云收藏 ©2019 Created by DragonKnightOfBreeze <ElIcon name="lollipop"/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from "vue-property-decorator"

  @Component
  export default class TheFooter extends Vue {
  }
</script>

<style scoped>
  #app-footer {
    color: #303133;
    text-align: center;
    height: 60px;
    line-height: 60px;
  }
</style>
