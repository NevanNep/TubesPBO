<template>
  <div>
    <!-- Header -->
    <header class="bg-red-700 text-white py-3">
      <div class="container d-flex justify-content-between align-items-center">
        <h1 class="h3">SCENTIFY</h1>
        <!-- Dropdown Kategori Parfum -->
        <div class="dropdown">
  <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    Dropdown button
  </button>
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="#">Parfum Pria</a></li>
    <li><a class="dropdown-item" href="#">Parfum Wanita action</a></li>
    <li><a class="dropdown-item" href="#">Something else here</a></li>
  </ul>
</div>
        <div>
          <router-link to="/login" class="text-white mx-3">Login</router-link>
          <router-link to="/register" class="text-white mx-3">Register</router-link>
        </div>
      </div>
    </header>

    <!-- Konten Halaman -->
    <router-view></router-view>

    <!-- Footer -->
    <footer class="bg-red-700 text-white py-4 mt-5">
      <div class="container text-center">
        <p>&copy; 2025 SCENTIFY - All Rights Reserved</p>
        <div>
          <a href="#" class="text-white mx-3">Contact</a>
          <a href="#" class="text-white mx-3">About</a>
          <a href="#" class="text-white mx-3">Terms</a>
        </div>
        <div class="mt-2">
          <a href="#" class="text-white mx-3"><i class="bi bi-facebook"></i> Facebook</a>
          <a href="#" class="text-white mx-3"><i class="bi bi-whatsapp"></i> WhatsApp</a>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style scoped>
.bg-red-700 {
  background-color: #66bcf1;
}

.text-white {
  color: white;
}

.text-white:hover {
  color: #151010;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>
