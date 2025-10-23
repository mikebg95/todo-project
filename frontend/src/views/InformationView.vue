<script setup>
import ItemService from "@/service/ItemService.js";

async function getAllItems() {
  try {
    await ItemService.getAllItems()
  } catch (e) {
    console.log(e)
  }
}

</script>

<template>
  <div class="information">
    <h1>Information</h1>

    <button @click="getAllItems">GET ALL ITEMS</button>
  </div>
</template>

<style scoped>
button {
  border: 2px solid red;
}
</style>