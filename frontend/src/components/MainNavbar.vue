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
        <router-link to="/">SHOP BY CATEGORIES</router-link>
        <router-link to="#">SHOP BY BRANDS</router-link>
        <router-link to="#">TOP PICKS</router-link>
        <router-link to="#">SALE</router-link>
        <router-link to="#">BLOG</router-link>
        <router-link to="#">GIFT</router-link>
      </nav>

      <!-- Menu Kanan -->
      <div class="navbar-right">
        <!-- Search -->
        <button class="icon-btn" aria-label="Search">
          <svg class="icon" viewBox="0 0 24 24" width="22" height="22">
            <circle cx="11" cy="11" r="7" stroke="currentColor" stroke-width="2" fill="none" />
            <line x1="16.5" y1="16.5" x2="21" y2="21" stroke="currentColor" stroke-width="2" />
          </svg>
        </button>

        <!-- Jika belum login -->
        <div v-if="!isLoggedIn">
          <router-link to="/login" class="login-btn">
            LOGIN <span class="divider">|</span> DAFTAR
          </router-link>
        </div>

        <!-- Jika sudah login: dropdown -->
        <div v-else class="profile-dropdown" @click="toggleDropdown">
          <svg class="icon" viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="8" r="4" />
            <path d="M4 20c0-4 4-7 8-7s8 3 8 7" />
          </svg>
          <div v-if="dropdownOpen" class="dropdown-menu">
            <p class="dropdown-user">Halo, {{ username || 'User' }}</p>
            <router-link to="/profile">Profil</router-link>
            <button @click="logout">Logout</button>
          </div>
        </div>

        <!-- Cart -->
        <router-link to="/cart" class="cart-link" aria-label="Cart">
          <svg class="icon" viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2">
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
  name: "MainNavbar",
  data() {
    return {
      isLoggedIn: false,
      dropdownOpen: false,
      username: '',
    };
  },
  mounted() {
    this.checkLoginStatus();
    window.addEventListener("storage", this.checkLoginStatus); // sync login status antar tab
  },
  methods: {
    checkLoginStatus() {
      this.isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
      this.username = localStorage.getItem("username") || '';
    },
    toggleDropdown() {
      this.dropdownOpen = !this.dropdownOpen;
    },
    logout() {
      localStorage.removeItem("isLoggedIn");
      localStorage.removeItem("username");
      this.isLoggedIn = false;
      this.dropdownOpen = false;
      this.$router.push("/");
    }
  },
  beforeUnmount() {
    window.removeEventListener("storage", this.checkLoginStatus);
  }
};
</script>

<style scoped>
.navbar {
  background-color: #45000D;
  font-family: "Poppins", sans-serif;
  border-bottom: 1px solid #eaeaea;
}

.navbar-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 30px;
  gap: 20px;
}

/* Kiri */
.navbar-left {
  flex-shrink: 0;
}

.logo {
  height: 25px;
  cursor: pointer;
}

/* Tengah */
.navbar-center {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  gap: 32px;
}

.navbar-center a {
  color: #fff;
  text-decoration: none;
  font-size: 0.85rem;
  font-weight: 600;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.navbar-center a:hover {
  color: #F7E7CE;
  transform: scale(1.1);
}

/* Kanan */
.navbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
  min-width: 500px;
  justify-content: flex-end;
}

.icon-btn {
  background: transparent;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon {
  color: #fff;
  transition: all 0.2s ease;
  width: 25px;
  height: 25px;
}

.icon:hover {
  color: #F7E7CE;
  transform: scale(1.1);
}

.login-btn {
  padding: 6px 12px;
  border: 1px solid #F7E7CE;
  color: #F7E7CE;
  background-color: transparent;
  font-size: 0.9rem;
  font-weight: 600;
  border-radius: 6px;
  text-decoration: none;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.login-btn:hover {
  background-color: #F7E7CE;
  color: #45000D;
}

.divider {
  margin: 0 10px;
}

/* Cart & Profile icon link */
.profile-link,
.cart-link {
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.profile-link:hover .icon,
.cart-link:hover .icon {
  color: #F7E7CE;
  transform: scale(1.1);
}

/* Profile Dropdown */
.profile-dropdown {
  position: relative;
  cursor: pointer;
}

.dropdown-menu {
  position: absolute;
  top: 36px;
  right: 0;
  background: white;
  color: #45000D;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 12px;
  width: 160px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  z-index: 100;
  display: flex;
  flex-direction: column;
  gap: 10px;
  font-size: 0.9rem;
}

.dropdown-menu p {
  margin: 0;
  font-weight: 600;
}

.dropdown-menu button {
  background: none;
  border: none;
  color: #45000D;
  padding: 0;
  text-align: left;
  cursor: pointer;
}

.dropdown-menu a,
.dropdown-menu button {
  transition: color 0.2s;
}

.dropdown-menu a:hover,
.dropdown-menu button:hover {
  color: #700014;
}
</style>
