<template>
  <div class="post-detail-page">
    <div class="container">
      <button class="btn btn-secondary back-btn" @click="goBack">
        ← 返回
      </button>

      <ConfirmModal
        v-if="showDeleteModal"
        title="刪除貼文"
        message="確定要刪除這篇貼文嗎？刪除後無法恢復。"
        confirm-text="確認刪除"
        cancel-text="取消"
        @confirm="confirmDelete"
        @cancel="showDeleteModal = false"
      />
      
      <div v-if="loading" class="loading-state">
        <div class="spinner"></div>
        <p>載入中...</p>
      </div>
      
      <template v-else-if="postDetail">
        <!-- Post Content -->
        <div class="post-card">
          <div class="post-header">
            <div class="author-info">
              <img 
                :src="postDetail.postVo?.author?.coverImage || defaultAvatar" 
                :alt="postDetail.postVo?.author?.userName"
                class="author-avatar"
              />
              <div>
                <span class="author-name">{{ postDetail.postVo?.author?.userName || '匿名用戶' }}</span>
              </div>
            </div>
            
            <div v-if="isOwner" class="post-actions">
              <router-link :to="`/post/${postId}/edit`" class="btn btn-secondary">
                ✏️ 編輯
              </router-link>
              <button class="btn btn-danger" @click="handleDelete" :disabled="deleting">
                {{ deleting ? '刪除中...' : '🗑️ 刪除' }}
              </button>
            </div>
          </div>
          
          <div class="post-body">
            <div class="post-content-wrapper">
              <div v-if="postDetail.postVo?.image" class="post-image-section">
                <img :src="postDetail.postVo.image" alt="Post image" class="post-image" />
              </div>
              <div class="post-text-section">
                <p class="post-content">{{ postDetail.postVo?.content }}</p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Comments Section -->
        <div class="comments-section">
          <h3 class="comments-title">💬 留言區 ({{ postDetail.commentVoList?.length || 0 }})</h3>
          
          <div v-if="postDetail.commentVoList?.length === 0" class="no-comments">
            <p>還沒有留言，成為第一個留言的人吧！</p>
          </div>
          
          <div v-else class="comments-list">
            <CommentItem 
              v-for="(comment, index) in postDetail.commentVoList" 
              :key="index"
              :comment="comment"
            />
          </div>
          
          <CommentBox 
            v-if="authStore.isLoggedIn"
            :post-id="postId"
            @comment-created="refreshPost"
          />
          <div v-else class="login-prompt">
            <p>請 <router-link to="/login">登入</router-link> 後才能留言</p>
          </div>
        </div>
      </template>
      
      <div v-else class="error-state">
        <p>找不到貼文</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { postApi } from '../utils/api'
import { useAuthStore } from '../stores/auth'
import { useToastStore } from '../stores/toast'
import CommentItem from '../components/CommentItem.vue'
import CommentBox from '../components/CommentBox.vue'
import ConfirmModal from '../components/ConfirmModal.vue'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const toastStore = useToastStore()

const postId = computed(() => route.params.id)
const postDetail = ref(null)
const loading = ref(true)
const deleting = ref(false)
const showDeleteModal = ref(false)
const defaultAvatar = 'https://ui-avatars.com/api/?name=User&background=6366f1&color=fff'

const isOwner = computed(() => {
  if (!authStore.user || !postDetail.value?.postVo?.author) return false
  return authStore.user.id === postDetail.value.postVo.author.id ||
         authStore.user.userName === postDetail.value.postVo.author.userName
})

async function fetchPostDetail() {
  loading.value = true
  try {
    const response = await postApi.getPostDetail(postId.value)
    if (response.data.status === 'SUCCESS') {
      postDetail.value = response.data.data
    } else {
      toastStore.showError(response.data.message || ['載入貼文失敗'])
    }
  } catch (error) {
    console.error('Failed to fetch post:', error)
    toastStore.showError(['載入貼文失敗'])
  } finally {
    loading.value = false
  }
}

async function handleDelete() {
  showDeleteModal.value = true
}

async function confirmDelete() {
  showDeleteModal.value = false
  
  deleting.value = true
  try {
    const response = await postApi.deletePost(postId.value)
    if (response.data.status === 'SUCCESS') {
      toastStore.showSuccess(['貼文已刪除'])
      router.push('/')
    } else {
      toastStore.showError(response.data.message || ['刪除失敗'])
    }
  } catch (error) {
    toastStore.showError(['刪除失敗'])
  } finally {
    deleting.value = false
  }
}

function refreshPost() {
  fetchPostDetail()
}

function goBack() {
  router.push('/')
}

onMounted(() => {
  fetchPostDetail()
})
</script>

<style scoped>
.post-detail-page {
  padding: 40px 0;
}

.back-btn {
  margin-bottom: 24px;
}

.loading-state,
.error-state {
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

.post-card {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  overflow: hidden;
  margin-bottom: 24px;
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid var(--border-color);
}

.author-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.author-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
}

.author-name {
  font-weight: 600;
  font-size: 1.1rem;
  color: var(--text-primary);
}

.post-actions {
  display: flex;
  gap: 12px;
}

.post-body {
  padding: 24px;
}

.post-content-wrapper {
  display: flex;
  gap: 24px;
}

.post-image-section {
  flex: 0 0 40%;
}

.post-image {
  width: 100%;
  border-radius: var(--radius-md);
  object-fit: cover;
}

.post-text-section {
  flex: 1;
}

.post-content {
  font-size: 1.1rem;
  line-height: 1.8;
  color: var(--text-primary);
  white-space: pre-wrap;
}

.comments-section {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  overflow: hidden;
}

.comments-title {
  padding: 20px 24px;
  border-bottom: 1px solid var(--border-color);
  font-size: 1.25rem;
  color: var(--text-primary);
}

.comments-list {
  padding: 20px 24px;
}

.no-comments {
  padding: 40px;
  text-align: center;
  color: var(--text-secondary);
}

.login-prompt {
  padding: 20px 24px;
  text-align: center;
  background: var(--bg-secondary);
  color: var(--text-secondary);
}

.login-prompt a {
  color: var(--primary-color);
  font-weight: 500;
}

@media (max-width: 768px) {
  .post-content-wrapper {
    flex-direction: column;
  }
  
  .post-image-section {
    flex: none;
  }
  
  .post-actions {
    flex-direction: column;
    gap: 8px;
  }
}
</style>
