<template>
  <div id="app">
    <!--NOTE 不指定direction的话，就会出现奇怪的问题-->
    <ElContainer direction="vertical">
      <ElHeader>
        <!--Header-->
        <TheHeader></TheHeader>
      </ElHeader>

      <ElMain>
        <!--Main-->
        <router-view></router-view>
      </ElMain>

      <ElFooter>
        <!--Footer-->
        <TheFooter></TheFooter>
      </ElFooter>
    </ElContainer>
  </div>
</template>

<script lang="ts">
  import TheFooter from "@/components/TheFooter.vue"
  import TheHeader from "@/components/TheHeader.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {TheHeader, TheFooter}
  })
  export default class App extends Vue {
  }
</script>

<style>
  @import "assets/css/global.css";

  #app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: left;
    color: #2c3e50;
    width: 1200px;
    margin: 0 auto;
    padding-top: 60px;
  }
  img {
    vertical-align: bottom;
  }
</style>
