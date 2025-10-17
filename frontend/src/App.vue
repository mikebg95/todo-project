<script setup>

import HomepageView from "@/views/TodoView.vue";
</script>

<template>
  <div>
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/info">Info</router-link> |
      <router-link to="/dashboard">Dashboard</router-link> |
      <router-link to="/todo">To-do</router-link>
    </nav>

    <router-view />
  </div>
</template>

<style scoped></style>
