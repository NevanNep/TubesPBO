<template>
  <div class="container py-5">
    <h2 class="text-center mb-5">Pilih Parfum Favoritmu</h2>

    <!-- Filter berdasarkan kategori -->
    <div class="text-center mb-5">
      <button class="btn btn-outline-primary" @click="filterProducts('women')">Parfum Wanita</button>
      <button class="btn btn-outline-primary" @click="filterProducts('men')">Parfum Pria</button>
    </div>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
      <div v-for="product in filteredProducts" :key="product.id" class="col">
        <div class="card h-100">
          <router-link :to="`/product/${product.id}`">
            <img :src="product.image" alt="product.name" class="card-img-top" />
          </router-link>
          <div class="card-body">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="card-text">{{ product.description }}</p>
            <p class="card-text text-danger">Rp {{ product.price }}</p>
            <button @click="addToCart(product)" class="btn btn-primary w-100">
              Tambah ke Keranjang
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Keranjang -->
    <div class="fixed-bottom mb-4 ms-4" style="z-index: 9999;">
      <div class="card p-3 shadow">
        <h5>Keranjang Kamu</h5>
        <p>Total Barang: {{ cartItems.length }}</p>
        <p>Total Harga: Rp {{ totalPrice }}</p>
        <router-link to="/cart" class="btn btn-success w-100">
          Lihat Keranjang
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: [
        {
          id: 1,
          name: 'Miss Dior',
          price: 3000000,
          description: 'A timeless floral fragrance with aldehydes and jasmine notes.',
          image: 'images/parfumeMissDior.jpg',
          category: 'women',
        },
        {
          id: 2,
          name: 'Dior Sauvage',
          price: 2500000,
          description: 'A bold fragrance with notes of bergamot, pepper, and ambroxan.',
          image: 'images/parfumeSauvageDior.jpg',
          category: 'men',
        },
        {
          id: 3,
          name: 'Chanel No. 5',
          price: 2500000,
          description: 'A timeless floral fragrance with aldehydes and jasmine notes.',
          image: 'images/parfumeChanelNo5.jpg',
          category: 'women',
        }
      ],
      cartItems: JSON.parse(localStorage.getItem('cartItems')) || [],
      filteredProducts: [],
    };
  },
  computed: {
    totalPrice() {
      return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
    }
  },
  methods: {
    addToCart(product) {
      let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
      const productExists = cartItems.find(item => item.id === product.id);

      if (productExists) {
        productExists.quantity += 1;
      } else {
        product.quantity = 1;
        cartItems.push(product);
      }

      localStorage.setItem('cartItems', JSON.stringify(cartItems));
      this.cartItems = cartItems; // Update data di Vue
    },
    filterProducts(category) {
      this.filteredProducts = this.products.filter(product => product.category === category);
    }
  },
  mounted() {
    // Menampilkan semua produk saat pertama kali
    this.filteredProducts = this.products;
  }
};
</script>

<style scoped>
.container {
  padding: 0 20px;
}

.card {
  background-color: white;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.card-img-top {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.card-body {
  text-align: center;
}

.card-title {
  font-size: 1.2rem;
  font-weight: bold;
}

.card-text {
  color: #777;
}

.btn-primary {
  background-color: #007bff;
  border: none;
}

.fixed-bottom {
  position: fixed;
  bottom: 10px;
  right: 10px;
}

.card p {
  margin: 5px 0;
}

.card a {
  display: block;
  margin-top: 10px;
}
</style>
