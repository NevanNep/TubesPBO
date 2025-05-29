<template>
    <div class="profile-page">
      <h1>Profil Saya</h1>
      <p>Selamat datang, user!</p>
      <button @click="logout" class="logout-btn">Logout</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ProfilePage',
    methods: {
      logout() {
        localStorage.removeItem('isLoggedIn');
        // Bisa juga hapus data user lain kalau ada
        this.$router.push('/'); // Redirect ke homepage setelah logout
      },
    },
  };
  </script>
  
  <style scoped>
  .profile-page {
    max-width: 600px;
    margin: 40px auto;
    padding: 20px;
    font-family: 'Poppins', sans-serif;
  }
  
  .logout-btn {
    padding: 10px 20px;
    background-color: #45000D;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 600;
    margin-top: 20px;
  }
  
  .logout-btn:hover {
    background-color: #700014;
  }
  </style>
  