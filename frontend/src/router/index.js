import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/HomeView.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/LoginView.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/RegisterView.vue')
    },
    {
        path: '/post/:id',
        name: 'PostDetail',
        component: () => import('../views/PostDetailView.vue')
    },
    {
        path: '/post/create',
        name: 'CreatePost',
        component: () => import('../views/CreatePostView.vue')
    },
    {
        path: '/post/:id/edit',
        name: 'EditPost',
        component: () => import('../views/EditPostView.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
