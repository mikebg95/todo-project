import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        isAuthenticated: false,
        roles: [],
        username: "",
        firstName: "",
        lastName: "",
        email: "",
    }),
    actions: {
        setUser(authenticated, roles, tokenParsed) {
            const allowed = ["USER_ROLE", "ADMIN_ROLE"];
            this.isAuthenticated = authenticated;
            this.roles = roles.filter(r => allowed.includes(r));

            this.username = tokenParsed?.preferred_username || "";
            this.firstName = tokenParsed?.given_name || "";
            this.lastName = tokenParsed?.family_name || "";
            this.email = tokenParsed?.email || "";
        }
    },
});