import { mount } from '@vue/test-utils'
import App from '../App.vue'

test('renders app without crashing', () => {
    const wrapper = mount(App)
    expect(wrapper.exists()).toBe(true)
})
