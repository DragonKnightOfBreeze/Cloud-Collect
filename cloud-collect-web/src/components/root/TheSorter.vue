<template>
  <ElForm id="app-sorter" class="align-center" size="small" inline>
    <ElFormItem size="small" label="排序方式">
      <ElSelect size="small" v-model="syncPageableParam.sort" :value="syncPageableParam.sort" placeholder="请选择"
                @change="handleChange">
        <ElOption v-for="option in sortOptions" :key="option.label" :label="option.label" :value="option.value"></ElOption>
      </ElSelect>
    </ElFormItem>
  </ElForm>
</template>

<script lang="ts">
  import {Option, PageableParam, SortType} from "@/types"
  import {Component, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class CollectSortSelect extends Vue {
    @Prop({required: true}) type!: SortType
    @PropSync("pageableParam", {required: true}) syncPageableParam!: PageableParam

    private collectSortOptions: Option<string>[] = [
      {label: "按名字顺序", value: "name,asc"},
      {label: "按名字倒序", value: "name,desc"},
      {label: "按分类顺序", value: "category,asc"},
      {label: "按分类倒序", value: "category,desc"},
      {label: "按创建时间顺序", value: "createdTime,asc"},
      {label: "按创建时间倒序", value: "createdTime,desc"},
      {label: "按修改时间顺序", value: "lastModifiedTime,asc"},
      {label: "按修改时间倒序", value: "lastModifiedTime,desc"}
    ]
    private categoryOrTagSortOptions: Option<string>[] = [
      {label: "按名字顺序", value: "name,asc"},
      {label: "按名字倒序", value: "name,desc"},
      {label: "按创建时间顺序", value: "createdTime,asc"},
      {label: "按创建时间倒序", value: "createdTime,desc"},
      {label: "按修改时间顺序", value: "lastModifiedTime,asc"},
      {label: "按修改时间倒序", value: "lastModifiedTime,desc"}
    ]
    private userSortOptions: Option<string>[] = [
      {label: "按昵称顺序", value: "nickname,asc"},
      {label: "按昵称倒序", value: "nickname,desc"},
      {label: "按用户名顺序", value: "username,asc"},
      {label: "按用户名倒序", value: "username,desc"},
      {label: "按注册时间顺序", value: "registerTime,asc"},
      {label: "按注册时间倒序", value: "registerTime,desc"}
    ]

    get sortOptions() {
      switch (this.type) {
        case "collect":
          return this.collectSortOptions
        case "category":
          return this.categoryOrTagSortOptions
        case "tag":
          return this.categoryOrTagSortOptions
        case "user":
          break
      }
    }

    //Vue监听器默认监听不到对象或数组内部的变化，所以需要使用这种取巧的方式使之能够监听

    private handleChange() {
      this.syncPageableParam = {...this.syncPageableParam}
    }
  }
</script>

<style scoped>
  #app-sorter {
    margin: 5px 5px;
  }
  .el-form-item {
    margin: 0 0;
  }
</style>
