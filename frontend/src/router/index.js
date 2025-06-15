import { createRouter, createWebHistory } from 'vue-router'

// Halaman Umum
import MenuPage from '../pages/MenuPage.vue'
import CartPage from '../pages/CartPage.vue'
import PaymentPage from '../pages/PaymentPage.vue'
import LoginPage from '../pages/LoginPage.vue'
import RegisterPage from '@/pages/RegisterPage.vue'
import ProfilePage from '@/pages/ProfilePage.vue'
import CategoryPage from '@/pages/CategoryPage.vue'
import ProductDetail from '@/pages/ProductDetail.vue'
import ProductList from '@/pages/ProductList.vue'
import OrderHistoryPage from '@/pages/OrderHistoryPage.vue' // ✅ Tambahkan ini

// Halaman Admin
import AdminView from '@/pages/AdminView.vue'
import AdminDetail from '@/pages/AdminDetail.vue'
import AddProduct from '@/pages/AddProduct.vue'
import EditProduct from '@/pages/EditProduct.vue'

const routes = [
  // Halaman Umum
  { path: '/', component: MenuPage },
  { path: '/cart', component: CartPage },
  { path: '/payment', component: PaymentPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/profile', component: ProfilePage },
  {
    path: '/category/:category',
    name: 'CategoryPage',
    component: CategoryPage,
    props: true
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: ProductDetail
  },
  {
    path: '/products',
    name: 'ProductList',
    component: ProductList
  },
  {
    path: '/history', // ✅ Route untuk riwayat transaksi buyer
    name: 'OrderHistory',
    component: OrderHistoryPage
  },

  // Halaman Admin
  {
    path: '/admin',
    name: 'AdminView',
    component: AdminView,
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin/edit/:id',
    name: 'EditProduct',
    component: EditProduct,
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
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// ✅ Middleware Cek Role Admin
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  if (to.meta.requiresAdmin) {
    if (token && role?.toUpperCase() === 'ADMIN') {
      next()
    } else {
      alert('Akses hanya untuk admin!')
      next('/login')
    }
  } else {
    next()
  }
})

export default router
