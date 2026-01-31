import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getToken, setToken, removeToken, getCurrentUser, isLoggedIn as checkLoggedIn } from '../utils/auth'

export const useAuthStore = defineStore('auth', () => {
    const user = ref(getCurrentUser())

    const isLoggedIn = computed(() => checkLoggedIn())

    function login(token) {
        setToken(token)
        user.value = getCurrentUser()
    }

    function logout() {
        removeToken()
        user.value = null
    }

    function refreshUser() {
        user.value = getCurrentUser()
    }

    return {
        user,
        isLoggedIn,
        login,
        logout,
        refreshUser
    }
})
