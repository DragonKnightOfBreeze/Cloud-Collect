<template>
  <div>
    <ElPageHeader title="返回首页" content="搜索页面" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>搜索指定的内容。</div>
    </div>
    <ElBlankLine/>
    <div class="app-button-group align-center">
        <ElButton type="primary" @click="handleGoSearchCollect"><ElIcon name="search"/> 搜索收藏</ElButton>
        <ElButton type="primary" @click="handleGoSearchUser"><ElIcon name="search"/> 搜索用户</ElButton>
    </div>
  </div>
</template>

<script lang="ts">
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine}
  })
  export default class SearchOverview extends Vue {
    private handleGoBack() {
      this.$router.push("/")
    }

    private handleGoSearchCollect() {
      this.$router.push("/search/collect")
    }

    private handleGoSearchUser() {
      this.$router.push("/search/user")
    }
  }
</script>

<style scoped>

</style>
