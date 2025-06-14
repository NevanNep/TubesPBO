<template>
  <div class="menu-page">
    <!-- Hero Banner -->
    <HeroBanner />

    <!-- Best Deals Section -->
    <section class="best-deals">
      <h2 class="section-title">Best Deals</h2>
      <div class="best-grid">
        <ProductCard
          v-for="item in bestDeals"
          :key="item.id"
          :product="item"
        />
      </div>
    </section>

    <!-- Best Seller Section -->
    <section class="best-seller">
      <div class="best-seller-header">
        <h2 class="section-title">Best Seller</h2>
        <div class="slider-buttons">
          <button @click="scrollLeft" aria-label="Scroll Left">
            &#10094;
          </button>
          <button @click="scrollRight" aria-label="Scroll Right">
            &#10095;
          </button>
        </div>
      </div>

      <div class="best-seller-wrapper">
        <div class="best-seller-grid" ref="bestSellerScroll">
          <ProductCard
            v-for="item in topSellers"
            :key="item.id"
            :product="item"
          />
        </div>
      </div>

      <button class="lihat-produk-btn" @click="lihatProduk">
        Lihat Produk
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
import axios from 'axios'
import HeroBanner from '@/components/HeroBanner.vue'
import ProductCard from '@/components/ProductCard.vue'
import ShopByCategories from '@/components/ShopbyCategories.vue'

const allProducts = ref([])
const bestDeals = ref([])
const topSellers = ref([])

onMounted(async () => {
  try {
    const { data } = await axios.get('http://localhost:8081/api/products')
    allProducts.value = data

    // Filter diskon besar
    bestDeals.value = data
      .filter((p) => p.discount >= 20)
      .sort((a, b) => b.discount - a.discount)
      .slice(0, 8)

    // Filter produk terjual terbanyak
    topSellers.value = data
      .filter((p) => p.sold) // pastikan ada field sold
      .sort((a, b) => b.sold - a.sold)
      .slice(0, 8)
  } catch (error) {
    console.error('Gagal mengambil produk:', error)
  }
})

const scrollLeft = () => {
  document.querySelector('.best-seller-grid').scrollBy({
    left: -300,
    behavior: 'smooth'
  })
}

const scrollRight = () => {
  document.querySelector('.best-seller-grid').scrollBy({
    left: 300,
    behavior: 'smooth'
  })
}

const lihatProduk = () => {
  window.location.href = '/products'
}
</script>

<style scoped>
.menu-page {
  font-family: 'Poppins', sans-serif;
  padding: 2rem;
  background: #fff;
}

.section-title {
  font-size: 2rem;
  font-weight: 700;
  color: #45000d;
  margin-bottom: 1rem;
}

.best-deals,
.shop-by-categories {
  margin: 3rem 0;
}

.best-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 1.5rem;
}

.best-seller {
  margin: 4rem 0;
}

.best-seller-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.slider-buttons button {
  background: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #45000d;
}

.best-seller-wrapper {
  overflow-x: auto;
  padding: 1rem 0;
}

.best-seller-grid {
  display: flex;
  gap: 1rem;
  overflow-x: auto;
  scroll-behavior: smooth;
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
