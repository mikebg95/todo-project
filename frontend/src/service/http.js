import keycloak from '@/auth/keycloak';
import router from '@/router';

async function getValidToken() {
    await keycloak.updateToken(30).catch(() => {});
    if (!keycloak.authenticated) throw new Error('Not authenticated');
    return keycloak.token;
}

export async function apiFetch(path, options = {}) {
    const token = await getValidToken();
    const headers = {
        ...options.headers,
        Authorization: `Bearer ${token}`,
    };

    const res = await fetch(path, { ...options, headers });

    // --- minimal 401 / 403 handling ---
    if (res.status === 401) {
        // Token invalid or expired → re-login
        keycloak.login({ redirectUri: window.location.href });
        return;
    }

    if (res.status === 403) {
        // Authenticated but not allowed → redirect to Forbidden page
        router.push('/forbidden');
        return;
    }
    // ----------------------------------

    if (!res.ok) {
        throw new Error(`${options.method || 'GET'} ${path} -> ${res.status}`);
    }

    const ct = res.headers.get('content-type') || '';
    return ct.includes('application/json') ? res.json() : res.text();
}