<template>
  <div id="app">
    <!--NOTE 不指定direction的话，就会出现奇怪的问题-->
    <ElContainer direction="vertical">
      <ElHeader v-show="showHeader">
        <keep-alive>
          <TheHeader></TheHeader>
        </keep-alive>
      </ElHeader>

      <ElMain>
        <router-view></router-view>
      </ElMain>

      <ElFooter v-show="showFooter">
        <keep-alive>
          <TheFooter></TheFooter>
        </keep-alive>
      </ElFooter>

      <ElBacktop :visibility-height="400"/>
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
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      this.changeShowHeaderAndFooter()
    }

    //当为错误页和成功页时隐藏首页头部和尾部
    private changeShowHeaderAndFooter() {
      if (this.$route.path.startsWith("/error") || this.$route.path.startsWith("/success")) {
        console.log("隐藏页面首部和尾部。")
        this.showHeader = false
        this.showFooter = false
      } else {
        console.log("显示页面首部和尾部。")
        this.showHeader = true
        this.showFooter = true
      }
    }
  }
</script>

<style>
  @import "assets/css/global.css";

  body {
    margin: 0 0;
  }
  #app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: left;
    color: #2c3e50;
    width: 1000px;
    margin: 0 auto;
    vertical-align: middle;
    overflow: hidden;
  }
  img {
    vertical-align: middle;
  }

  .el-main {
    overflow: hidden;
  }
  .el-card {
    margin: 10px 10px;
  }
  .el-tag {
    margin: 0 5px;
  }
  .el-page-header__left {
    width: 100px;
  }
  .el-page-header__content {
    float: right;
    width: 100%;
  }

  .app-item-list {
    padding: 10px 0;
  }
  .app-title {
    font-size: 20px;
    line-height: 30px;
    padding: 10px 0;
  }
  .app-meta {
    font-size: 12px;
    height: 18px;
    line-height: 18px;
    color: #909399;
    padding: 10px 0;
  }
  .app-meta span {
    font-size: 12px;
    height: 18px;
    line-height: 18px;
  }
  /*对应的html元素不应该是p，因为可能是markdown文本*/
  .app-summary {
    padding: 15px 0;
  }
  .app-content {
    padding: 15px 0;
  }
  .app-introduce {
    padding: 15px 0;
  }
  .app-input-select {
    width: 180px;
  }
  .app-searchable-select {
    width: 100%;
  }
  .app-button-group {
    padding: 10px 10px;
  }
</style>
