import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import keycloak from "./auth/keycloak";
import { useUserStore } from "@/store/user.js";
import { useUiStore } from "@/store/ui.js";
import "@/scss/main.scss";
import * as lucide from "lucide-vue-next";

async function bootstrap() {
    await keycloak.init({
        onLoad: "check-sso",
        pkceMethod: "S256",
        checkLoginIframe: false,
        responseMode: "query",
    });

    // Import router AFTER keycloak init to avoid loading URLs with fragments
    const { default: router } = await import("./router");

    const app = createApp(App);
    app.use(createPinia());

    const uiStore = useUiStore();
    uiStore.startLoading();

    app.use(router);

    // register lucide icons globally
    Object.entries(lucide).forEach(([name, component]) => {
        app.component(name, component);
    });

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

    uiStore.stopLoading();

    // Clear URL fragments if any remain after login
    if (window.location.hash && window.location.hash.includes("code")) {
        window.history.replaceState({}, document.title, window.location.pathname);
    }
}

await bootstrap();
