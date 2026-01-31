<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <h1 class="auth-title">🎉 加入我們</h1>
          <p class="auth-subtitle">建立您的新帳號</p>
        </div>
        
        <form @submit.prevent="handleRegister" class="auth-form">
          <div class="form-group">
            <label class="form-label">手機號碼 *</label>
            <input 
              v-model="form.phoneNumber"
              type="tel"
              class="form-input"
              placeholder="09xxxxxxxx"
              pattern="^09\d{8}$"
              required
            />
          </div>
          
          <div class="form-group">
            <label class="form-label">Email</label>
            <input 
              v-model="form.email"
              type="email"
              class="form-input"
              placeholder="your@email.com"
            />
          </div>
          
          <div class="form-group">
            <label class="form-label">密碼 *</label>
            <input 
              v-model="form.password"
              type="password"
              class="form-input"
              placeholder="請輸入密碼"
              required
            />
          </div>
          
          <div class="form-group">
            <label class="form-label">自我介紹</label>
            <textarea 
              v-model="form.biography"
              class="form-input form-textarea"
              placeholder="簡單介紹一下自己..."
              rows="3"
            ></textarea>
          </div>
          
          <div class="form-group">
            <label class="form-label">頭像照片</label>
            <ImageUploader v-model="form.coverImage" />
          </div>
          
          <button 
            type="submit" 
            class="btn btn-primary btn-block"
            :disabled="loading"
          >
            {{ loading ? '註冊中...' : '註冊' }}
          </button>
        </form>
        
        <div class="auth-footer">
          <p>已有帳號？ <router-link to="/login">立即登入</router-link></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authApi } from '../utils/api'
import { useToastStore } from '../stores/toast'
import ImageUploader from '../components/ImageUploader.vue'

const router = useRouter()
const toastStore = useToastStore()

const form = ref({
  phoneNumber: '',
  email: '',
  password: '',
  coverImage: '',
  biography: ''
})
const loading = ref(false)

async function handleRegister() {
  if (loading.value) return
  
  loading.value = true
  try {
    const response = await authApi.register(form.value)
    
    if (response.data.status === 'SUCCESS') {
      toastStore.showSuccess(['註冊成功！請登入'])
      router.push('/login')
    } else {
      toastStore.showError(response.data.message || ['註冊失敗'])
    }
  } catch (error) {
    toastStore.showError(['註冊失敗，請稍後再試'])
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
  max-width: 480px;
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

.form-textarea {
  resize: vertical;
  min-height: 80px;
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
