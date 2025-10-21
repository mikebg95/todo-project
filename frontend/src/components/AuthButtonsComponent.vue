<script setup>
import keycloak from "../auth/keycloak";

const login = () => keycloak.login();
const logout = () => keycloak.logout({ redirectUri: window.location.origin });
const signup = () => keycloak.register();
</script>

<template>
  <div style="display:flex; gap:8px; align-items:center">
    <router-link to="/profile" v-if="keycloak.authenticated">👋 {{ keycloak.tokenParsed?.preferred_username }}</router-link>
    <button v-if="!keycloak.authenticated" @click="login">Login</button>
    <button v-if="!keycloak.authenticated" @click="signup">Sign Up</button>
    <button v-else @click="logout">Logout</button>
  </div>
</template>

<style scoped>

</style>