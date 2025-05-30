<template>
    <div class="category-page">
      <!-- Breadcrumb -->
      <nav class="breadcrumb">
        <span>Home</span> › <span>FRAGRANCE</span> › <span class="active">{{ pageTitle }}</span>
      </nav>
  
      <!-- Title -->
      <h1 class="page-title">{{ pageTitle }}</h1>
  
      <!-- Layout -->
      <div class="layout">
        <!-- Filter Sidebar -->
        <FilterSidebar :price="price" :priceRange="priceRange" :subcategories="subcategories" />
  
        <!-- Product Section -->
        <section class="products">
          <div class="controls">
            <select>
              <option>Price: Lowest - Highest</option>
              <option>Price: Highest - Lowest</option>
            </select>
            <div class="sort-item">
              <label for="show">Tampilkan</label>
              <select id="show">
                <option>12 Item</option>
                <option>24 Item</option>
              </select>
            </div>
          </div>
  
          <div class="product-grid">
            <ProductCard
              v-for="product in products"
              :key="product.id"
              :product="product"
            />
          </div>
        </section>
      </div>
    </div>
  </template>
  
  <script setup>
  import { computed } from 'vue'
  import { useRoute } from 'vue-router'
  import { useProductStore } from '@/Stores/Product'
  
  import FilterSidebar from '@/components/FilterSidebar.vue'
  import ProductCard from '@/components/ProductCard.vue'
  
  // Routing
  const route = useRoute()
  const type = route.params.type || 'men'
  
  // Pinia Store
  const productStore = useProductStore()
  
  // Products berdasarkan kategori
  const products = computed(() => productStore.getProductsByCategory(type))
  
  // Judul berdasarkan kategori
  const pageTitle = computed(() => {
    const titles = {
      men: 'FOR MEN',
      women: 'FOR WOMEN',
      unisex: 'UNISEX'
    }
    return titles[type] || 'CATEGORY'
  })
  
  // Dummy untuk filter (belum interaktif)
  const subcategories = ['EAU DE PARFUM', 'EAU DE TOILETTE', 'EXTRAIT DE PARFUM']
  const price = 3139000
  const priceRange = { max: 3139000 }
  </script>
  
  <style scoped>
  .category-page {
    padding: 2rem;
    font-family: 'Helvetica Neue', sans-serif;
  }
  
  .breadcrumb {
    font-size: 0.9rem;
    color: #555;
    margin-bottom: 0.5rem;
  }
  
  .breadcrumb .active {
    font-weight: bold;
    color: #C30010;
    font-family: Poppins;
  }
  
  .page-title {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
    color: #000;
    font-family: Poppins;
  }
  
  .layout {
    display: flex;
    gap: 2rem;
  }
  
  .products {
    flex: 1;
  }
  
  .controls {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1rem;
  }
  
  .controls select {
    padding: 0.4rem 1rem;
    border: 1px solid #ccc;
    background: #fff;
  }
  
  .sort-item {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  
  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
    gap: 1.5rem;
  }
  </style>
  