import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        isAuthenticated: false,
        user: {
            roles: [],
            username: "",
            firstName: "",
            lastName: "",
            email: "",
        },
        loadingCount: 0,
    }),

    getters: {
        isLoading: (state) => state.loadingCount > 0,
    },

    actions: {
        setUser(authenticated, roles, tokenParsed) {
            const allowed = ["USER_ROLE", "ADMIN_ROLE"];
            this.isAuthenticated = authenticated;

            this.user = {
                roles: roles.filter(r => allowed.includes(r)),
                username: tokenParsed?.preferred_username || "",
                firstName: tokenParsed?.given_name || "",
                lastName: tokenParsed?.family_name || "",
                email: tokenParsed?.email || "",
            };
        },

        startLoading() {
            this.loadingCount++;
        },

        stopLoading() {
            if (this.loadingCount > 0) this.loadingCount--;
        },

        resetLoading() {
            this.loadingCount = 0;
        },
    },
});
