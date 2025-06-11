<template>
  <div class="menu-page">
    <!-- Hero Banner -->
    <HeroBanner />

    <!-- Best Seller Section -->
    <section class="best-seller">
      <div class="best-seller-header">
        <h2 class="section-title">Best Seller</h2>
        <div class="slider-buttons">
          <button @click="scrollLeft" aria-label="Scroll Left">&#10094;</button>
          <button @click="scrollRight" aria-label="Scroll Right">&#10095;</button>
        </div>
      </div>

      <div class="best-seller-wrapper">
        <div class="best-seller-grid" ref="bestSellerScroll">
          <ProductCard
            v-for="item in bestSellers"
            :key="item.id"
            :product="item"
          />
        </div>
      </div>

      <button class="lihat-produk-btn" @click="lihatProduk">Lihat Produk</button>
    </section>

    <!-- Shop by Categories -->
    <section class="shop-by-categories">
      <h2 class="section-title">Shop by Categories</h2>
      <ShopByCategories />
    </section>
  </div>
</template>

<script>
import { onMounted, computed } from 'vue'
import { useProductStore } from '@/Stores/Product'

import HeroBanner from '../components/HeroBanner.vue'
import ProductCard from '../components/ProductCard.vue'
import ShopByCategories from '@/components/ShopbyCategories.vue'

export default {
  components: {
    HeroBanner,
    ProductCard,
    ShopByCategories
  },
  setup() {
    const productStore = useProductStore()

    const bestSellers = computed(() => productStore.getBestSellers)

    const scrollLeft = () => {
      document.querySelector('.best-seller-grid').scrollBy({ left: -300, behavior: 'smooth' })
    }

    const scrollRight = () => {
      document.querySelector('.best-seller-grid').scrollBy({ left: 300, behavior: 'smooth' })
    }

    const lihatProduk = () => {
      window.location.href = '/products'
    }

    // ⏳ Fetch produk dari API saat komponen dimount
    onMounted(async () => {
      if (productStore.products.length === 0) {
        await productStore.fetchProducts()
      }
    })

    return {
      bestSellers,
      scrollLeft,
      scrollRight,
      lihatProduk
    }
  }
}
</script>

<style scoped>
/* ... semua CSS tetap seperti yang kamu tulis sebelumnya ... */
.menu-page {
  font-family: 'Poppins', sans-serif;
  padding-bottom: 4rem;
  color: #2E1A1A;
}

.section-title {
  font-size: 2rem;
  font-weight: bold;
  margin: 0;
}

.best-seller-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 2rem 2rem 1rem;
}

.slider-buttons {
  display: flex;
  gap: 1rem;
}
.slider-buttons button {
  background: #fff;
  border: 1px solid #ccc;
  padding: 0.3rem 0.7rem;
  font-size: 1.5rem;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.slider-buttons button:hover {
  background-color: #d33;
  color: #fff;
}

.best-seller-wrapper {
  overflow-x: auto;
  padding: 0 2rem;
}
.best-seller-grid {
  display: flex;
  gap: 1.5rem;
  padding-bottom: 1rem;
  min-width: max-content;
}

.best-seller-grid ::v-deep(.product-card) {
  flex: 0 0 240px;
}

.lihat-produk-btn {
  margin: 1rem auto 0;
  display: block;
  background: transparent;
  border: 1px solid #45000D;
  color: #45000D;
  padding: 0.5rem 1.2rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease, color 0.3s ease;
}
.lihat-produk-btn:hover {
  background-color: #45000D;
  color: #fff;
}

.shop-by-categories {
  margin-top: 4rem;
  padding: 0 1rem;
}
</style>
