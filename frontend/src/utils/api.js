import axios from 'axios'
import { getToken } from './auth'

const api = axios.create({
    baseURL: import.meta.env.DEV ? '/api' : '',
    headers: {
        'Content-Type': 'application/json'
    }
})

// Request interceptor - add JWT token to all requests
api.interceptors.request.use(
    (config) => {
        const token = getToken()
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// Response interceptor - handle errors
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            // Token expired or invalid
            localStorage.removeItem('jwt_token')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)

export default api

// Auth APIs
export const authApi = {
    register: (data) => api.post('/auth/register', data),
    login: (data) => api.post('/auth/login', data)
}

// Post APIs
export const postApi = {
    getAllPosts: () => api.post('/post/getAllPost'),
    getPostDetail: (postId) => api.post('/post/getPostDetail', null, { params: { postId } }),
    createPost: (data) => api.post('/post/createPost', data),
    editPost: (data) => api.post('/post/editPost', data),
    deletePost: (postId) => api.post('/post/deletePost', null, { params: { postId } })
}

// Comment APIs
export const commentApi = {
    createComment: (data) => api.post('/comment/createComment', data)
}

// File APIs
export const fileApi = {
    upload: (file) => {
        const formData = new FormData()
        formData.append('file', file)
        return api.post('/file/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    }
}
