import keycloak from '@/auth/keycloak';
import router from '@/router';
import { useUiStore } from '@/store/ui.js'; // ✅ import the UI store

async function getValidToken() {
    await keycloak.updateToken(30).catch(() => {});
    if (!keycloak.authenticated) throw new Error('Not authenticated');
    return keycloak.token;
}

export async function apiFetch(path, options = {}) {
    const ui = useUiStore();
    ui.startLoading();

    try {
        const token = await getValidToken();
        const headers = {
            ...options.headers,
            Authorization: `Bearer ${token}`,
        };

        const res = await fetch(path, { ...options, headers });

        if (res.status === 401) {
            keycloak.login({ redirectUri: window.location.href });
            return;
        }

        if (res.status === 403) {
            router.push('/forbidden');
            return;
        }

        if (!res.ok) {
            throw new Error(`${options.method || 'GET'} ${path} -> ${res.status}`);
        }

        const ct = res.headers.get('content-type') || '';
        return ct.includes('application/json') ? res.json() : res.text();
    } finally {
        ui.stopLoading();
    }
}
