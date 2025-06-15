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

    <!-- Product Cards -->
    <div class="product-grid">
      <ProductCard
        v-for="product in products"
        :key="product.productId"
        :product="product"
        :quantity="1"
        :is-admin="isAdmin"
        @delete-product="deleteProduct(product.productId)"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchAllProducts, deleteProductById } from '@/api/productApi'
import ProductCard from '@/components/ProductCard.vue' // sesuaikan path jika perlu

const products = ref([])
const role = localStorage.getItem('role')
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
  justify-content: center;
}
</style>
