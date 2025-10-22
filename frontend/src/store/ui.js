import { defineStore } from 'pinia';

export const useUiStore = defineStore('ui', {
    state: () => ({ loadingCount: 0 }),
    getters: { isLoading: (s) => s.loadingCount > 0 },
    actions: {
        startLoading() { this.loadingCount++; },
        stopLoading() { if (this.loadingCount > 0) this.loadingCount--; },
        resetLoading() { this.loadingCount = 0; },
    },
});