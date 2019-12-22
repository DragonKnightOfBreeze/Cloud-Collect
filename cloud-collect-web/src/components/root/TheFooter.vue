<template>
  <div id="app-footer">
    <div class="app-footer-text">
      <ElIcon name="lollipop"/> 云收藏 ©2019 Created by DragonKnightOfBreeze <ElIcon name="lollipop"/>
    </div>
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
    height: 60px;
    line-height: 60px;
  }
  .app-footer-text {
    color: #303133;
    text-align: center;
  }
</style>
