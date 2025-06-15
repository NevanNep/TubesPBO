<template>
  <header class="navbar">
    <div class="navbar-container">
      <!-- Logo -->
      <router-link to="/" class="logo">SCENTIFY</router-link>

      <!-- Menu Tengah -->
      <nav class="navbar-center">
        <a href="#shop-by-categories">SHOP BY CATEGORIES</a>
        <a href="#best-seller">BEST SELLER</a>
      <nav class="navbar-menu">
        <router-link to="/category/Men">SHOP BY CATEGORIES</router-link>
        <router-link to="#">BEST SELLER</router-link>
        <router-link to="#">BLOG</router-link>
        <router-link to="#">GIFT</router-link>
        <router-link to="/products">PRODUCT</router-link>

        <!-- Menu Admin (khusus admin) -->
        <div v-if="isAdmin" class="admin-dropdown" @mouseenter="showAdmin = true" @mouseleave="showAdmin = false">
          <span class="admin-label">ADMIN ▾</span>
          <div v-if="showAdmin" class="admin-menu">
            <router-link to="/admin">Kelola Produk</router-link>
            <router-link to="/admin/add">Tambah Produk</router-link>
            <router-link to="/admin/users">Kelola User</router-link>
            <router-link to="/admin/orders">Kelola Pesanan</router-link>
          </div>
        </div>
      </nav>

      <!-- Ikon Kanan -->
      <div class="navbar-icons">
        <button aria-label="Search"><i class="fas fa-search"></i></button>

        <router-link v-if="isLoggedIn" to="/profile" aria-label="User">
          <i class="fas fa-user"></i>
        </router-link>

        <router-link v-if="isLoggedIn" to="/cart" aria-label="Cart">
          <i class="fas fa-shopping-cart"></i>
        </router-link>

        <!-- ✅ Tambahan: Riwayat hanya untuk buyer -->
        <router-link v-if="isBuyer" to="/history" aria-label="Riwayat">
          <i class="fas fa-clock"></i>
        </router-link>

        <button v-if="isLoggedIn" @click="logout" class="logout-btn">Logout</button>
        <router-link v-else to="/login" class="login-btn">Login</router-link>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, watchEffect } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const isLoggedIn = ref(false)
const isAdmin = ref(false)
const isBuyer = ref(false)
const showAdmin = ref(false)

const checkLogin = () => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role') || ''
  isLoggedIn.value = !!token
  isAdmin.value = role.toUpperCase() === 'ADMIN'
  isBuyer.value = role.toUpperCase() === 'BUYER'
}

// Supaya auto update saat localStorage berubah
watchEffect(() => {
  checkLogin()
})

const logout = () => {
  localStorage.clear()
  isLoggedIn.value = false
  isAdmin.value = false
  isBuyer.value = false
  router.push('/login')
}

onMounted(() => {
  checkLogin()
  window.addEventListener('storage', checkLogin)
})
</script>

<style scoped>
.navbar {
  background-color: #45000D;
  font-family: "Poppins", sans-serif;
  color: white;
  padding: 0.9rem 0;
  border-bottom: 1px solid #300009;
}

.navbar-container {
  max-width: 1440px;
  margin: auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 3rem;
}

/* Logo */
.logo {
  font-size: 1.7rem;
  font-weight: 800;
  color: #F7E7CE;
  text-decoration: none;
  letter-spacing: 1px;
}

/* Menu Tengah */
.navbar-menu {
  display: flex;
  flex: 1;
  justify-content: center;
  gap: 2rem;
  position: relative;
}

.navbar-menu a {
  color: #F7E7CE;
  font-weight: 600;
  font-size: 0.95rem;
  text-decoration: none;
  transition: color 0.3s ease;
}

.navbar-menu a:hover {
  color: #ffffff;
}

/* Dropdown Admin */
.admin-dropdown {
  position: relative;
  color: #F7E7CE;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
}

.admin-label:hover {
  color: #ffffff;
}

.admin-menu {
  position: absolute;
  top: 30px;
  background: white;
  border: 1px solid #ccc;
  color: #45000D;
  padding: 0.5rem 1rem;
  display: flex;
  flex-direction: column;
  gap: 6px;
  border-radius: 6px;
  z-index: 99;
}

.admin-menu a {
  text-decoration: none;
  color: #45000D;
  font-weight: 500;
}

.admin-menu a:hover {
  color: #700014;
}

/* Ikon Kanan */
.navbar-icons {
  display: flex;
  align-items: center;
  gap: 1.2rem;
}

.navbar-icons i {
  font-size: 1.1rem;
  color: #F7E7CE;
  transition: transform 0.2s ease, color 0.2s ease;
}

.navbar-icons i:hover {
  color: #ffffff;
  transform: scale(1.2);
}

.navbar-icons button {
  background: none;
  border: none;
  cursor: pointer;
  font-weight: 600;
  color: #F7E7CE;
  transition: color 0.2s ease;
}

.navbar-icons button:hover {
  color: #ffffff;
}
</style>
