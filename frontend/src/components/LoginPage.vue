<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
    <router-link to="/register">Belum punya akun? Daftar sekarang</router-link>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    login() {
      const storedUser = JSON.parse(localStorage.getItem('user'));

      if (storedUser && storedUser.email === this.email && storedUser.password === this.password) {
        alert('Login berhasil!');
        this.$router.push('/'); // Redirect ke halaman utama setelah login berhasil
      } else {
        alert('Email atau password salah.');
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
  background-color: #007bff;
  color: white;
  padding: 10px;
  cursor: pointer;
}
</style>
