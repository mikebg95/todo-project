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
app.use(router);
app.use(pinia);

// register lucide icons globally
Object.entries(lucide).forEach(([name, component]) => {
    app.component(name, component);
});

const uiStore = useUiStore();

console.log("Before startLoading →", uiStore.isLoading);
uiStore.startLoading();
console.log("After startLoading →", uiStore.isLoading);

keycloak
    .init({
        onLoad: "check-sso",
        pkceMethod: "S256",
        checkLoginIframe: false
    })
    .then(() => {
        const userStore = useUserStore();
        userStore.setUser(
            keycloak.authenticated,
            keycloak.tokenParsed?.realm_access?.roles || [],
            keycloak.tokenParsed
        );

        app.mount("#app");
    })
    .finally(() => {
        console.log("Before stopLoading →", uiStore.isLoading);
        uiStore.stopLoading();
        console.log("After stopLoading →", uiStore.isLoading);
    });