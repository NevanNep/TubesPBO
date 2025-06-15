<template>
  <div class="register-page">
    <div class="register-box">
      <h2>Daftar Akun</h2>
      <form @submit.prevent="handleRegister">
        <input v-model="nama" type="text" placeholder="Nama Lengkap" required />
        <input v-model="email" type="email" placeholder="Email" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <input v-model="alamat" type="text" placeholder="Alamat" required />
        <button type="submit">Daftar</button>
      </form>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <router-link to="/login">Sudah punya akun? Login</router-link>
    </div>
  </div>
</template>

<script>
import axios from '@/api/axiosInstance'

export default {
  name: 'RegisterPage',
  data() {
    return {
      nama: '',
      email: '',
      password: '',
      alamat: '',
      errorMessage: ''
    }
  },
  methods: {
    async handleRegister() {
      try {
        await axios.post('/auth/register', {
          nama: this.nama,
          email: this.email,
          password: this.password,
          alamat: this.alamat,
          role: 'BUYER' // default role
        })

        // Jika sukses, redirect ke login
        this.$router.push('/login')
      } catch (err) {
        console.error('Register error:', err)
        this.errorMessage = 'Pendaftaran gagal. Email mungkin sudah digunakan.'
      }
    }
  }
}
</script>

<style scoped>
.register-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90vh;
  background-color: #f9f9f9;
}

.register-box {
  width: 100%;
  max-width: 400px;
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0,0,0,0.1);
  text-align: center;
}

.register-box h2 {
  margin-bottom: 1.5rem;
  color: #45000D;
  font-family: 'Poppins', sans-serif;
}

.register-box input {
  width: 100%;
  padding: 10px;
  margin-bottom: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 0.95rem;
}

.register-box button {
  width: 100%;
  background-color: #45000D;
  color: white;
  padding: 10px;
  border: none;
  font-weight: bold;
  border-radius: 6px;
  transition: background 0.3s ease;
}

.register-box button:hover {
  background-color: #700014;
}

.error {
  color: red;
  margin-top: 1rem;
  font-size: 0.9rem;
}
</style>
