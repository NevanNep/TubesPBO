<template>
    <section class="shop-categories">
      <h2 class="title">SHOP BY CATEGORIES</h2>
      <div class="category-grid">
        <router-link to="/category/men" class="category-card">
          <img :src="images.men" alt="Men" />
          <span>Men</span>
        </router-link>
        <router-link to="/category/women" class="category-card">
          <img :src="images.women" alt="Women" />
          <span>Women</span>
        </router-link>
        <router-link to="/category/unisex" class="category-card">
          <img :src="images.unisex" alt="Unisex" />
          <span>Unisex</span>
        </router-link>
      </div>
    </section>
  </template>
  
  <script>
  import menImg from '@/assets/men.jpg'
  import womenImg from '@/assets/women.jpg'
  import unisexImg from '@/assets/unisex.jpg'
  
  export default {
    name: 'ShopByCategories',
    data() {
      return {
        images: {
          men: menImg,
          women: womenImg,
          unisex: unisexImg,
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .shop-categories {
    padding: 40px 20px;
    text-align: center;
    font-family: "Poppins", sans-serif;
  }
  
  .title {
    font-size: 1.8rem;
    font-weight: 700;
    margin-bottom: 30px;
    color: #45000D;
  }
  
  .category-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
    gap: 24px;
    justify-items: center;
  }
  
  .category-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-decoration: none;
    color: #45000D;
    transition: transform 0.3s ease;
  }
  
  .category-card img {
    width: 100%;
    height: 250px;
    object-fit: cover;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
  
  .category-card span {
    margin-top: 12px;
    font-size: 1rem;
    font-weight: 600;
  }
  
  .category-card:hover {
    transform: scale(1.03);
    color: #700014;
  }
  </style>
  