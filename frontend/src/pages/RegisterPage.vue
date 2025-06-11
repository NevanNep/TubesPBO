<template>
  <div class="container my-5" style="max-width: 420px;">
    <h2 class="text-center mb-4 fw-bold" style="font-family: Poppins; font-size: 2.5rem; color: black;">
    Daftar Akun Baru
</h2>
    <form @submit.prevent="register" class="shadow p-4 rounded bg-light">
      <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input
          type="email"
          id="email"
          v-model="email"
          required
          class="form-control"
          placeholder="Masukkan email Anda"
        />
      </div>
      <div class="mb-3">
        <label for="username" class="form-label">Username:</label>
        <input
          type="text"
          id="username"
          v-model="username"
          required
          class="form-control"
          placeholder="Masukkan username"
        />
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Password:</label>
        <input
          type="password"
          id="password"
          v-model="password"
          required
          class="form-control"
          placeholder="Masukkan password"
        />
      </div>

      <div class="mb-4">
        <label for="confirmPassword" class="form-label">Konfirmasi Password:</label>
        <input
          type="password"
          id="confirmPassword"
          v-model="confirmPassword"
          required
          class="form-control"
          placeholder="Konfirmasi password"
        />
      </div>

      <div class="mb-3">
       <label for="role">Login sebagai:</label>
        <select v-model="form.role" class="form-control" required>
          <option value="user">User</option>
          <option value="admin">Admin</option>
        </select>
      </div>

      <button type="submit" class="btn btn-danger w-100 fw-semibold">
        Register
      </button>
    </form>
    <p class="text-center mt-3">
      Sudah punya akun?
      <router-link to="/login" class="text-danger w-100 fw-semibold">
        Login
      </router-link>
    </p>
  </div>
</template>

<script>
export default {
data() {
  return {
    email: '',
    username: '',
    password: '',
    confirmPassword: '',
    form: {
    role: 'user'
    }
  };
  },
  methods: {
    register() {
      if (this.password === this.confirmPassword) {
        const user = {
          email: this.email,
          username: this.username,
          password: this.password,
          role: this.form.role
        };

        localStorage.setItem('user', JSON.stringify(user));
        alert('Pendaftaran berhasil! Silakan login.');
        this.$router.push('/login');
      } else {
        alert('Password dan konfirmasi password tidak cocok.');
      }
    },
  },
};
</script>

<style scoped>
/* Opsional: buat shadow lebih halus */
.shadow {
  box-shadow: 0 4px 12px rgb(0 0 0 / 0.1);
}
</style>
