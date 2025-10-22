<script setup>
import NavbarComponent from "@/components/NavbarComponent.vue";
import { useUiStore } from "@/store/ui.js";
import { DotLoader } from "vue3-spinner";

const ui = useUiStore();
</script>

<template>
  <div v-if="ui.isLoading" class="spinner-overlay">
    <DotLoader color="#333" size="50px" />
  </div>

  <div class="app-container">
    <NavbarComponent />
    <router-view class="container" />
  </div>
</template>

<style scoped>
.container { padding: 16px; }
.spinner-overlay {
  position: fixed; inset: 0;
  display: flex; justify-content: center; align-items: center;
  background: rgba(255,255,255,0.7);
  z-index: 9999;
  pointer-events: all;
}
</style>
