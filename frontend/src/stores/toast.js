import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useToastStore = defineStore('toast', () => {
    const toasts = ref([])
    let nextId = 0

    function showToast(status, messages) {
        const id = nextId++
        const toast = {
            id,
            status,
            messages: Array.isArray(messages) ? messages : [messages]
        }
        toasts.value.push(toast)

        // Auto remove after 5 seconds
        setTimeout(() => {
            removeToast(id)
        }, 5000)
    }

    function removeToast(id) {
        const index = toasts.value.findIndex(t => t.id === id)
        if (index > -1) {
            toasts.value.splice(index, 1)
        }
    }

    function showSuccess(messages) {
        showToast('SUCCESS', messages)
    }

    function showError(messages) {
        showToast('ERROR', messages)
    }

    // Handle API response
    function handleApiResponse(response) {
        if (response.data) {
            if (response.data.status === 'SUCCESS' && response.data.message) {
                showSuccess(response.data.message)
            } else if (response.data.status === 'ERROR' && response.data.message) {
                showError(response.data.message)
            }
        }
    }

    return {
        toasts,
        showToast,
        removeToast,
        showSuccess,
        showError,
        handleApiResponse
    }
})
