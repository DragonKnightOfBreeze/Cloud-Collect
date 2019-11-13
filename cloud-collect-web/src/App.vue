<template>
  <div id="app">
    <!--NOTE 不指定direction的话，就会出现奇怪的问题-->
    <ElContainer direction="vertical">
      <ElHeader v-show="showHeader">
        <TheHeader></TheHeader>
      </ElHeader>

      <ElMain>
        <router-view></router-view>
      </ElMain>

      <ElFooter v-show="showFooter">
        <TheFooter></TheFooter>
      </ElFooter>
    </ElContainer>
  </div>
</template>

<script lang="ts">
  import TheFooter from "@/components/root/TheFooter.vue"
  import TheHeader from "@/components/root/TheHeader.vue"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {TheHeader, TheFooter}
  })
  export default class App extends Vue {
    private showHeader = true
    private showFooter = true

    @Watch("$route")
    onRouteChange(value: Route, oldValue: Route) {
      this.changeShowHeaderAndFooter(value, oldValue)
    }

    //DONE 当为错误页和成功页时隐藏首页头部和尾部
    private changeShowHeaderAndFooter(value: Route, oldValue: Route) {
      if (value.path.startsWith("/error") || value.path.startsWith("/success")) {
        console.log("隐藏页面首部和尾部。")
        this.showHeader = false
        this.showFooter = false
      } else {
        this.showHeader = true
        this.showFooter = true
      }
    }
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
    width: 1000px;
    margin: 0 auto;
  }
  img {
    vertical-align: center;
  }

  .el-card {
    margin: 10px 10px;
  }
  .el-tag {
    margin: 0 5px
  }

  .app-item-list {
    padding: 10px 0;
  }
  .app-title {
    font-size: 16px;
    font-weight: bold;
    line-height: 30px;
    color: #333;
    padding: 15px 0;
  }
  .app-meta {
    font-size: 14px;
    line-height: 24px;
    color: #909399;
    padding: 5px 0;
  }
  .app-meta-small {
    font-size: 12px;
    line-height: 18px;
    color: #909399;
    padding: 5px 0;
  }
  /*对应的html元素不应该是p，因为可能是markdown文本*/
  .app-summary {
    padding: 15px 0;
  }
  /*对应的html元素不应该是p，因为可能是markdown文本*/
  .app-content {
    padding: 15px 0;
  }
</style>
