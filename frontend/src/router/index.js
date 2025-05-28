import { createRouter, createWebHistory } from 'vue-router';
import MenuPage from '../components/MenuPage.vue'; // Halaman utama menu produk
import ProductDetail from '../components/ProductDetail.vue'; // Halaman detail produk
import CartPage from '../components/CartPage.vue'; // Halaman keranjang
import PaymentPage from '../components/PaymentPage.vue'; // Halaman pembayaran
import LoginPage from '../components/LoginPage.vue'; // Halaman login
import RegisterPage from '../components/RegisterPage.vue'; // Halaman register
import ReviewPage from '@/components/ReviewPage.vue'; // Halaman review

const routes = [
  { path: '/', component: MenuPage },
  { path: '/product/:id', component: ProductDetail },
  { path: '/cart', component: CartPage },
  { path: '/payment', component: PaymentPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/reviews', component: ReviewPage } // Ganti ke /reviews
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;