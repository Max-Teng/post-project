import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getToken, setToken, removeToken, getCurrentUser, isLoggedIn as checkLoggedIn } from '../utils/auth'

export const useAuthStore = defineStore('auth', () => {
    const user = ref(getCurrentUser())

    // Make isLoggedIn reactive by depending on user.value
    const isLoggedIn = computed(() => {
        return !!user.value && checkLoggedIn()
    })

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
