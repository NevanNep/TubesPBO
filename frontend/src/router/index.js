import { createRouter, createWebHistory } from 'vue-router';
import MenuPage from '../components/MenuPage.vue'; // Halaman utama menu produk
import ProductDetail from '../components/ProductDetail.vue'; // Halaman detail produk
import CartPage from '../components/CartPage.vue'; // Halaman keranjang
import PaymentPage from '../components/Paymentpage.vue'; // Halaman pembayaran
import LoginPage from '../components/LoginPage.vue'; // Halaman login
import RegisterPage from '../components/RegisterPage.vue'; // Halaman register
import ReviewPage from '@/components/ReviewPage.vue';

const routes = [
  { path: '/', component: MenuPage },
  { path: '/product/:id', component: ProductDetail },
  { path: '/cart', component: CartPage },
  { path: '/payment', component: PaymentPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/review', component: ReviewPage }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
