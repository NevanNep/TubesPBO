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
import HeroBanner from '../components/HeroBanner.vue'
import ProductCard from '../components/ProductCard.vue'
import ShopByCategories from '@/components/ShopbyCategories.vue'

import hitamImg from '@/assets/hitamm.jpeg'
import parfumGayImg from '@/assets/parfumgay.png'
import parfumCeweImg from '@/assets/parfumcewecakep.png'
import toxicImg from '@/assets/toxic.png'
import avienaImg from '@/assets/aviena.jpeg'
import bohemianImg from '@/assets/bohemian.png'
import aquaImg from '@/assets/aqua.jpeg'
import merahMeronaImg from '@/assets/merahmerona.png'

export default {
  components: {
    HeroBanner,
    ProductCard,
    ShopByCategories
  },
  data() {
    return {
      bestSellers: [
        {
          id: 101,
          brand: 'YVES SAINT LAURENT',
          name: 'Yves Saint Laurent MYSLF EDP 100 ml',
          price: 2350000,
          discount: 25,
          rating: 4,
          image: hitamImg,
          category: 'Unisex',
          isNew: false
        },
        {
          id: 102,
          brand: 'SAMPAR',
          name: 'Sampar Prodigal Pen 6 ml',
          price: 1400000,
          discount: 50,
          rating: 4,
          image: parfumGayImg,
          category: 'Women',
          isNew: false
        },
        {
          id: 103,
          brand: 'YVES ROCHER',
          name: 'Yves Rocher Anti Dandruff Shampoo 300 ml',
          price: 2390000,
          discount: 44,
          rating: 5,
          image: parfumCeweImg,
          category: 'Men',
          isNew: false
        },
        {
          id: 104,
          brand: 'ESSENTIAL PARFUMS',
          name: 'Essential Parfums Bois Imperial EDP 100 ml',
          price: 1990000,
          discount: 10,
          rating: 5,
          image: toxicImg,
          category: 'Unisex',
          isNew: false
        },
        {
          id: 105,
          brand: 'GIORGIO ARMANI',
          name: 'Emporio Armani Stronger With You Intensely EDP 100 ml',
          price: 1870000,
          discount: 0,
          rating: 5,
          image: avienaImg,
          category: 'Men',
          isNew: false
        },
        {
          id: 106,
          brand: 'BOHEMIAN PARFUME',
          name: 'Bohemian Taste With You EDP 250 ml',
          price: 2400000,
          discount: 0,
          rating: 5,
          image: bohemianImg,
          category: 'Men',
          isNew: false
        },
        {
          id: 107,
          brand: 'GIORGIO ARMANI',
          name: 'Emporio Armani Stronger With You Intensely EDP 100 ml',
          price: 1870000,
          discount: 0,
          rating: 5,
          image: aquaImg,
          category: 'Men',
          isNew: false
        },
        {
          id: 108,
          brand: 'GIORGIO ARMANI',
          name: 'Emporio Armani Stronger With You Intensely EDP 100 ml',
          price: 1870000,
          discount: 0,
          rating: 5,
          image: merahMeronaImg,
          category: 'Women',
          isNew: false
        }
      ]
    }
  },
  methods: {
    scrollLeft() {
      this.$refs.bestSellerScroll.scrollBy({ left: -300, behavior: 'smooth' })
    },
    scrollRight() {
      this.$refs.bestSellerScroll.scrollBy({ left: 300, behavior: 'smooth' })
    },
    lihatProduk() {
      this.$router.push('/products')
    }
  }
}
</script>

<style scoped>
.menu-page {
  font-family: 'Poppins', sans-serif;
  padding-bottom: 4rem;
  color: #2E1A1A;
}

/* Section Titles */
.section-title {
  font-size: 2rem;
  font-weight: bold;
  margin: 0;
}

/* Best Seller Header */
.best-seller-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 2rem 2rem 1rem;
}

/* Slider Buttons */
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

/* Scroll Container */
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

/* Product Card diatur proporsional */
.best-seller-grid ::v-deep(.product-card) {
  flex: 0 0 240px;
}

/* Lihat Produk Button */
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

/* Kamu bisa tambahkan styling untuk .shop-by-categories di sini */
.shop-by-categories {
  margin-top: 4rem;
  padding: 0 1rem;
}
</style>
