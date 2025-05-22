<template>
  <div class="product-list">
    <div class="product" v-for="product in filteredProducts" :key="product.id">
      <img :src="product.image" :alt="product.name" class="product-image"/>
      <div class="product-details">
        <h3>{{ product.name }}</h3>
        <p>{{ product.description }}</p>
        <div class="product-price">
          <p class="price">Rp {{ product.price.toLocaleString() }}</p>
          <span v-if="product.discount" class="discount-badge">{{ product.discount }}%</span>
        </div>
        <button @click="addToCart(product)">Tambah ke Keranjang</button>
        <button class="quick-view" @click="quickView(product)">Quick View</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductList',
  props: ['category'],
  data() {
    return {
      products: [
        { id: 1, name: 'Avicenna', description: 'Avicenna Alter Ego Extrait', price: 559000, image: '/assets/avicenna.jpg', category: 'woman', discount: 25 },
        { id: 2, name: 'Thank You Farmer', description: 'Bakuvita Treatment Toner', price: 479000, image: '/assets/thankyoufarmer.jpg', category: 'woman', discount: 10 },
        { id: 3, name: 'Police', description: 'To Be My Avatar for Men', price: 825000, image: '/assets/police.jpg', category: 'man', discount: 30 },
        { id: 4, name: 'Banana Republic', description: 'Dark Cherry & Amber EDP', price: 1320000, image: '/assets/banana.jpg', category: 'man', discount: 30 },
        // Add more products here...
      ]
    };
  },
  computed: {
    filteredProducts() {
      return this.products.filter(product => product.category === this.category);
    }
  },
  methods: {
    addToCart(product) {
      this.$emit('add-to-cart', product); // Emit event to add product to cart
    },
    quickView(product) {
      console.log("Quick View for product:", product);
      // Add logic for opening quick view modal or redirecting to a detailed page
    }
  }
};
</script>

<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.product {
  border: 1px solid #ccc;
  margin: 20px;
  padding: 20px;
  width: 220px;
  text-align: center;
  position: relative;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.product img {
  width: 150px;
  height: 150px;
  object-fit: cover;
}

.discount-badge {
  background-color: red;
  color: white;
  font-size: 1.2rem;
  padding: 8px 12px;
  border-radius: 10px;
  position: absolute;
  top: 10px;
  left: 10px;
}

button {
  padding: 12px;
  background-color: #42b983;
  color: white;
  border: none;
  cursor: pointer;
  margin-top: 10px;
  width: 100%;
}

button:hover {
  background-color: #388e3c;
}

.quick-view {
  background-color: #f1f1f1;
  margin-top: 5px;
  width: 100%;
}

.quick-view:hover {
  background-color: #e0e0e0;
}
</style>
