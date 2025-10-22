<script setup>
import AuthButtons from "@/components/AuthButtonsComponent.vue";
import { useUserStore } from "@/store/user.js";

const store = useUserStore();
</script>

<template>
  <nav>
    <div class="router-links" v-if="store.isAuthenticated">
<!--      <router-link to="/">Home</router-link> |-->
      <router-link to="/dashboard">Dashboard</router-link> |
      <router-link to="/info">Info</router-link> |
      <router-link to="/todo">To-do</router-link>
    </div>

    <AuthButtons class="auth" />
  </nav>
</template>

<style scoped lang="scss">

</style>
