import { useUiStore } from '@/store/ui';

/**
 * Shows the global spinner only if the work lasts longer than delayMs (default 200ms).
 * Accepts a promise or a function returning a promise.
 */
export async function withLoading(promiseOrFn, delayMs = 200) {
    const ui = useUiStore();
    let started = false;
    const t = setTimeout(() => { ui.startLoading(); started = true; }, delayMs);
    try {
        const p = typeof promiseOrFn === 'function' ? promiseOrFn() : promiseOrFn;
        return await p;
    } finally {
        clearTimeout(t);
        if (started) ui.stopLoading();
    }
}
