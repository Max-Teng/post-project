<template>
  <div class="post-card" @click="goToDetail">
    <div class="post-header">
      <img 
        :src="post.author?.coverImage || defaultAvatar" 
        :alt="post.author?.userName"
        class="author-avatar"
      />
      <div class="author-info">
        <span class="author-name">{{ post.author?.userName || '匿名用戶' }}</span>
      </div>
    </div>
    <div class="post-body">
      <div v-if="post.image" class="post-image-container">
        <img :src="post.image" :alt="post.content" class="post-image" />
      </div>
      <p class="post-content">{{ truncatedContent }}</p>
    </div>
    <div class="post-footer">
      <span class="read-more">閱讀更多 →</span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  post: {
    type: Object,
    required: true
  }
})

const router = useRouter()
const defaultAvatar = 'https://ui-avatars.com/api/?name=User&background=6366f1&color=fff'

const truncatedContent = computed(() => {
  const content = props.post.content || ''
  return content.length > 100 ? content.substring(0, 100) + '...' : content
})

function goToDetail() {
  router.push(`/post/${props.post.id}`)
}
</script>

<style scoped>
.post-card {
  background: var(--bg-primary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  overflow: hidden;
  cursor: pointer;
  transition: var(--transition);
}

.post-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
}

.post-header {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px 20px;
  border-bottom: 1px solid var(--border-color);
}

.author-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.author-name {
  font-weight: 600;
  color: var(--text-primary);
}

.post-body {
  padding: 20px;
}

.post-image-container {
  margin-bottom: 16px;
  border-radius: var(--radius-md);
  overflow: hidden;
}

.post-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.post-card:hover .post-image {
  transform: scale(1.05);
}

.post-content {
  color: var(--text-secondary);
  line-height: 1.6;
}

.post-footer {
  padding: 16px 20px;
  background: var(--bg-secondary);
}

.read-more {
  color: var(--primary-color);
  font-weight: 500;
  font-size: 0.9rem;
}
</style>
