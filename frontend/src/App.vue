<script setup>
import NavbarComponent from "@/components/NavbarComponent.vue";
import { useUiStore } from "@/store/ui.js";
import { DotLoader } from "vue3-spinner";
import { watch, onBeforeUnmount } from "vue";
import { setInteractionLocked, unlockUI } from "@/utils/interactionsLock.js";

const ui = useUiStore();

// Toggle global interaction lock when spinner is active
watch(() => ui.isLoading, setInteractionLocked, { immediate: true });

// Safety: ensure unlock on unmount
onBeforeUnmount(() => unlockUI());
</script>

<template>
  <!-- spinner overlay; app stays mounted underneath -->
  <div v-if="ui.isLoading" class="spinner-overlay">
    <DotLoader size="50px" />
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
}
</style>