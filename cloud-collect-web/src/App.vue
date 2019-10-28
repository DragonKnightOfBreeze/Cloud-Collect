<template>
  <div id="app">
    <NavBar v-show="isShowNavBar"></NavBar>
    <div id="layout">
      <router-view></router-view>
    </div>
    <Footer v-show="isShowFooter"></Footer>
  </div>
</template>

<script lang="ts">
  import Footer from "@/components/Footer.vue"
  import NavBar from "@/components/NavBar.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {NavBar, Footer}
  })
  export default class App extends Vue {
    private isShowNavBar = true
    private isShowFooter = true
  }
</script>

<style>
  /*导入公共样式*/
  @import "../public/css/global.css";

  #app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: left;
    color: #2c3e50;
    width: 1200px;
    margin: 0 auto;
    padding-top: 61px;
  }
  img {
    vertical-align: bottom;
  }
</style>
