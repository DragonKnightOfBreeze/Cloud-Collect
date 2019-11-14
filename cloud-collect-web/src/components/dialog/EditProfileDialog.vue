<template>
  <!--TODO-->
</template>

<script lang="ts">
  import {User} from "@/types"
  import {Component, Emit, Prop, PropSync, Vue} from "vue-property-decorator"

  @Component
  export default class EditProfileDialog extends Vue {
    private formLabelWidth = "120px"

    @PropSync("visible") syncVisible!: boolean
    @Prop({required: true}) user!: User

    @Emit("submit")
    handleSubmit() {
      //TODO
    }
  }
</script>

<style scoped>

</style>
