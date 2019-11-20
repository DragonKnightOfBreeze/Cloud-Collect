<template>
  <div>
    <ElPageHeader title="返回搜索页面" content="搜索用户" @back="handleGoBack"></ElPageHeader>
    <ElDivider/>
    <div>
      <div>搜索指定的用户。</div>
    </div>
    <ElBlankLine/>
    <ElRow type="flex" justify="center" align="middle">
      <ElCol :span="12">
      <ElInput v-model="searchTerm" placeholder="关键词">
        <template v-slot:prepend>
          <ElSelect class="app-input-select" v-model="searchType" :value="searchType" placeholder="请选择">
            <ElOption v-for="option in searchOptions" :key="option.label" :label="option.label" :value="option.value"></ElOption>
          </ElSelect>
        </template>

        <template v-slot:append>
          <ElButton @click="handleSearch"><ElIcon name="search" /></ElButton>
        </template>
      </ElInput>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="searchPage">
      <UserOverviewCard v-for="user in searchPage.content" :key="user.id" :user="user"/>
      <ThePagination :page="searchPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import UserOverviewCard from "@/components/card/UserOverviewCard.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import * as userService from "@/services/userService"
  import {Option, Page, PageableParam, User, UserSearchType} from "@/types"
  import {Component, Vue, Watch} from "vue-property-decorator"

  @Component({
    components: {ThePagination, ElCardGroup, UserOverviewCard, ElBlankLine}
  })
  export default class SearchUser extends Vue {
    private searchTerm: string = ""
    private searchType: UserSearchType = "nickname"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private searchPage: Page<User> | null = null
    private searchOptions: Option<UserSearchType>[] = [
      {label: "按昵称搜索", value: "nickname"},
      {label: "按用户名搜索", value: "username"},
      {label: "按邮箱搜索", value: "email"}
    ]

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`查询分页参数发生变化：`, value)
      this.searchUser()
    }

    private handleGoBack() {
      this.$router.push("/search")
    }

    private handleSearch() {
      this.searchUser()
    }

    private async searchUser() {
      try {
        switch (this.searchType) {
          case "none":
            this.searchPage = await userService.findAll(this.pageableParam)
            break
          case "nickname":
            this.searchPage = await userService.findAllByNicknameContains(this.searchTerm, this.pageableParam)
            break
          case "username":
            this.searchPage = await userService.findAllByUsernameContains(this.searchTerm, this.pageableParam)
            break
          case "email":
            this.searchPage = await userService.findAllByEmailContains(this.searchTerm, this.pageableParam)
            break
        }
      } catch (e) {
        this.$message.warning("查询失败！")
      }
    }
  }
</script>

<style scoped>

</style>
