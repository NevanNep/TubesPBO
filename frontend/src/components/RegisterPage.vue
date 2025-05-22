<template>
  <div>
    <h2>Daftar Akun Baru</h2>
    <form @submit.prevent="register">
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div>
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div>
        <label for="confirmPassword">Konfirmasi Password:</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>
      <button type="submit">Daftar</button>
    </form>
    <router-link to="/login">Sudah punya akun? Login</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      username: '',
      password: '',
      confirmPassword: ''
    };
  },
  methods: {
    register() {
      if (this.password === this.confirmPassword) {
        const user = {
          email: this.email,
          username: this.username,
          password: this.password
        };

        localStorage.setItem('user', JSON.stringify(user)); // Menyimpan data pengguna di localStorage
        alert('Pendaftaran berhasil! Silakan login.');
        this.$router.push('/login'); // Redirect ke halaman login setelah registrasi berhasil
      } else {
        alert('Password dan konfirmasi password tidak cocok.');
      }
    }
  }
};
</script>

<style scoped>
input {
  margin: 10px 0;
  padding: 8px;
  width: 100%;
}

button {
  background-color: #28a745;
  color: white;
  padding: 10px;
  cursor: pointer;
}
</style>
