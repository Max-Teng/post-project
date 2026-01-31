<template>
  <div class="home-page">
    <div class="container">
      <div class="page-header-section">
        <h1 class="page-title">📝 貼文牆</h1>
        <p class="page-subtitle">探索最新的貼文動態</p>
      </div>
      
      <div class="posts-header">
        <h2 class="posts-title">所有貼文</h2>
        <router-link v-if="authStore.isLoggedIn" to="/post/create" class="btn btn-primary">
          ✏️ 新增貼文
        </router-link>
      </div>
      
      <div v-if="loading" class="loading-state">
        <div class="spinner"></div>
        <p>載入中...</p>
      </div>
      
      <div v-else-if="posts.length === 0" class="empty-state">
        <div class="empty-icon">📭</div>
        <h3>目前沒有貼文</h3>
        <p>成為第一個發文的人吧！</p>
      </div>
      
      <div v-else class="posts-grid">
        <PostCard 
          v-for="post in posts" 
          :key="post.id" 
          :post="post" 
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { postApi } from '../utils/api'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'
import PostCard from '../components/PostCard.vue'

const authStore = useAuthStore()
const toastStore = useToastStore()
const posts = ref([])
const loading = ref(true)

async function fetchPosts() {
  loading.value = true
  try {
    const response = await postApi.getAllPosts()
    if (response.data.status === 'SUCCESS') {
      posts.value = response.data.data || []
    } else {
      toastStore.showError(response.data.message || ['載入貼文失敗'])
    }
  } catch (error) {
    console.error('Failed to fetch posts:', error)
    toastStore.showError(['載入貼文失敗，請稍後再試'])
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchPosts()
})
</script>

<style scoped>
.home-page {
  padding: 40px 0;
}

.page-header-section {
  text-align: center;
  margin-bottom: 40px;
  padding: 40px;
  background: linear-gradient(135deg, var(--primary-color), #8b5cf6);
  border-radius: var(--radius-lg);
  color: white;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 8px;
}

.page-subtitle {
  opacity: 0.9;
  font-size: 1.1rem;
}

.posts-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.posts-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--text-primary);
}

.loading-state,
.empty-state {
  text-align: center;
  padding: 60px 20px;
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
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

.empty-icon {
  font-size: 4rem;
  margin-bottom: 16px;
}

.empty-state h3 {
  font-size: 1.25rem;
  margin-bottom: 8px;
  color: var(--text-primary);
}

.empty-state p {
  color: var(--text-secondary);
}

.posts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 24px;
}

@media (max-width: 768px) {
  .posts-grid {
    grid-template-columns: 1fr;
  }
  
  .page-title {
    font-size: 1.75rem;
  }
}
</style>
