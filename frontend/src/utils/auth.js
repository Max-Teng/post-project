const TOKEN_KEY = 'jwt_token'

export function getToken() {
    return localStorage.getItem(TOKEN_KEY)
}

export function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token)
}

export function removeToken() {
    localStorage.removeItem(TOKEN_KEY)
}

export function isLoggedIn() {
    const token = getToken()
    if (!token) return false

    try {
        const payload = parseJwt(token)
        // Check if token is expired
        if (payload.exp && payload.exp * 1000 < Date.now()) {
            removeToken()
            return false
        }
        return true
    } catch (e) {
        return false
    }
}

export function getCurrentUser() {
    const token = getToken()
    if (!token) return null

    try {
        const payload = parseJwt(token)
        return {
            id: payload.id,
            userName: payload.userName || payload.sub,
            coverImage: payload.coverImage
        }
    } catch (e) {
        return null
    }
}

function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1]
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split('')
                .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                .join('')
        )
        return JSON.parse(jsonPayload)
    } catch (e) {
        return {}
    }
}
