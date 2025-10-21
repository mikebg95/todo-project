import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/HomeView.vue'
import Information from '../views/InformationView.vue'
import Dashboard from '../views/DashboardView.vue'
import Forbidden from '../views/ForbiddenView.vue'
import PageNotFound from '../views/PageNotFoundView.vue'
import Todo from "@/views/TodoView.vue";
import Profile from "@/views/ProfileView.vue"
import {useUserStore} from "@/store/index.js";

const routes = [
    {
        path: '/',
        component: Home,
    },
    {
        path: '/info',
        component: Information,
        meta: { requiresAuth: true }
    },
    {
        path: '/dashboard',
        component: Dashboard,
        meta: { requiresAuth: true }
    },
    {
        path: '/forbidden',
        component: Forbidden,
    },
    {
        path: '/not-found',
        component: PageNotFound,
    },
    {
        path: '/profile',
        component: Profile,
        meta: { requiresAuth: true }
    },
    {
        path: '/todo',
        component: Todo,
        meta: { requiresAuth: true }
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    const store = useUserStore();

    // if user is logged in, homepage redirects to dashboard
    if (to.path === "/" && store.isAuthenticated) {
        return next("/dashboard");
    }

    if (!to.meta?.requiresAuth) return next();

    if (!store.isAuthenticated) return next("/");

    // if (to.meta.role && !store.roles.includes(to.meta.role)) return next("/forbidden");

    return next();
});

export default router