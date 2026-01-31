<template>
  <nav class="navbar">
    <div class="navbar-container">
      <router-link to="/" class="navbar-brand">
        <span class="brand-icon">📝</span>
        <span class="brand-text">貼文系統</span>
      </router-link>
      
      <div class="navbar-right">
        <template v-if="authStore.isLoggedIn">
          <div class="user-info">
            <img 
              :src="authStore.user?.coverImage || defaultAvatar" 
              :alt="authStore.user?.userName"
              class="avatar"
            />
            <span class="user-name">{{ authStore.user?.userName }}</span>
          </div>
          <button class="btn btn-secondary" @click="handleLogout">
            登出
          </button>
        </template>
        <template v-else>
          <router-link to="/login" class="btn btn-secondary">
            登入
          </router-link>
          <router-link to="/register" class="btn btn-primary">
            註冊
          </router-link>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const defaultAvatar = 'https://ui-avatars.com/api/?name=User&background=6366f1&color=fff'

function handleLogout() {
  authStore.logout()
  router.push('/')
}
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--border-color);
  z-index: 1000;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.navbar-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--text-primary);
  transition: var(--transition);
}

.navbar-brand:hover {
  color: var(--primary-color);
}

.brand-icon {
  font-size: 1.5rem;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 16px;
  background: var(--secondary-color);
  border-radius: var(--radius-full);
}

.user-name {
  font-weight: 500;
  color: var(--text-primary);
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}
</style>
