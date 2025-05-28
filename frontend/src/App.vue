<template>
  <div>
    <!-- Navbar Merah Atas -->
    <nav class="navbar-top bg-danger"></nav>

    <!-- Bar Putih di bawah Navbar Merah -->
    <div class="header-bar">
      <h1 class="site-title">SCENTIFY</h1>
      <div class="auth-buttons">
        <router-link to="/login" class="btn btn-login mx-2">Login</router-link>
        <router-link to="/register" class="btn btn-register mx-2">Register</router-link>
      </div>
    </div>

    <!-- Background merah muda muda di bawah header-bar -->
    <div class="pink-background">
      <!-- Konten Halaman -->
      <router-view></router-view>
    </div>

    <!-- Footer -->
    <footer>
      <div class="container text-center">
        <p>&copy; 2025 SCENTIFY - All Rights Reserved</p>
        <div>
          <a href="#" class="footer-link">Contact</a>
          <a href="#" class="footer-link">About</a>
          <a href="#" class="footer-link">Terms</a>
        </div>
        <div class="mt-2">
          <a href="#" class="footer-link"><i class="bi bi-facebook"></i> Facebook</a>
          <a href="#" class="footer-link"><i class="bi bi-whatsapp"></i> WhatsApp</a>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');

/* Navbar merah atas */
.navbar-top {
  height: 25px;
  background-color: #dc3545; /* merah */
}

/* Bar putih di bawah navbar */
.header-bar {
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 40px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

/* Tulisan SCENTIFY warna merah */
.site-title {
  color: #dc3545;
  margin: 0;
  font-weight: 800;
  font-size: 2.8rem;
  user-select: none;
  font-family: 'Poppins', sans-serif;
}

/* Tombol Login dan Register */
.btn-login, .btn-register {
  background-color: white;
  color: #dc3545;
  padding: 8px 20px;
  border-radius: 25px;
  text-decoration: none;
  font-weight: 600;
  border: 2px solid #dc3545;
  transition: background-color 0.3s ease, color 0.3s ease;
  user-select: none;
  cursor: pointer;
  display: inline-block;
  font-family: 'Poppins', sans-serif;
}

/* Hover tombol */
.btn-login:hover, .btn-register:hover {
  background-color: #a71d2a;
  color: white;
  border-color: #a71d2a;
}

/* Active tombol */
.btn-login:active, .btn-register:active {
  background-color: #a71d2a;
  color: white;
  border-color: #a71d2a;
  transition: none;
}

/* Background merah muda muda di bawah header-bar */
.pink-background {
  background-color: #fff0f2; /* very light pink */
  min-height: 100vh; /* biar cover seluruh area konten */
  padding: 30px 40px;
}

/* Footer styling */
footer {
  font-family: 'Poppins', sans-serif;
  letter-spacing: 0.05em;
  background-color: #dc3545;
  color: #fff;
  padding: 30px 0;
  user-select: none;
}

/* Container footer */
footer .container {
  max-width: 960px;
  margin: 0 auto;
}

/* Footer copyright text */
footer p {
  font-weight: 600;
  font-size: 1rem;
  text-shadow: 0 1px 3px rgba(0,0,0,0.3);
  margin-bottom: 15px;
}

/* Footer links */
.footer-link {
  color: #ffe3e6;
  font-weight: 500;
  text-decoration: none;
  margin: 0 12px;
  transition: color 0.3s ease;
  position: relative;
  display: inline-block;
}

/* Underline animation on hover */
.footer-link::after {
  content: '';
  position: absolute;
  width: 0;
  height: 2px;
  bottom: -3px;
  left: 0;
  background-color: #fff;
  transition: width 0.3s ease;
}

.footer-link:hover {
  color: #fff;
}

.footer-link:hover::after {
  width: 100%;
}

/* Icon spacing */
footer .mt-2 a i {
  margin-right: 6px;
  font-size: 1.1rem;
  vertical-align: middle;
}

/* Footer social media links bigger */
footer .mt-2 a {
  font-weight: 600;
  font-size: 1.1rem;
}
</style>