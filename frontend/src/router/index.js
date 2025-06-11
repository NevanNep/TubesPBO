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

// Halaman Admin
import AdminView from '@/pages/AdminView.vue';        // List & tambah produk
import AdminDetail from '@/pages/AdminDetail.vue';    // Edit produk
import AddProduct from '@/pages/AddProduct.vue';      // Tambah produk (kalau terpisah)

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
},
{
    path: '/product/:id',
    name: 'ProductDetail',
    component: () => import('@/pages/ProductDetail.vue')
  },


  // Halaman Admin (dengan meta: requiresAdmin)
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
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 🔐 Middleware Route Guard
router.beforeEach((to, from, next) => {
  const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
  const userRole = localStorage.getItem('userRole'); // Ambil role yang sedang login

  if (to.meta.requiresAdmin) {
    if (isLoggedIn && userRole === 'admin') {
      next(); // boleh lanjut ke /admin
    } else {
      alert('Akses hanya untuk admin!');
      next('/login');
    }
  } else {
    next(); // semua route lain bebas diakses
  }
});

export default router;
