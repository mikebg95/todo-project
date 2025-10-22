import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        isAuthenticated: false,
        userInfo: {
            roles: [],
            username: "",
            firstName: "",
            lastName: "",
            email: "",
        },
        loading: false,
    }),

    actions: {
        setUser(authenticated, roles, tokenParsed) {
            const allowed = ["USER_ROLE", "ADMIN_ROLE"];
            this.isAuthenticated = authenticated;

            this.userInfo = {
                roles: roles.filter(r => allowed.includes(r)),
                username: tokenParsed?.preferred_username || "",
                firstName: tokenParsed?.given_name || "",
                lastName: tokenParsed?.family_name || "",
                email: tokenParsed?.email || "",
            };
        },
        setLoading(loading) {
            this.isLoading = loading;
        },
    },
});
