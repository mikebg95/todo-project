import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/HomeView.vue'
import Information from '../views/InformationView.vue'
import Dashboard from '../views/DashboardView.vue'
import DashboardAdmin from '../views/DashboardAdminView.vue'
import Forbidden from '../views/ForbiddenView.vue'
import PageNotFound from '../views/PageNotFoundView.vue'
import Todo from "@/views/TodoView.vue";
import Profile from "@/views/ProfileView.vue"

const routes = [
    { path: '/', component: Home },
    { path: '/info', component: Information },
    { path: '/dashboard', component: Dashboard },
    { path: '/forbidden', component: Forbidden },
    { path: '/not-found', component: PageNotFound },
    { path: '/profile', component: Profile },
    { path: '/todo', component: Todo },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router