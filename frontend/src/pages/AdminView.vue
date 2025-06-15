<template>
  <div class="admin-view">
    <h2>Manajemen Produk</h2>

    <div class="top-actions">
      <router-link to="/admin/add" class="btn tambah-btn">+ Tambah Produk</router-link>
    </div>

    <div v-if="products.length === 0">
      <p>Belum ada produk tersedia.</p>
    </div>

    <div class="product-grid">
      <ProductCard
        v-for="product in products"
        :key="product.productId"
        :product="product"
        :isAdmin="true"
        @delete-product="deleteProduct"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchAllProducts, deleteProductById } from '@/api/productApi'
import ProductCard from '@/components/ProductCard.vue' // pastikan path-nya sesuai

const products = ref([])

const loadProducts = async () => {
  try {
    products.value = await fetchAllProducts()
  } catch (err) {
    console.error('Gagal load produk:', err)
  }
}

const deleteProduct = async (productId) => {
  if (!confirm('Yakin ingin menghapus produk ini?')) return

  try {
    await deleteProductById(productId)
    products.value = products.value.filter(p => p.productId !== productId)
  } catch (err) {
    console.error('Gagal hapus produk:', err)
  }
}

onMounted(loadProducts)
</script>

<style scoped>
.admin-view {
  padding: 2rem;
  max-width: 1100px;
  margin: auto;
}

.top-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1.5rem;
}

.tambah-btn {
  background: #45000D;
  color: white;
  padding: 10px 16px;
  border-radius: 6px;
  font-weight: bold;
  text-decoration: none;
}

.product-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  justify-content: flex-start;
}
</style>
