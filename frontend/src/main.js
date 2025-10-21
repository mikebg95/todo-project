import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import keycloak from "./auth/keycloak";
import {useUserStore} from "@/store/index.js";

const pinia = createPinia();

keycloak.init({ onLoad: "check-sso", pkceMethod: "S256" }).then(() => {
    const app = createApp(App);
    app.use(router);
    app.use(pinia);

    const userStore = useUserStore();
    userStore.setUser(
        keycloak.authenticated,
        keycloak.tokenParsed?.realm_access?.roles || [],
        keycloak.tokenParsed
    );

    app.mount("#app");
});