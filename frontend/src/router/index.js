import { createRouter, createWebHistory } from 'vue-router';
import MenuPage from '../pages/MenuPage.vue'; // Halaman utama menu produk
import CartPage from '../pages/CartPage.vue'; // Halaman keranjang
import PaymentPage from '../pages/PaymentPage.vue'; // Halaman pembayaran
import LoginPage from '../pages/LoginPage.vue'; // Halaman login
import RegisterPage from '../pages/RegisterPage.vue'; // Halaman register
import ReviewPage from '@/pages/ReviewPage.vue'; // Halaman review
import ProfilePage from '@/pages/ProfilePage.vue';
import CategoryPage from '@/pages/CategoryPage.vue';
import AdminView from '@/pages/AdminDetail.vue';
import AdminDetail from '@/pages/AdminView.vue';
import AddProduct from '@/pages/AddProduct.vue';





const routes = [
  { path: '/', component: MenuPage },
  { path: '/cart', component: CartPage },
  { path: '/payment', component: PaymentPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/reviews', component: ReviewPage },
  { path: '/profile', component: ProfilePage},
  { path: '/admin', component: AdminView},
  { path: '/admindetail', component: AdminDetail},
  { path: '/addproduct', component: AddProduct},


  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: () => import('@/pages/ProductDetail.vue')
  },
  {
    path: '/category/:type',
    name: 'CategoryPage',
    component: CategoryPage,
    props: true
  },
 {
  path: '/admin',
  component: () => import('@/pages/AdminView.vue'),
  meta: { requiresAdmin: true }
},
{
  path: '/admin-detail/:id',
  name: 'AdminDetail',
  component: () => import('../pages/AdminDetail.vue'),
  meta: { requiresAuth: true, requiresAdmin: true }
},
{
  path: '/admin/add',
  component: () => import('@/pages/AddProduct.vue'),
  meta: { requiresAdmin: true }
}

];



const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

