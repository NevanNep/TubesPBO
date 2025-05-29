<template>
    <header class="navbar">
      <div class="navbar-top">
        <!-- Logo -->
        <div class="navbar-left">
          <router-link to="/">
            <img src="@/assets/newnew.png" alt="Scentify Logo" class="logo" />
          </router-link>
        </div>
  
        <!-- Menu Tengah -->
        <nav class="navbar-center">
          <router-link to="/" exact>SHOP BY CATEGORIES</router-link>
          <router-link to="#">SHOP BY BRANDS</router-link>
          <router-link to="#">TOP PICKS</router-link>
          <router-link to="#">SALE</router-link>
          <router-link to="#">BLOG</router-link>
          <router-link to="#">GIFT</router-link>
        </nav>
  
        <!-- Menu Kanan -->
        <div class="navbar-right">
          <!-- Icon Search -->
          <svg class="icon" viewBox="0 0 24 24" width="20" height="20">
            <circle cx="11" cy="11" r="7" stroke="currentColor" stroke-width="2" fill="none" />
            <line x1="16.5" y1="16.5" x2="21" y2="21" stroke="currentColor" stroke-width="2" />
          </svg>
  
          <!-- Login / Register -->
          <div v-if="!isLoggedIn">
            <router-link to="/login" class="login-btn">
              LOGIN <span class="divider">|</span> DAFTAR
            </router-link>
          </div>
  
          <!-- Ikon Profil Jika Login -->
          <div v-else>
            <router-link to="/profile">
              <svg class="icon" viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="8" r="4" />
                <path d="M4 20c0-4 4-7 8-7s8 3 8 7" />
              </svg>
            </router-link>
          </div>
  
          <!-- Icon Cart -->
          <router-link to="/cart">
            <svg class="icon" viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M3 3h2l.4 2M7 13h10l4-8H5.4" />
              <circle cx="7" cy="21" r="1" />
              <circle cx="20" cy="21" r="1" />
            </svg>
          </router-link>
        </div>
      </div>
    </header>
  </template>
  
  <script>
  export default {
    name: 'MainNavbar',
    data() {
      return {
        isLoggedIn: false,
      };
    },
    mounted() {
      this.checkLoginStatus();
      window.addEventListener('storage', this.checkLoginStatus); // update dari tab lain
    },
    methods: {
      checkLoginStatus() {
        this.isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
      },
    },
    beforeUnmount() {
      window.removeEventListener('storage', this.checkLoginStatus);
    }
  };
  </script>
  
  <style scoped>
  .navbar {
    background-color: #45000D;
    font-family: 'Poppins', sans-serif;
    border-bottom: 1px solid #eaeaea;
  }
  
  .navbar-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 14px 30px;
  }
  
  .navbar-left {
    flex-shrink: 0;
  }
  
  .logo {
    height: 21px;
  }
  
  .navbar-center {
    flex-grow: 1;
    display: flex;
    justify-content: center;
    gap: 30px;
  }
  
  .navbar-center a {
    color: #fff;
    text-decoration: none;
    font-size: 0.85rem;
    font-weight: 500;
    transition: all 0.2s ease;
    transform-origin: center;
  }
  
  .navbar-center a:hover {
    color: #F7E7CE; /* warna hover yang kalem */
    transform: scale(1.1);
  }
  
  .navbar-right {
    margin-left: auto;
    display: flex;
    align-items: center;
    gap: 12px;
  }
  
  .icon {
    color: #fff;
    cursor: pointer;
    transition: all 0.2s ease;
  }
  
  .icon:hover {
    color: #F7E7CE;
    transform: scale(1.1);
  }
  
  .login-btn {
    padding: 5px 8px;
    border: 1px solid #F7E7CE;
    color: #F7E7CE;
    background-color: transparent;
    font-size: 0.9rem;
    font-weight: 600;
    border-radius: 6px;
    letter-spacing: 0.5px;
    text-decoration: none;
    transition: all 0.2s ease;
  }
  
  .login-btn:hover {
    background-color: #F7E7CE;
    color: #45000D;
  }
  
  .divider {
    margin: 0 10px;
  }
  </style>
  