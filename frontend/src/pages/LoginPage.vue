<template>
  <div class="login-page">
    <div class="login-box">
      <h2>Login</h2>
      <form @submit.prevent="handleLogin">
        <input v-model="email" type="email" placeholder="Email" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <button type="submit">Login</button>
      </form>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <router-link to="/register">Belum punya akun? Daftar</router-link>
    </div>
  </div>
</template>

<script>
import axios from '@/api/axiosInstance' // gunakan instance agar token otomatis di-manage

export default {
  name: 'LoginPage',
  data() {
    return {
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        const res = await axios.post('/auth/login', {
          email: this.email,
          password: this.password
        });

        // Simpan token dan data user
        const token = res.data.token;
        const role = res.data.role || 'BUYER'; // fallback jika tidak tersedia
        const userId = res.data.userId || res.data.id;
        const username = res.data.username || res.data.nama || res.data.name || 'User';

        localStorage.setItem('token', token);
        localStorage.setItem('role', role.toUpperCase());
        localStorage.setItem('userId', userId);
        localStorage.setItem('username', username);

        // Arahkan ke halaman sesuai role
        if (role.toUpperCase() === 'ADMIN') {
          this.$router.push('/admin');
        } else {
          this.$router.push('/');
        }

      } catch (err) {
        console.error('Login error:', err);
        this.errorMessage = 'Login gagal. Cek email dan password Anda.';
      }
    }
  }
};
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90vh;
  background-color: #f9f9f9;
}

.login-box {
  width: 100%;
  max-width: 400px;
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0,0,0,0.1);
  text-align: center;
}

.login-box h2 {
  margin-bottom: 1.5rem;
  color: #45000D;
  font-family: 'Poppins', sans-serif;
}

.login-box input {
  width: 100%;
  padding: 10px;
  margin-bottom: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 0.95rem;
}

.login-box button {
  width: 100%;
  background-color: #45000D;
  color: white;
  padding: 10px;
  border: none;
  font-weight: bold;
  border-radius: 6px;
  transition: background 0.3s ease;
}

.login-box button:hover {
  background-color: #700014;
}

.error {
  color: red;
  margin-top: 1rem;
  font-size: 0.9rem;
}
</style>
