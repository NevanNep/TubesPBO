<template>
  <div class="product-card">
    <img :src="product.image" alt="product.name" class="product-image" />
    <h3 class="product-name">{{ product.name }}</h3>
    <p class="product-description">{{ product.description }}</p>
    <p class="product-price">Rp {{ product.price }}</p>
    <button @click="addToCart">Tambah ke Keranjang</button>
  </div>
</template>

<script>
export default {
  props: ['product'],
  methods: {
    addToCart() {
      // Mengirim event ke parent untuk menambahkan produk ke keranjang
      this.$emit('add-to-cart', this.product);
    }
  }
}
</script>

<style scoped>
.product-card {
  border: 1px solid #ddd;
  padding: 20px;
  margin: 10px;
  text-align: center;
}

.product-image {
  width: 100%;
  max-width: 200px;
  height: auto;
}

.product-name {
  font-size: 1.5rem;
  margin-top: 10px;
}

.product-description {
  color: #555;
}

.product-price {
  font-weight: bold;
  color: #e60000;
}

button {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
