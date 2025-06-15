<template>
  <div class="category-page">
    <h2 class="title">Kategori: {{ category }}</h2>

    <div v-if="products.length === 0" class="empty-message">
      <p>Tidak ada produk dalam kategori ini.</p>
    </div>

    <div class="product-grid">
      <ProductCard
        v-for="product in products"
        :key="product.productId"
        :product="product"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { fetchProductsByCategory } from '@/api/productApi'
import ProductCard from '@/components/ProductCard.vue'

const route = useRoute()
const category = ref(route.params.category)
const products = ref([])

const loadProducts = async () => {
  try {
    const response = await fetchProductsByCategory(category.value)
    products.value = response
  } catch (err) {
    console.error('Gagal mengambil produk kategori:', err)
  }
}


onMounted(loadProducts)

watch(() => route.params.category, (newVal) => {
  category.value = newVal
  loadProducts()
})
</script>

<style scoped>
.category-page {
  max-width: 1200px;
  margin: auto;
  padding: 2rem;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
  text-transform: capitalize;
}

.product-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
}

.empty-message {
  text-align: center;
  font-style: italic;
  color: #777;
}
</style>
