<template>
  <div class="menu-page">
    <!-- Hero Banner -->
    <HeroBanner />

    <!-- Best Deals Section -->
    <section class="best-deals">
      <div class="section-header">
        <h2 class="section-title">Best Deals</h2>
        <div class="slider-buttons">
          <button @click="scrollLeftDeals" aria-label="Scroll Left">&#10094;</button>
          <button @click="scrollRightDeals" aria-label="Scroll Right">&#10095;</button>
        </div>
      </div>

      <div class="scroll-wrapper" ref="bestDealsScroll">
        <div class="scroll-row">
          <ProductCard
            v-for="item in bestDeals"
            :key="item.productId"
            :product="item"
            class="scroll-item"
          />
        </div>
      </div>
    </section>

    <!-- Best Seller Section -->
    <section class="best-seller">
      <div class="section-header">
        <h2 class="section-title">Best Seller</h2>
        <div class="slider-buttons">
          <button @click="scrollLeft" aria-label="Scroll Left">&#10094;</button>
          <button @click="scrollRight" aria-label="Scroll Right">&#10095;</button>
        </div>
      </div>

      <div class="scroll-wrapper" ref="bestSellerScroll">
        <div class="scroll-row">
          <ProductCard
            v-for="item in topSellers"
            :key="item.productId"
            :product="item"
            class="scroll-item"
          />
        </div>
      </div>

      <button class="lihat-produk-btn" @click="lihatProduk">
        Lihat Semua Produk
      </button>
    </section>

    <!-- Shop by Categories -->
    <section class="shop-by-categories">
      <h2 class="section-title">Shop by Categories</h2>
      <ShopByCategories />
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '@/api/axiosInstance'
import HeroBanner from '@/components/HeroBanner.vue'
import ProductCard from '@/components/ProductCard.vue'
import ShopByCategories from '@/components/ShopbyCategories.vue'

const bestDeals = ref([])
const topSellers = ref([])

const bestDealsScroll = ref(null)
const bestSellerScroll = ref(null)

onMounted(async () => {
  try {
    const { data: all } = await axios.get('/products')
    bestDeals.value = all
      .filter(p => p.discount >= 20)
      .sort((a, b) => b.discount - a.discount)
      .slice(0, 10)

    const { data: bestSellerData } = await axios.get('/products/bestsellers')
    topSellers.value = bestSellerData
  } catch (err) {
    console.error('Gagal mengambil produk:', err)
  }
})

const scrollLeft = () => {
  bestSellerScroll.value?.scrollBy({ left: -300, behavior: 'smooth' })
}
const scrollRight = () => {
  bestSellerScroll.value?.scrollBy({ left: 300, behavior: 'smooth' })
}

const scrollLeftDeals = () => {
  bestDealsScroll.value?.scrollBy({ left: -300, behavior: 'smooth' })
}
const scrollRightDeals = () => {
  bestDealsScroll.value?.scrollBy({ left: 300, behavior: 'smooth' })
}

const lihatProduk = () => {
  window.location.href = '/products'
}
</script>

<style scoped>
.menu-page {
  font-family: 'Poppins', sans-serif;
  padding: 2rem;
  background: #FFF;
}

.section-title {
  font-size: 2rem;
  font-weight: 700;
  color: #45000d;
  margin-bottom: 1rem;
  margin-top: 3rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.scroll-wrapper {
  overflow-x: auto;
  white-space: nowrap;
  padding: 1rem 0;
}

.scroll-row {
  display: inline-flex;
  gap: 1rem;
}

.scroll-item {
  flex: 0 0 auto;
  width: 220px;
}

.slider-buttons button {
  background: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #45000d;
}

.lihat-produk-btn {
  display: block;
  margin: 2rem auto 0;
  padding: 0.75rem 2rem;
  background: #45000d;
  color: #fff;
  font-weight: 600;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s;
}

.lihat-produk-btn:hover {
  background: #700014;
}
</style>
