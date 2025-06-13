<template>
  <div class="container py-5 d-flex flex-column align-items-center" style="min-height: 100vh; background-color: #f8f9fa;">
    <h2 class="text-center mb-4 fw-bold" style="font-family: Poppins, Poppins; font-size: 2.5rem; color: black;">
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
      password: ''
    };
  },
  mounted() {
    if (localStorage.getItem('isLoggedIn') === 'true') {
      this.$router.push('/');
    }
  },
  methods: {
    async login() {
  if (!this.email || !this.password) {
    alert('Mohon isi email dan password.');
    return;
  }

  const payload = {
    email: this.email,
    password: this.password
  };

  try {
    const response = await fetch('http://localhost:8081/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });

    const token = await response.text(); // karena backend kirim token string
    if (!response.ok) {
      throw new Error("Login gagal.");
    }

    localStorage.setItem('isLoggedIn', 'true');
    localStorage.setItem('token', token);

    // Decode role dari token (payload base64)
    const decodedPayload = JSON.parse(atob(token.split('.')[1]));
    const role = decodedPayload.role || 'BUYER';

    localStorage.setItem('userRole', role);

    alert('Login berhasil!');

    // Redirect berdasarkan role
    if (role === 'ADMIN') {
      this.$router.push('/admin');
    } else {
      this.$router.push('/');
    }

  } catch (error) {
    alert(`Error: ${error.message}`);
  }
}

  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&display=swap');

body {
  margin: 0;
  font-family: Poppins;
  background-color: #f8f9fa;
}

h2 {
  font-family: 'Playfair Display', serif;
}

.form-label {
  font-weight: 600;
}
</style>
