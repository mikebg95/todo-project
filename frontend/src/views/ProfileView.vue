<script setup>
import { useUserStore } from "../store";
const store = useUserStore();
</script>

<template>
  <div>
    <p class="title">User Info</p>
    <p><strong>Username:</strong> {{ store.userInfo.username }}</p>
    <p><strong>First name:</strong> {{ store.userInfo.firstName }}</p>
    <p><strong>Last name:</strong> {{ store.userInfo.lastName }}</p>
    <p><strong>Email:</strong> {{ store.userInfo.email }}</p>
    <p><strong>Roles:</strong> {{ store.userInfo.roles.join(", ") }}</p>
  </div>
</template>

<style scoped>
h2 {
  margin-bottom: 0.5rem;
}
p {
  margin: 0.25rem 0;
}
</style>
