<template>
  <div class="comment-item">
    <img 
      :src="comment.author?.coverImage || defaultAvatar" 
      :alt="comment.author?.userName"
      class="comment-avatar"
    />
    <div class="comment-bubble">
      <span class="comment-author">{{ comment.author?.userName || '匿名用戶' }}</span>
      <p class="comment-content">{{ comment.content }}</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  comment: {
    type: Object,
    required: true
  }
})

const defaultAvatar = 'https://ui-avatars.com/api/?name=User&background=6366f1&color=fff'
</script>

<style scoped>
.comment-item {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.comment-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  object-fit: cover;
  flex-shrink: 0;
}

.comment-bubble {
  background: var(--bg-secondary);
  padding: 12px 16px;
  border-radius: 18px;
  border-top-left-radius: 4px;
  max-width: 80%;
}

.comment-author {
  font-weight: 600;
  font-size: 0.9rem;
  color: var(--text-primary);
  display: block;
  margin-bottom: 4px;
}

.comment-content {
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.5;
}
</style>
