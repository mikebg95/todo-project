import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import keycloak from "./auth/keycloak";
import { useUserStore } from "@/store/user.js";
import { useUiStore } from "@/store/ui.js";
import "@/scss/main.scss";
import * as lucide from "lucide-vue-next";

const pinia = createPinia();
const app = createApp(App);

app.use(pinia);
app.use(router);

// register lucide icons globally
Object.entries(lucide).forEach(([name, component]) => {
    app.component(name, component);
});

const uiStore = useUiStore();
uiStore.startLoading();

keycloak
    .init({
        onLoad: "check-sso",
        pkceMethod: "S256",
        checkLoginIframe: false,
        silentCheckSsoRedirectUri: `${window.location.origin}/silent-check-sso.html`, // redirect to silent-check-sso.html
    })
    .then(() => {
        const userStore = useUserStore();
        userStore.setUser(
            keycloak.authenticated,
            keycloak.tokenParsed?.realm_access?.roles || [],
            keycloak.tokenParsed
        );

        if (router.currentRoute.value.path === "/" && userStore.isAuthenticated) {
            router.replace("/dashboard");
        }

        app.mount("#app");
    })
    .finally(() => {
        uiStore.stopLoading();
    });