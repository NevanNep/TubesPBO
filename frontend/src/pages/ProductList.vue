<template>
  <div class="product-list-page">
    <h2 class="page-title">Daftar Produk</h2>

    <!-- Tombol tambah hanya untuk admin -->
    <div class="top-actions" v-if="isAdmin">
      <router-link to="/admin/add" class="btn tambah-btn">+ Tambah Produk</router-link>
    </div>

    <div v-if="products.length === 0" class="empty">
      <p>Belum ada produk.</p>
    </div>

    <div class="product-grid">
      <div v-for="product in products" :key="product.productId" class="product-card">
        <img :src="resolveImage(product.image)" :alt="product.name" />

        <h3>{{ product.name }}</h3>
        <p class="price">
          <template v-if="product.discount > 0">
            <span class="original">Rp {{ product.price.toLocaleString('id-ID') }}</span>
            <span class="discounted">Rp {{ calculateDiscount(product).toLocaleString('id-ID') }}</span>
          </template>
          <template v-else>
            Rp {{ product.price.toLocaleString('id-ID') }}
          </template>
        </p>

        <!-- ADMIN: Edit + Hapus -->
        <div v-if="isAdmin" class="actions">
          <router-link :to="`/admin/detail/${product.productId}`" class="btn">Edit</router-link>
          <button class="btn danger" @click="deleteProduct(product.productId)">Hapus</button>
        </div>

        <!-- BUYER: Tambah ke keranjang -->
        <div v-else>
          <button class="btn" @click="handleAddToCart(product)">Tambah ke Keranjang</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchAllProducts, deleteProductById } from '@/api/productApi'
import { addToCart as addToCartAPI } from '@/api/cartApi'

const products = ref([])
const role = localStorage.getItem('role')
const userId = Number(localStorage.getItem('userId'))
const isAdmin = role?.toUpperCase() === 'ADMIN'

const loadProducts = async () => {
  try {
    products.value = await fetchAllProducts()
  } catch (err) {
    console.error('Gagal mengambil produk:', err)
  }
}

const deleteProduct = async (id) => {
  if (!confirm('Yakin hapus produk ini?')) return
  try {
    await deleteProductById(id)
    products.value = products.value.filter(p => p.productId !== id)
  } catch (err) {
    console.error('Gagal hapus:', err)
  }
}

const handleAddToCart = async (product) => {
  if (!userId || isNaN(userId)) {
    alert('Silakan login sebagai buyer untuk menambahkan produk.')
    return
  }

  try {
    await addToCartAPI(userId, product.productId, 1)
    if (typeof window !== 'undefined' && window.$toast) {
      window.$toast.success(`${product.name} berhasil ditambahkan ke keranjang.`)
    } else {
      alert(`${product.name} berhasil ditambahkan ke keranjang.`)
    }
  } catch (err) {
    console.error('Gagal menambahkan ke keranjang:', err)
    if (typeof window !== 'undefined' && window.$toast) {
      window.$toast.error('Gagal menambahkan produk ke keranjang.')
    } else {
      alert('Gagal menambahkan produk ke keranjang.')
    }
  }
}

const resolveImage = (image) => {
  if (!image) return '/images/default.jpg'
  return image.startsWith('http') ? image : `/images/${image}`
}

const calculateDiscount = (product) => {
  return Math.round(product.price * (1 - (product.discount || 0) / 100))
}

onMounted(loadProducts)
</script>

<style scoped>
.product-list-page {
  max-width: 1100px;
  margin: auto;
  padding: 2rem;
  font-family: 'Poppins', sans-serif;
}

.page-title {
  font-size: 1.8rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: #45000D;
}

.top-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1.5rem;
}

.tambah-btn {
  background-color: #45000D;
  color: white;
  padding: 10px 16px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: 600;
}

.product-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
}

.product-card {
  width: 220px;
  background: white;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 1rem;
  text-align: center;
}

.product-card img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 6px;
  margin-bottom: 0.5rem;
}

.price {
  font-weight: bold;
  color: #45000D;
  margin-bottom: 0.5rem;
}

.price .original {
  text-decoration: line-through;
  font-size: 0.85rem;
  color: #888;
  margin-right: 6px;
}

.price .discounted {
  color: #b91c1c;
  font-weight: 700;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-top: 1rem;
}

.btn {
  padding: 6px;
  background: #45000D;
  color: white;
  border: none;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  text-decoration: none;
}

.btn:hover {
  background: #700014;
}

.btn.danger {
  background: crimson;
}
</style>
