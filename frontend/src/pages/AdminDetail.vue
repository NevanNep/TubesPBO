<template>
  <div class="admin-detail">
    <h2>Detail Produk</h2>

    <div v-if="!product">
      <p>Memuat data produk...</p>
    </div>

    <div v-else>
      <img :src="product.image" :alt="product.name" />
      <h3>{{ product.name }}</h3>
      <p>Brand: {{ product.brand }}</p>
      <p>Kategori: {{ product.category }}</p>
      <p>Harga: Rp {{ product.price.toLocaleString('id-ID') }}</p>
      <p>Diskon: {{ product.discount }}%</p>
      <p>Stok: {{ product.stock }}</p>
      <p>Rating: {{ product.rating }} / 5</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { fetchProductById } from '@/api/productApi'

const route = useRoute()
const productId = route.params.id
const product = ref(null)

onMounted(async () => {
  try {
    product.value = await fetchProductById(productId)
  } catch (err) {
    console.error('Gagal mengambil data produk:', err)
  }
})
</script>

<style scoped>
.admin-detail {
  max-width: 600px;
  margin: auto;
  padding: 2rem;
}

.admin-detail img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 1rem;
}
</style>
