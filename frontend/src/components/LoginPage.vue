<template>
  <div class="container py-5 d-flex flex-column align-items-center" style="min-height: 100vh; background-color: #f8f9fa;">
    <h2 class="text-center mb-4 fw-bold" style="font-family: 'Playfair Display', serif; font-size: 2.5rem; color: black;">
    Login
</h2>

    <form @submit.prevent="login" class="border rounded p-4 shadow-sm bg-white" style="width: 100%; max-width: 400px;">
      <div class="mb-3">
        <label for="email" class="form-label fw-semibold">Email</label>
        <input
          type="email"
          id="email"
          v-model="email"
          required
          class="form-control"
          placeholder="Masukkan email"
          autocomplete="email"
        />
      </div>
      <div class="mb-4">
        <label for="password" class="form-label fw-semibold">Password</label>
        <input
          type="password"
          id="password"
          v-model="password"
          required
          class="form-control"
          placeholder="Masukkan password"
          autocomplete="current-password"
        />
      </div>
      <button type="submit" class="btn btn-danger w-100 fw-semibold">
        Login
      </button>
    </form>

    <p class="text-center mt-3" style="max-width: 400px;">
      Belum punya akun?
      <router-link to="/register" class="text-danger fw-semibold text-decoration-none">
        Daftar sekarang
      </router-link>
    </p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    login() {
      const storedUser = JSON.parse(localStorage.getItem('user'));

      if (storedUser && storedUser.email === this.email && storedUser.password === this.password) {
        alert('Login berhasil!');
        this.$router.push('/'); // Redirect ke homepage setelah login berhasil
      } else {
        alert('Email atau password salah.');
      }
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&display=swap');

body {
  margin: 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #f8f9fa;
}

h2 {
  font-family: 'Playfair Display', serif;
}

.form-label {
  font-weight: 600;
}
</style>