<script setup>
import NavbarComponent from "@/components/NavbarComponent.vue";

</script>

<template>
  <div class="app-container">
    <NavbarComponent />

    <router-view class="container" />
  </div>
</template>

<style scoped>
.container {
  padding: 16px;
}
</style>
