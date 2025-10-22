<script setup>
import keycloak from "../auth/keycloak";
import { useUiStore } from "@/store/ui.js";

const ui = useUiStore();

const login = () => {
  ui.startLoading();
  keycloak.login();
}
const logout = () => {
  ui.startLoading();
  keycloak.logout({ redirectUri: window.location.origin });
}
const signup = () => {
  ui.startLoading();
  keycloak.register();
}
</script>

<template>
  <div style="display:flex; gap:8px; align-items:center">
    <router-link to="/profile" v-if="keycloak.authenticated" class="profile-link">
      <CircleUser />
      {{ keycloak.tokenParsed?.preferred_username }}
    </router-link>
    <button v-if="!keycloak.authenticated" @click="login">Log in</button>
    <button v-if="!keycloak.authenticated" @click="signup">Sign Up</button>
    <button v-else @click="logout">Log out</button>
  </div>
</template>

<style scoped>
.profile-link {
  display: flex;
  align-items: center;
  margin-right: 8px;

  svg {
    margin-right: 4px;
  }
}
</style>