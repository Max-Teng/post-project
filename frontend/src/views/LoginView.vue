<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <h1 class="auth-title">👋 歡迎回來</h1>
          <p class="auth-subtitle">登入您的帳號</p>
        </div>
        
        <form @submit.prevent="handleLogin" class="auth-form">
          <div class="form-group">
            <label class="form-label">使用者名稱</label>
            <input 
              v-model="form.userName"
              type="text"
              class="form-input"
              placeholder="請輸入使用者名稱"
              required
            />
          </div>
          
          <div class="form-group">
            <label class="form-label">密碼</label>
            <input 
              v-model="form.password"
              type="password"
              class="form-input"
              placeholder="請輸入密碼"
              required
            />
          </div>
          
          <button 
            type="submit" 
            class="btn btn-primary btn-block"
            :disabled="loading"
          >
            {{ loading ? '登入中...' : '登入' }}
          </button>
        </form>
        
        <div class="auth-footer">
          <p>還沒有帳號？ <router-link to="/register">立即註冊</router-link></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authApi } from '../utils/api'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'

const router = useRouter()
const authStore = useAuthStore()
const toastStore = useToastStore()

const form = ref({
  userName: '',
  password: ''
})
const loading = ref(false)

async function handleLogin() {
  if (loading.value) return
  
  loading.value = true
  try {
    const response = await authApi.login(form.value)
    
    if (response.data.status === 'SUCCESS') {
      const token = response.data.data
      authStore.login(token)
      toastStore.showSuccess(['登入成功！'])
      router.push('/')
    } else {
      toastStore.showError(response.data.message || ['登入失敗'])
    }
  } catch (error) {
    toastStore.showError(['登入失敗，請稍後再試'])
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: calc(100vh - 70px);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.auth-container {
  width: 100%;
  max-width: 420px;
}

.auth-card {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
  padding: 40px;
}

.auth-header {
  text-align: center;
  margin-bottom: 32px;
}

.auth-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.auth-subtitle {
  color: var(--text-secondary);
}

.auth-form {
  margin-bottom: 24px;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: var(--text-primary);
}

.form-input {
  width: 100%;
  padding: 14px 16px;
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  font-size: 1rem;
  transition: var(--transition);
}

.form-input:focus {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
  outline: none;
}

.btn-block {
  width: 100%;
  padding: 16px;
  font-size: 1rem;
}

.btn-block:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.auth-footer {
  text-align: center;
  color: var(--text-secondary);
}

.auth-footer a {
  color: var(--primary-color);
  font-weight: 500;
}

.auth-footer a:hover {
  text-decoration: underline;
}
</style>
