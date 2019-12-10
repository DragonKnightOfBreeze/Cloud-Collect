<template>
  <div>
    <ElPageHeader title="返回首页" content="搜索页面" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>搜索指定的内容。</div>
    </div>
    <ElBlankLine/>
    <ElRow class="justify-content-center align-items-center">
      <ElCol :span="6" class="justify-content-center align-items-center">
        <ElButton type="primary" @click="handleGoSearchCollect"><ElIcon name="search"/> 搜索收藏</ElButton>
      </ElCol>
      <ElCol :span="6" class="justify-content-center align-items-center">
        <ElButton type="primary" @click="handleGoSearchUser"><ElIcon name="search"/> 搜索用户</ElButton>
      </ElCol>
    </ElRow>
  </div>
</template>

<script lang="ts">
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import {Component, Vue} from "vue-property-decorator"

  @Component({
    components: {ElBlankLine}
  })
  export default class SearchOverview extends Vue {
    handleGoBack() {
      this.$router.push("/")
    }

    handleGoSearchCollect() {
      this.$router.push("/search/collect")
    }

    handleGoSearchUser() {
      this.$router.push("/search/user")
    }
  }
</script>

<style scoped>

</style>
