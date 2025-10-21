import { mount, RouterLinkStub } from '@vue/test-utils'
import { createTestingPinia } from '@pinia/testing'
import { createRouter, createMemoryHistory } from 'vue-router'
import App from '../App.vue'

const router = createRouter({
    history: createMemoryHistory(),
    routes: [{ path: '/', component: { template: '<div />' } }],
})

test('renders app without crashing', async () => {
    const pinia = createTestingPinia({ createSpy: vi.fn })
    await router.push('/'); await router.isReady()

    const wrapper = mount(App, {
        global: {
            plugins: [pinia, router],
            // avoid resolving actual links in a smoke test
            stubs: { RouterLink: RouterLinkStub },
        },
    })

    expect(wrapper.exists()).toBe(true)
})