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
      <div v-for="product in products" :key="product.productId" class="product-box">
        <img :src="product.image" :alt="product.name" />
        <h3>{{ product.name }}</h3>
        <p>Rp {{ product.price.toLocaleString('id-ID') }}</p>
        <div class="actions">
          <router-link :to="`/admin/edit/${product.productId}`" class="btn">Edit</router-link>
          <button @click="deleteProduct(product.productId)">Hapus</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchAllProducts, deleteProductById } from '@/api/productApi'

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
}

.product-box {
  width: 220px;
  border: 1px solid #eee;
  padding: 1rem;
  border-radius: 8px;
  text-align: center;
  background: white;
}

.product-box img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  margin-bottom: 1rem;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-top: 1rem;
}

.btn {
  background: #700014;
  color: white;
  padding: 6px;
  border-radius: 6px;
  text-decoration: none;
  text-align: center;
}
</style>
