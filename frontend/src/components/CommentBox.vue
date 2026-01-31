<template>
  <div class="comment-box">
    <input 
      v-model="content"
      type="text"
      placeholder="寫下你的留言..."
      class="comment-input"
      @keyup.enter="submitComment"
    />
    <button 
      class="btn btn-primary comment-btn"
      :disabled="!content.trim() || loading"
      @click="submitComment"
    >
      {{ loading ? '送出中...' : '留言' }}
    </button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { commentApi } from '../utils/api'
import { useToastStore } from '../stores/toast'

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true
  }
})

const emit = defineEmits(['commentCreated'])

const content = ref('')
const loading = ref(false)
const toastStore = useToastStore()

async function submitComment() {
  if (!content.value.trim() || loading.value) return
  
  loading.value = true
  try {
    const response = await commentApi.createComment({
      postId: Number(props.postId),
      content: content.value
    })
    
    if (response.data.status === 'SUCCESS') {
      toastStore.showSuccess(['留言成功！'])
      content.value = ''
      emit('commentCreated')
    } else {
      toastStore.showError(response.data.message || ['留言失敗'])
    }
  } catch (error) {
    toastStore.showError(['留言失敗，請稍後再試'])
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.comment-box {
  display: flex;
  gap: 12px;
  padding: 20px;
  background: var(--bg-primary);
  border-top: 1px solid var(--border-color);
  border-radius: 0 0 var(--radius-lg) var(--radius-lg);
}

.comment-input {
  flex: 1;
  padding: 12px 20px;
  border-radius: var(--radius-full);
  border: 1px solid var(--border-color);
  background: var(--bg-secondary);
}

.comment-input:focus {
  border-color: var(--primary-color);
  background: var(--bg-primary);
}

.comment-btn {
  padding: 12px 24px;
  border-radius: var(--radius-full);
}

.comment-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
