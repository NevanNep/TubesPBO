<template>
  <div class="category-page">
    <nav class="breadcrumb">Home > Category    <span class="active">{{ category }}</span></nav>
    <h2 class="title">FOR {{ category }}</h2>

    <div class="main-content">
      <!-- Sidebar -->
      <aside class="sidebar">
        <FilterSidebar @filter-changed="handleFilterChange" />
      </aside>

      <!-- Produk -->
      <section class="product-area">
        <div class="toolbar">
          <div class="sort-group">
            <select v-model="sortBy" @change="loadProducts">
              <option value="price-asc">Price: Lowest - Highest</option>
              <option value="az">A-Z</option>
              <!-- Tambahkan opsi lain -->
            </select>
          </div>
          <div class="limit-group">
            <label>Tampilkan</label>
            <select v-model="limit" @change="loadProducts">
              <option :value="12">12 Item</option>
              <option :value="24">24 Item</option>
              <option :value="36">36 Item</option>
            </select>
          </div>
        </div>

        <div v-if="products.length === 0" class="empty-message">
          <p>Tidak ada produk dalam kategori ini.</p>
        </div>

        <div v-else class="product-grid">
          <ProductCard
            v-for="product in products"
            :key="product.productId"
            :product="product"
          />
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { fetchProductsByCategory } from '@/api/productApi'
import ProductCard from '@/components/ProductCard.vue'
import FilterSidebar from '@/components/FilterSidebar.vue'

const route = useRoute()
const category = ref(route.params.category)
const products = ref([])
const filters = ref({})
const sortBy = ref('price-asc')
const limit = ref(12)

const loadProducts = async () => {
  try {
    const response = await fetchProductsByCategory(category.value, {
      filters: filters.value,
      sortBy: sortBy.value,
      limit: limit.value,
    })
    products.value = response
  } catch (err) {
    console.error('Gagal mengambil produk kategori:', err)
  }
}

const handleFilterChange = (newFilters) => {
  filters.value = newFilters
  loadProducts()
}

onMounted(loadProducts)

watch(() => route.params.category, (newVal) => {
  category.value = newVal
  loadProducts()
})
</script>

<style scoped>
.category-page {
  max-width: 1400px;
  margin: auto;
  padding: 2rem;
  font-family: 'Poppins', sans-serif;
  color: #333;
}

.breadcrumb {
  font-size: 0.9rem;
  margin-bottom: 1rem;
  color: #999;
}

.breadcrumb .active {
  color: #d60000;
  font-weight: bold;
}

.title {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 2rem;
  text-transform: uppercase;
}

.main-content {
  display: flex;
  gap: 2rem;
}

/* Sidebar */
.sidebar {
  width: 270px;
  flex-shrink: 0;
}

/* Product area */
.product-area {
  flex: 1;
}

/* Toolbar */
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.sort-group select,
.limit-group select {
  padding: 0.4rem 0.6rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  background: white;
  font-size: 0.9rem;
}

/* Product Grid */
.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
  gap: 2rem 1.5rem;
}

.empty-message {
  text-align: center;
  color: #777;
  font-style: italic;
  margin-top: 2rem;
}
</style>
