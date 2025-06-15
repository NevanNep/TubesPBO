<template>
  <header class="navbar">
    <div class="navbar-container">
      <!-- Logo Gambar -->
      <router-link to="/" class="logo">
        <img src="/newnew.png" alt="Scentify Logo" class="logo-img" />
      </router-link>

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
        <!-- Search -->
        <button aria-label="Search" class="icon-button">
          <MagnifyingGlassIcon class="icon" />
        </button>

        <!-- Profile -->
        <router-link v-if="isLoggedIn" to="/profile" aria-label="User" class="icon-button">
          <UserIcon class="icon" />
        </router-link>

        <!-- Cart -->
        <router-link v-if="isLoggedIn" to="/cart" aria-label="Cart" class="icon-button">
          <ShoppingCartIcon class="icon" />
        </router-link>

        <!-- Riwayat (Buyer Only) -->
        <router-link v-if="isBuyer" to="/history" aria-label="Riwayat" class="icon-button">
          <ClockIcon class="icon" />
        </router-link>

        <!-- Login/Register / Logout -->
        <template v-if="isLoggedIn">
          <button @click="logout" class="logout-btn">Logout</button>
        </template>
        <template v-else>
          <router-link to="/login" class="login-btn">Login / Register</router-link>
        </template>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, watchEffect } from 'vue'
import { useRouter } from 'vue-router'

// Import Heroicons
import { MagnifyingGlassIcon, UserIcon, ShoppingCartIcon, ClockIcon } from '@heroicons/vue/24/outline'

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
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 3rem;
}

/* Logo */
.logo {
  display: flex;
  align-items: center;
  margin-right: auto;
}
.logo-img {
  height: 25px;
  width: auto;
  object-fit: contain;
}

/* Menu Tengah */
.navbar-menu {
  display: flex;
  flex: 1;
  justify-content: center;
  gap: 2rem;
}

.navbar-menu a {
  color: #ffffff;
  font-weight: 600;
  font-size: 0.9rem;
  text-decoration: none;
  transition: color 0.3s ease;
}
.navbar-menu a:hover {
  color: #F7E7CE;
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
  margin-left: auto;
  padding-left: 23rem;
}
.icon-button {
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
}
.icon {
  width: 22px;
  height: 22px;
  stroke: #ffffff;
  transition: stroke 0.3s ease, transform 0.2s ease;
}
.icon-button:hover .icon {
  stroke: #F7E7CE;
  transform: scale(1.15);
}

/* Login / Register Button */
.login-btn {
  background-color: #F7E7CE;
  color: #45000D;
  padding: 6px 14px;
  font-weight: 600;
  border-radius: 8px;
  text-decoration: none;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}
.login-btn:hover {
  background-color: #ffffff;
  color: #700014;
}

/* Logout */
.logout-btn {
  background-color: transparent;
  color: #ffffff;
  border: 1px solid #ffffff;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
}
.logout-btn:hover {
  background-color: #F7E7CE;
  color: #45000D;
}
</style>
