import keycloak from '@/auth/keycloak'; // where you init keycloak

async function getValidToken() {
    await keycloak.updateToken(30); // refresh if expiring in 30s
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

    if (!res.ok) {
        // Optional: if (res.status === 401) keycloak.login();
        throw new Error(`${options.method || 'GET'} ${path} -> ${res.status}`);
    }

    const ct = res.headers.get('content-type') || '';
    return ct.includes('application/json') ? res.json() : res.text();
}