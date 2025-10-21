import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import keycloak from "./auth/keycloak";

keycloak.init({ onLoad: "check-sso", pkceMethod: "S256" }).then(() => {
    const app = createApp(App);
    app.use(router);
    app.mount("#app");
});