import { createRouter, createWebHistory } from 'vue-router';

// Halaman Umum
import MenuPage from '../pages/MenuPage.vue';
import CartPage from '../pages/CartPage.vue';
import PaymentPage from '../pages/PaymentPage.vue';
import LoginPage from '../pages/LoginPage.vue';
import RegisterPage from '../pages/RegisterPage.vue';
import ReviewPage from '@/pages/ReviewPage.vue';
import ProfilePage from '@/pages/ProfilePage.vue';
import CategoryPage from '@/pages/CategoryPage.vue';
import ProductPage from '@/pages/productPage.vue'

// Halaman Admin
import AdminView from '@/pages/AdminView.vue';
import AdminDetail from '@/pages/AdminDetail.vue';
import AddProduct from '@/pages/AddProduct.vue';
import ProductDetail from '@/pages/ProductDetail.vue';

const routes = [
  // Halaman Umum
  { path: '/', component: MenuPage },
  { path: '/cart', component: CartPage },
  { path: '/payment', component: PaymentPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/reviews', component: ReviewPage },
  { path: '/profile', component: ProfilePage },
  {
    path: '/category/:type',
    name: 'CategoryPage',
    component: CategoryPage,
    props: true
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: ProductDetail
  },

  // Halaman Admin
  {
    path: '/admin',
    name: 'AdminView',
    component: AdminView,
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin/detail/:id',
    name: 'AdminDetail',
    component: AdminDetail,
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin/add',
    name: 'AddProduct',
    component: AddProduct,
    meta: { requiresAdmin: true }
  },
  {
    path: '/products',
    name: 'Products',
    component: ProductPage
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: ProductDetail,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

// Middleware: Cek akses admin
router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
  const role = localStorage.getItem('userRole');

  if (to.meta.requiresAdmin) {
    if (isLoggedIn && role?.toUpperCase() === 'ADMIN') {
      next();
    } else {
      alert('Akses hanya untuk admin!');
      next('/login');
    }
  } else {
    next();
  }
});

export default router;
