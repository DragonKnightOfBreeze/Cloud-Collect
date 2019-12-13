<template>
  <div>
    <h3>Ta喜爱的收藏</h3>
    <ElDivider/>

    <!--允许过滤-->
    <ElRow :gutter="5">
      <ElCol :span="6">
        <ElInput v-model="searchTerm1" placeholder="按名字搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('name')"><ElIcon name="search"/></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="6">
        <ElInput v-model="searchTerm2" placeholder="按分类名搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('categoryName')"><ElIcon name="search"/></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="6">
        <ElInput v-model="searchTerm3" placeholder="按标签名搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('tagName')"><ElIcon name="search"/></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="4">
        <ElDropdown type="primary" @command="handleCommand">
          <ElButton type="text">按类型搜索 <ElIcon name="arrow-down"/></ElButton>
          <template v-slot:dropdown>
            <ElDropdownMenu>
              <ElDropdownItem v-for="type in collectTypes" :key="type.name" :command="type.name">
                {{type.text}}
              </ElDropdownItem>
            </ElDropdownMenu>
          </template>
        </ElDropdown>
      </ElCol>
      <ElCol :span="2">
        <ElButton type="info" @click="handleSearch('none')">重置</ElButton>
      </ElCol>
    </ElRow>

    <ElCardGroup v-if="praiseToCollectPage">
      <TheSorter type="collect" :pageable-param.sync="pageableParam"/>
      <CollectOverviewCard v-for="collect in praiseToCollectPage.content" :key="collect.id" :collect="collect"/>
      <ThePagination :page="praiseToCollectPage" :pageable-param.sync="pageableParam"/>
    </ElCardGroup>
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Collect, CollectSearchType, CollectType, Page, PageableParam} from "@/domain"
  import {collectTypes} from "@/enums"
  import * as collectService from "@/services/collectService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {TheSorter, ThePagination, CollectOverviewCard, ElCardGroup}
  })
  export default class ProfileDetailStars extends Vue {
    private searchTerm1: string = ""
    private searchTerm2: string = ""
    private searchTerm3: string = ""
    private searchTerm4: CollectType = "NONE"
    private searchType: CollectSearchType = "none"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private praiseToCollectPage: Page<Collect> | null = null
    private collectTypes = collectTypes

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    created() {
      this.getPraiseToCollectPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      console.log("路由发生了变化：", value)
      if (value.params.id === oldValue.params.id) return
      this.getPraiseToCollectPage()
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getPraiseToCollectPage()
    }

    private handleSearch(type: CollectSearchType) {
      this.searchType = type
      this.getPraiseToCollectPage()
    }

    private handleCommand(value: CollectType) {
      this.searchTerm4 = value
      this.handleSearch("type")
    }

    private async getPraiseToCollectPage() {
      try {
        switch (this.searchType) {
          case "none":
            this.praiseToCollectPage = await collectService.findAllByPraiseByUserId(this.userId, this.pageableParam)
            break
          case "name":
            this.praiseToCollectPage = await collectService.findAllByNameContainsAndPraiseByUserId(this.searchTerm1, this.userId, this.pageableParam)
            break
          case "categoryName":
            this.praiseToCollectPage = await collectService.findAllByCategoryNameContainsAndPraiseByUserId(this.searchTerm2, this.userId, this.pageableParam)
            break
          case "tagName":
            this.praiseToCollectPage = await collectService.findAllByTagNameContainsAndPraiseByUserId(this.searchTerm3, this.userId, this.pageableParam)
            break
          case "type":
            this.praiseToCollectPage = await collectService.findAllByTypeAndPraiseByUserId(this.searchTerm4, this.userId, this.pageableParam)
            break
          default:
            this.$message.error(`Cannot search collects by ${this.searchType} here.`)
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
