<template>
    <footer class="footer">
      <div class="footer-top">
        <div class="footer-brand">
          <img src="@/assets/LG.png" alt="Scentify Logo" class="logo" />
        </div>
  
        <div class="footer-links">
          <div class="footer-column">
            <h3>Tentang Kami</h3>
            <ul>
              <li><a href="#">Tentang Scentify</a></li>
              <li><a href="#">Karier</a></li>
              <li><a href="#">Blog</a></li>
              <li><a href="#">Mitra</a></li>
            </ul>
          </div>
  
          <div class="footer-column">
            <h3>Bantuan</h3>
            <ul>
              <li><a href="#">Pusat Bantuan</a></li>
              <li><a href="#">Cara Belanja</a></li>
              <li><a href="#">Pengembalian</a></li>
              <li><a href="#">Kontak Kami</a></li>
            </ul>
          </div>
  
          <div class="footer-column">
            <h3>Kategori</h3>
            <ul>
              <li><a href="#">Parfum Pria</a></li>
              <li><a href="#">Parfum Wanita</a></li>
              <li><a href="#">Unisex</a></li>
              <li><a href="#">Gift Set</a></li>
            </ul>
          </div>
        </div>
  
        <div class="footer-newsletter">
          <h3>Newsletter</h3>
          <p>Dapatkan info promo dan diskon terbaru</p>
          <form @submit.prevent="subscribe">
            <input type="email" v-model="email" placeholder="Email kamu" />
            <button type="submit">Langganan</button>
          </form>
          <div class="social-icons">
            <a href="#"><i class="fab fa-instagram"></i></a>
            <a href="#"><i class="fab fa-facebook-f"></i></a>
            <a href="#"><i class="fab fa-twitter"></i></a>
          </div>
        </div>
      </div>
  
      <div class="footer-bottom">
        <p>&copy; 2025 Scentify. Hak cipta dilindungi.</p>
        <div class="legal-links">
          <a href="#">Syarat & Ketentuan</a>
          <a href="#">Kebijakan Privasi</a>
        </div>
      </div>
    </footer>
  </template>
  
  <script>
  export default {
    name: 'MainFooter',
    data() {
      return {
        email: ''
      }
    },
    methods: {
      subscribe() {
        alert(`Terima kasih telah berlangganan, ${this.email}!`)
        this.email = ''
      }
    }
  }
  </script>
  
  <style scoped>
  .footer {
    background-color: #fff;
    color: #45000D;
    padding: 3rem 2rem 1.5rem;
    font-family: 'Poppins', sans-serif;
  }
  
  .footer-top {
    display: flex;
    flex-wrap: wrap;
    gap: 2rem;
    justify-content: space-between;
  }
  
  .footer-brand {
    max-width: 200px;
  }
  .logo {
    width: 160px;
    height: auto;
  }
  
  .footer-links {
    display: flex;
    gap: 2rem;
    flex-wrap: wrap;
  }
  
  .footer-column h3 {
    margin-bottom: 0.5rem;
    font-size: 1rem;
  }
  .footer-column ul {
    list-style: none;
    padding: 0;
  }
  .footer-column li {
    margin-bottom: 0.4rem;
  }
  .footer-column a {
    color: #45000D;
    text-decoration: none;
    font-size: 0.9rem;
  }
  .footer-column a:hover {
    text-decoration: underline;
  }
  
  .footer-newsletter {
    max-width: 350px;
  }
  .footer-newsletter h3 {
    margin-bottom: 0.5rem;
  }
  .footer-newsletter form {
    display: flex;
    gap: 0.5rem;
    margin-top: 0.5rem;
  }
  .footer-newsletter input {
    padding: 0.4rem 0.6rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    width: 150px;
  }
  .footer-newsletter button {
    background: #45000D;
    color: white;
    border: none;
    padding: 0.4rem 0.8rem;
    border-radius: 4px;
    cursor: pointer;
  }
  .footer-newsletter button:hover {
    background-color: #30000a;
  }
  
  .social-icons {
    margin-top: 1rem;
  }
  .social-icons a {
    margin-right: 0.5rem;
    font-size: 1.2rem;
    color: #45000D;
  }
  
  .footer-bottom {
    border-top: 1px solid #ccc;
    margin-top: 2rem;
    padding-top: 1rem;
    display: flex;
    justify-content: space-between;
    font-size: 0.85rem;
    flex-wrap: wrap;
  }
  .legal-links a {
    margin-left: 1rem;
    color: #45000D;
    text-decoration: none;
  }
  .legal-links a:hover {
    text-decoration: underline;
  }
  </style>
  