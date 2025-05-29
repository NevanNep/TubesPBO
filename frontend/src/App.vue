<template>
  <div>
    <!-- Komponen Navbar Utama -->
    <MainNavbar />

    <!-- Background merah muda muda di bawah header-bar -->
    <div class="pink-background">
      <!-- Konten Halaman -->
      <router-view></router-view>
    </div>

    <!-- Footer -->
   
     <div>
     <!-- Konten utama -->
       <MainFooter />
    </div>


  </div>
</template>

<script>
import MainNavbar from "@/components/MainNavbar.vue";
import MainFooter from '@/components/MainFooter.vue';


export default {
  name: "App",
  components: {
    MainNavbar,
    MainFooter
  },
  data() {
    return {
      user: null,
    };
  },
  created() {
    this.user = JSON.parse(localStorage.getItem("loggedInUser"));
  },
  watch: {
    $route() {
      this.user = JSON.parse(localStorage.getItem("loggedInUser"));
    },
  },
  methods: {
    logout() {
      localStorage.removeItem("loggedInUser");
      alert("Berhasil logout.");
      this.user = null;
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');

.navbar-top {
  height: 25px;
  background-color: #dc3545;
}

.header-bar {
  background-color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 40px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.site-title {
  color: #dc3545;
  margin: 0;
  font-weight: 800;
  font-size: 2.8rem;
  user-select: none;
  font-family: 'Poppins', sans-serif;
}

.btn-login,
.btn-register {
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

.btn-login:hover,
.btn-register:hover {
  background-color: #a71d2a;
  color: white;
  border-color: #a71d2a;
}

.btn-login:active,
.btn-register:active {
  background-color: #a71d2a;
  color: white;
  border-color: #a71d2a;
  transition: none;
}

.pink-background {
  background-color: #fff;
  min-height: 100vh;
  padding: 30px 40px;
}

footer {
  font-family: 'Poppins', sans-serif;
  letter-spacing: 0.05em;
  background-color: #45000D;
  color: #fff;
  padding: 30px 0;
  user-select: none;
}

footer .container {
  max-width: 960px;
  margin: 0 auto;
}

footer p {
  font-weight: 600;
  font-size: 1rem;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  margin-bottom: 15px;
}

.footer-link {
  color: #ffe3e6;
  font-weight: 500;
  text-decoration: none;
  margin: 0 12px;
  transition: color 0.3s ease;
  position: relative;
  display: inline-block;
}

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

footer .mt-2 a i {
  margin-right: 6px;
  font-size: 1.1rem;
  vertical-align: middle;
}

footer .mt-2 a {
  font-weight: 600;
  font-size: 1.1rem;
}
</style>
