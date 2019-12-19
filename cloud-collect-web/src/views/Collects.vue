<template>
    <keep-alive>
        <router-view></router-view>
    </keep-alive>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator"

    @Component
  export default class Collects extends Vue {
  }
</script>

<style scoped>

</style>
