<template>
  <div id="app">
    <h1>Pilih Parfum Favoritmu</h1>
    <div class="tabs">
      <button @click="selectCategory('woman')">Parfum Wanita</button>
      <button @click="selectCategory('man')">Parfum Pria</button>
    </div>
    <ProductList :category="selectedCategory" @add-to-cart="addToCart"/>
    <Cart :cart="cart"/>
  </div>
</template>

<script>
import ProductList from './components/ProductDetail.vue';
import Cart from './components/CartPage.vue';

export default {
  name: 'App',
  components: {
    ProductList,
    Cart
  },
  data() {
    return {
      selectedCategory: 'woman',  // default to 'woman'
      cart: JSON.parse(localStorage.getItem('cartItems')) || [],
    };
  },
  methods: {
    selectCategory(category) {
      this.selectedCategory = category;
    },
    addToCart(product) {
      const existingItem = this.cart.find(item => item.id === product.id);
      if (existingItem) {
        existingItem.quantity += 1;
      } else {
        product.quantity = 1;
        this.cart.push(product);
      }
      localStorage.setItem('cartItems', JSON.stringify(this.cart)); // Store in localStorage
    }
  }
};
</script>

<style>
/* Basic styles for layout */
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.tabs {
  margin: 10px;
}

button {
  padding: 10px;
  margin: 5px;
}

h1 {
  font-size: 24px;
  font-weight: bold;
}
</style>
