import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import keycloak from "./auth/keycloak";
import { useUserStore } from "@/store/index.js";
import "@/scss/main.scss";
import * as lucide from "lucide-vue-next"; // ✅ import all Lucide icons

const pinia = createPinia();

keycloak.init({
    onLoad: "check-sso",
    pkceMethod: "S256",
    checkLoginIframe: false
})
.then(() => {
    const app = createApp(App);

    console.log("tokenParsed:")
    console.log(keycloak.tokenParsed)
    console.log("")

    console.log("token:")
    console.log(keycloak.token)
    console.log("")

    console.log("idToken")
    console.log(keycloak.idToken)
    console.log("")

    console.log("refreshToken")
    console.log(keycloak.refreshToken)
    console.log("")


    app.use(router);
    app.use(pinia);

    // register lucide icons globally
    Object.entries(lucide).forEach(([name, component]) => {
        app.component(name, component);
    });

    // Initialize user store after Keycloak
    const userStore = useUserStore();
    userStore.setUser(
        keycloak.authenticated,
        keycloak.tokenParsed?.realm_access?.roles || [],
        keycloak.tokenParsed
    );

    app.mount("#app");
});
