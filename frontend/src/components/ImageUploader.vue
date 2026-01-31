<template>
  <div class="image-uploader">
    <input 
      ref="fileInput"
      type="file" 
      accept="image/*"
      class="file-input"
      @change="handleFileChange"
    />
    
    <div 
      v-if="!imageUrl && !uploading"
      class="upload-placeholder"
      @click="triggerFileInput"
    >
      <div class="upload-icon">📷</div>
      <p class="upload-text">點擊上傳圖片</p>
      <p class="upload-hint">支援 JPG, PNG, GIF</p>
    </div>
    
    <div v-if="uploading" class="upload-loading">
      <div class="spinner"></div>
      <p>上傳中...</p>
    </div>
    
    <div v-if="imageUrl && !uploading" class="image-preview">
      <img :src="imageUrl" alt="Preview" class="preview-image" />
      <button class="remove-btn" @click="removeImage">✕</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { fileApi } from '../utils/api'
import { useToastStore } from '../stores/toast'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

const fileInput = ref(null)
const uploading = ref(false)
const imageUrl = ref(props.modelValue)
const toastStore = useToastStore()

function triggerFileInput() {
  fileInput.value?.click()
}

async function handleFileChange(event) {
  const file = event.target.files?.[0]
  if (!file) return
  
  uploading.value = true
  try {
    const response = await fileApi.upload(file)
    if (response.data.status === 'SUCCESS') {
      imageUrl.value = response.data.data
      emit('update:modelValue', response.data.data)
      toastStore.showSuccess(['圖片上傳成功！'])
    } else {
      toastStore.showError(response.data.message || ['上傳失敗'])
    }
  } catch (error) {
    toastStore.showError(['上傳失敗，請稍後再試'])
  } finally {
    uploading.value = false
  }
}

function removeImage() {
  imageUrl.value = ''
  emit('update:modelValue', '')
  if (fileInput.value) {
    fileInput.value.value = ''
  }
}
</script>

<style scoped>
.image-uploader {
  position: relative;
  border: 2px dashed var(--border-color);
  border-radius: var(--radius-lg);
  overflow: hidden;
  transition: var(--transition);
  min-height: 200px;
}

.image-uploader:hover {
  border-color: var(--primary-color);
}

.file-input {
  display: none;
}

.upload-placeholder {
  padding: 40px;
  text-align: center;
  cursor: pointer;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.upload-icon {
  font-size: 3rem;
  margin-bottom: 16px;
}

.upload-text {
  font-weight: 500;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.upload-hint {
  font-size: 0.85rem;
  color: var(--text-light);
}

.upload-loading {
  padding: 40px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--border-color);
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.image-preview {
  position: relative;
  width: 100%;
  height: 100%;
  min-height: 200px;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.6);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  transition: var(--transition);
}

.remove-btn:hover {
  background: rgba(239, 68, 68, 0.9);
}
</style>
