<template>
  <div>
    <h3>Ta的收藏</h3>
    <ElDivider />
    <!--仅当用户为当前用户时，才会显示以下内容-->
    <ElRow :gutter="5" v-if="isCurrentUser">
      <ElCol :span="4">
        <ElButton type="primary" @click="handleCreateCollect"><ElIcon name="plus"/> 创建收藏</ElButton>
      </ElCol>
      <ElCol :span="4">
        <ElButton type="primary" @click="handleCreateCategory"><ElIcon name="plus"/> 创建分类</ElButton>
      </ElCol>
      <ElCol :span="4">
        <ElButton type="primary" @click="handleCreateTag"><ElIcon name="plus"/> 创建标签</ElButton>
      </ElCol>
      <ElCol :span="4">
        <DataImportPopover @import="handleImport"/>
      </ElCol>
      <ElCol :span="4">
        <DataExportPopover/>
      </ElCol>
    </ElRow>
    <ElBlankLine :height="12" v-if="isCurrentUser"/>
    <!--允许过滤-->
    <ElRow :gutter="5">
      <ElCol :span="6">
        <ElInput v-model="searchTerm1" placeholder="按名字搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('name')"><ElIcon name="search" /></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="6">
        <ElInput v-model="searchTerm2" placeholder="按分类名搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('categoryName')"><ElIcon name="search" /></ElButton>
          </template>
        </ElInput>
      </ElCol>
      <ElCol :span="6">
        <ElInput v-model="searchTerm3" placeholder="按标签名搜索">
          <template v-slot:append>
            <ElButton @click="handleSearch('tagName')"><ElIcon name="search" /></ElButton>
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

    <ElCardGroup v-if="collectPage">
      <TheSorter type="collect" :pageable-param.sync="pageableParam" />
      <CollectOverviewCard v-for="collect in collectPage.content" :key="collect.id" :collect="collect" />
      <ThePagination :page="collectPage" :pageable-param.sync="pageableParam" />
    </ElCardGroup>

    <NewCollectDialog :visible.sync="newCollectDialogVisible" @submit="handleSubmit" />
    <NewCategoryDialog :visible.sync="newCategoryDialogVisible" />
    <NewTagDialog :visible.sync="newTagDialogVisible" />
  </div>
</template>

<script lang="ts">
  import CollectOverviewCard from "@/components/card/CollectOverviewCard.vue"
  import NewCategoryDialog from "@/components/dialog/NewCategoryDialog.vue"
  import NewCollectDialog from "@/components/dialog/NewCollectDialog.vue"
  import NewTagDialog from "@/components/dialog/NewTagDialog.vue"
  import DataExportPopover from "@/components/popover/DataExportPopover.vue"
  import DataImportPopover from "@/components/popover/DataImportPopover.vue"
  import ElBlankLine from "@/components/public/ElBlankLine.vue"
  import ElCardGroup from "@/components/public/ElCardGroup.vue"
  import ThePagination from "@/components/root/ThePagination.vue"
  import TheSorter from "@/components/root/TheSorter.vue"
  import {Collect, CollectSearchType, CollectType, Page, PageableParam} from "@/domain"
  import {collectTypes} from "@/enums"
  import * as collectService from "@/services/collectService"
  import {Component, Vue, Watch} from "vue-property-decorator"
  import {Route} from "vue-router"

  @Component({
    components: {
      DataImportPopover,
      DataExportPopover,
      TheSorter,
      ElBlankLine,
      NewTagDialog,
      NewCategoryDialog,
      NewCollectDialog,
      ThePagination,
      CollectOverviewCard,
      ElCardGroup
    }
  })
  export default class ProfileDetailCollects extends Vue {
    private searchTerm1: string = ""
    private searchTerm2: string = ""
    private searchTerm3: string = ""
    private searchTerm4: CollectType = "NONE"
    private searchType: CollectSearchType = "none"
    private pageableParam: PageableParam = {page: 0, size: 20}
    private collectPage: Page<Collect> | null = null
    private collectTypes = collectTypes
    private newCollectDialogVisible = false
    private newCategoryDialogVisible = false
    private newTagDialogVisible = false
    private importPopoverVisible = false
    private exportPopoverVisible = false

    private get userId() {
      return parseInt(this.$route.params["id"] as string)
    }

    private get isCurrentUser() {
      return this.$store.getters.currentUser && this.$store.getters.currentUser.id === this.userId
    }

    created() {
      this.getCollectPage()
    }

    @Watch("$route")
    private onRouteChange(value: Route, oldValue: Route) {
      if (value.params.id && value.params.id !== oldValue.params.id) {
        this.getCollectPage()
      }
    }

    @Watch("pageableParam")
    private onPageableParamChange(value: PageableParam, oldValue: PageableParam) {
      console.log(`分页参数发生了变化：`, value)
      this.getCollectPage()
    }

    //导入收藏后需要重新加载数据
    private handleImport() {
      this.getCollectPage()
    }

    private handleCreateCollect() {
      this.newCollectDialogVisible = true
    }

    private handleCreateCategory() {
      this.newCategoryDialogVisible = true
    }

    private handleCreateTag() {
      this.newTagDialogVisible = true
    }

    private handleSubmit() {
      this.getCollectPage()
    }

    private handleSearch(type: CollectSearchType) {
      this.searchType = type
      this.getCollectPage()
    }

    private handleCommand(value: CollectType) {
      this.searchTerm4 = value
      this.handleSearch("type")
    }

    private async getCollectPage() {
      try {
        switch (this.searchType) {
          case "none":
            this.collectPage = await collectService.findAllByUserId(this.userId, this.pageableParam)
            break
          case "name":
            this.collectPage = await collectService.findAllByNameContainsAndUserId(this.searchTerm1, this.userId, this.pageableParam)
            break
          case "categoryName":
            this.collectPage = await collectService.findAllByCategoryNameContainsAndUserId(this.searchTerm2, this.userId, this.pageableParam)
            break
          case "tagName":
            this.collectPage = await collectService.findAllByTagNameContainsAndUserId(this.searchTerm3, this.userId, this.pageableParam)
            break
          case "type":
            this.collectPage = await collectService.findAllByTypeAndUserId(this.searchTerm4, this.userId, this.pageableParam)
            break
          default:
            this.$message.error(`不能在这里使用这种方式查询收藏。`)
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
