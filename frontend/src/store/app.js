import { defineStore } from 'pinia';

export const useAppStore = defineStore('app', {
    state: () => ({ ready: false }),
    actions: {
        setReady(v) {
            this.ready = v;
        }
    }
});