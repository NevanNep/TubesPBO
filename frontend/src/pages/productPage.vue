<template>
  <div class="product-page">
    <h2>Daftar Produk</h2>
    <div class="product-list">
      <ProductCard v-for="product in products" :key="product.id" :product="product" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import ProductCard from '@/components/ProductCard.vue';

export default {
  name: "ProductPage",
  components: { ProductCard },
  data() {
    return {
      products: []
    };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    fetchProducts() {
      axios.get('http://localhost:8081/api/products') // Ganti sesuai endpoint backend-mu
        .then(response => {
          this.products = response.data;
        })
        .catch(error => {
          console.error('Gagal mengambil produk:', error);
        });
    }
  }
};
</script>

<style scoped>
.product-page {
  padding: 30px;
  color: #333;
}

.product-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
</style>
