<template>
  <div class="profile-page">
    <div class="profile-card">
      <div class="avatar">
        <svg viewBox="0 0 24 24" fill="currentColor" class="avatar-icon">
          <circle cx="12" cy="8" r="4" />
          <path d="M4 20c0-4 4-7 8-7s8 3 8 7" />
        </svg>
      </div>
      <h2 class="username">Halo, {{ username }}!</h2>
      <p class="welcome-text">
        Selamat datang di halaman profil Anda sebagai <strong>{{ role }}</strong>.
      </p>

      <div class="action-buttons">
        <button class="edit-btn" @click="editProfile">Edit Profil</button>
        <button class="logout-btn" @click="logout">Logout</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfilePage',
  data() {
    return {
      username: 'User',
      role: 'BUYER'
    };
  },
  mounted() {
    const storedName = localStorage.getItem('username')
    const storedRole = localStorage.getItem('role')

    this.username = storedName || 'User'
    this.role = (storedRole || 'BUYER').toUpperCase()
  },
  methods: {
    logout() {
      localStorage.clear()
      this.$router.push('/login')
    },
    editProfile() {
      alert('Fitur edit profil belum tersedia.')
    }
  }
};
</script>

<style scoped>
.profile-page {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 20px;
  font-family: 'Poppins', sans-serif;
}

.profile-card {
  background-color: #fff;
  padding: 30px 40px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
  text-align: center;
  max-width: 400px;
  width: 100%;
}

.avatar {
  margin-bottom: 20px;
}

.avatar-icon {
  width: 80px;
  height: 80px;
  color: #45000D;
}

.username {
  font-size: 1.5rem;
  margin: 10px 0;
  font-weight: 600;
  color: #45000D;
}

.welcome-text {
  font-size: 0.95rem;
  color: #555;
  margin-bottom: 30px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.logout-btn,
.edit-btn {
  padding: 10px 20px;
  font-weight: 600;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
}

.logout-btn {
  background-color: #45000D;
  color: white;
}

.logout-btn:hover {
  background-color: #700014;
}

.edit-btn {
  background-color: #F1F0ED;
  color: #45000D;
  border: 1px solid #45000D;
}

.edit-btn:hover {
  background-color: #fff0dc;
}
</style>
