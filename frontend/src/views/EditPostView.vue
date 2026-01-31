<template>
  <div class="edit-post-page">
    <div class="container">
      <div class="page-header-section">
        <h1 class="page-title">✏️ 編輯貼文</h1>
        <p class="page-subtitle">修改你的貼文內容</p>
      </div>
      
      <div v-if="loadingPost" class="loading-state">
        <div class="spinner"></div>
        <p>載入中...</p>
      </div>
      
      <div v-else class="post-form-card">
        <form @submit.prevent="handleSubmit">
          <div class="form-layout">
            <div class="form-left">
              <div class="form-group">
                <label class="form-label">貼文圖片</label>
                <ImageUploader v-model="form.image" />
              </div>
            </div>
            
            <div class="form-right">
              <div class="form-group">
                <label class="form-label">貼文內容 *</label>
                <textarea 
                  v-model="form.content"
                  class="form-input form-textarea"
                  placeholder="寫下你想分享的內容..."
                  rows="10"
                  required
                ></textarea>
              </div>
            </div>
          </div>
          
          <div class="form-actions">
            <button type="button" class="btn btn-secondary" @click="goBack">
              取消
            </button>
            <button 
              type="submit" 
              class="btn btn-primary"
              :disabled="loading || !form.content.trim()"
            >
              {{ loading ? '更新中...' : '更新貼文' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { postApi } from '../utils/api'
import { useToastStore } from '../stores/toast'
import ImageUploader from '../components/ImageUploader.vue'

const route = useRoute()
const router = useRouter()
const toastStore = useToastStore()

const postId = computed(() => route.params.id)
const form = ref({
  postId: null,
  content: '',
  image: ''
})
const loading = ref(false)
const loadingPost = ref(true)

async function fetchPost() {
  loadingPost.value = true
  try {
    const response = await postApi.getPostDetail(postId.value)
    if (response.data.status === 'SUCCESS' && response.data.data) {
      const postData = response.data.data.postVo
      form.value = {
        postId: postData.postId,
        content: postData.content || '',
        image: postData.image || ''
      }
    } else {
      toastStore.showError(['找不到貼文'])
      router.push('/')
    }
  } catch (error) {
    toastStore.showError(['載入貼文失敗'])
    router.push('/')
  } finally {
    loadingPost.value = false
  }
}

async function handleSubmit() {
  if (loading.value || !form.value.content.trim()) return
  
  loading.value = true
  try {
    const response = await postApi.editPost(form.value)
    
    if (response.data.status === 'SUCCESS') {
      toastStore.showSuccess(['貼文已更新！'])
      router.push(`/post/${postId.value}`)
    } else {
      toastStore.showError(response.data.message || ['更新失敗'])
    }
  } catch (error) {
    toastStore.showError(['更新失敗，請稍後再試'])
  } finally {
    loading.value = false
  }
}

function goBack() {
  router.push(`/post/${postId.value}`)
}

onMounted(() => {
  fetchPost()
})
</script>

<style scoped>
.edit-post-page {
  padding: 40px 0;
}

.page-header-section {
  text-align: center;
  margin-bottom: 32px;
  padding: 32px;
  background: linear-gradient(135deg, var(--primary-color), #8b5cf6);
  border-radius: var(--radius-lg);
  color: white;
}

.page-title {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 8px;
}

.page-subtitle {
  opacity: 0.9;
}

.loading-state {
  text-align: center;
  padding: 60px 20px;
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
}

.spinner {
  width: 50px;
  height: 50px;
  border: 4px solid var(--border-color);
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.post-form-card {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  padding: 32px;
}

.form-layout {
  display: flex;
  gap: 32px;
  margin-bottom: 24px;
}

.form-left {
  flex: 0 0 40%;
}

.form-right {
  flex: 1;
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
  min-height: 200px;
  line-height: 1.6;
}

.form-input:focus {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
  outline: none;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  padding-top: 24px;
  border-top: 1px solid var(--border-color);
}

@media (max-width: 768px) {
  .form-layout {
    flex-direction: column;
  }
  
  .form-left {
    flex: none;
  }
}
</style>
