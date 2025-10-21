<script setup>
import { useUserStore } from "../store";
const store = useUserStore();
</script>

<template>
  <div>
    <h2>User Info</h2>
    <p><strong>Username:</strong> {{ store.username }}</p>
    <p><strong>First name:</strong> {{ store.firstName }}</p>
    <p><strong>Last name:</strong> {{ store.lastName }}</p>
    <p><strong>Email:</strong> {{ store.email }}</p>
    <p><strong>Roles:</strong> {{ store.roles.join(", ") }}</p>
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
