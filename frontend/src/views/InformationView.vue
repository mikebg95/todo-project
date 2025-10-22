<script setup>
import { useUiStore } from '@/store/ui';
import { storeToRefs } from 'pinia';

const ui = useUiStore();

// make store state/getters reactive in the template
const { isLoading, loadingCount } = storeToRefs(ui);

function startLoading() {
  ui.startLoading();
}

function stopLoading() {
  ui.stopLoading();
}

function resetLoading() {
  ui.resetLoading();
}
</script>

<template>
  <div class="information">
    <h1>Information</h1>
    <button @click="startLoading">Start loading!</button>
    <button @click="stopLoading">Stop loading!</button>
    <button @click="resetLoading">Reset loading!</button>

    <p>isLoading: {{ isLoading }}</p>
    <p>loadingCount: {{ loadingCount }}</p>
  </div>
</template>

<style scoped>
button {
  display: block;
  margin-bottom: 8px;
  cursor: pointer;
  border: 1px solid red;
}
</style>