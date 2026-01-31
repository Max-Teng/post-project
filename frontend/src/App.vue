<template>
  <div id="app">
    <NavBar />
    <ToastNotification />
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue'
import ToastNotification from './components/ToastNotification.vue'
</script>

<style scoped>
.main-content {
  padding-top: 70px;
  min-height: calc(100vh - 70px);
}
</style>
