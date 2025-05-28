<template>
  <div class="container py-5">
    <h2 class="text-center mb-4 fw-bold section-title">
      Pilih Parfum Favoritmu
    </h2>

    <!-- Tombol Filter -->
    <div class="d-flex justify-content-center gap-3 mb-5">
      <button
        class="btn btn-outline-secondary rounded-pill px-4"
        :class="{ active: currentFilter === 'all' }"
        @click="showAllProducts"
      >
        Semua Parfum
      </button>
      <button
        class="btn btn-outline-danger rounded-pill px-4"
        :class="{ active: currentFilter === 'women' }"
        @click="filterProducts('women')"
      >
        Parfum Wanita
      </button>
      <button
        class="btn btn-outline-primary rounded-pill px-4"
        :class="{ active: currentFilter === 'men' }"
        @click="filterProducts('men')"
      >
        Parfum Pria
      </button>
    </div>

    <!-- Produk Parfum -->
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
      <div v-for="product in filteredProducts" :key="product.id" class="col">
        <div class="card h-100 shadow-sm border-0 product-card">
          <router-link :to="`/product/${product.id}`" class="d-block overflow-hidden">
            <img :src="product.image" :alt="product.name" class="card-img-top" />
          </router-link>
          <div class="card-body text-center">
            <h5 class="card-title fw-semibold">{{ product.name }}</h5>
            <p class="card-text text-muted small">{{ product.description }}</p>
            <p class="fw-bold text-dark">Rp {{ product.price.toLocaleString() }}</p>
            <button @click="addToCart(product)" class="btn btn-danger w-100">
              <i class="bi bi-cart-plus me-1"></i> Tambah ke Keranjang
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Keranjang -->
    <div class="position-fixed end-0 bottom-0 m-4" style="z-index: 1050; width: 300px;">
      <div class="card shadow-lg border-0">
        <div class="card-body">
          <h5 class="card-title fw-bold mb-3">🛒 Keranjang Kamu</h5>
          <p class="mb-1">Total Barang: <span class="badge bg-secondary">{{ cartItems.length }}</span></p>
          <p>Total Harga: <span class="text-dark">Rp {{ totalPrice.toLocaleString() }}</span></p>
          <router-link to="/cart" class="btn btn-danger w-100 mt-2">
            Lihat Keranjang
          </router-link>
        </div>
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
          image: 'images/Sauvage.jpg',
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
      currentFilter: 'all',
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
      const existing = cartItems.find(item => item.id === product.id);

      if (existing) {
        existing.quantity += 1;
      } else {
        product.quantity = 1;
        cartItems.push(product);
      }

      localStorage.setItem('cartItems', JSON.stringify(cartItems));
      this.cartItems = cartItems;
    },
    filterProducts(category) {
      this.filteredProducts = this.products.filter(p => p.category === category);
      this.currentFilter = category;
    },
    showAllProducts() {
      this.filteredProducts = this.products;
      this.currentFilter = 'all';
    }
  },
  mounted() {
    this.showAllProducts();
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&display=swap');

.card-img-top {
  height: 250px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.card-img-top:hover {
  transform: scale(1.05);
}

.product-card:hover {
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
  transition: box-shadow 0.3s ease;
}

/* Gaya untuk judul bagian */
.section-title {
  color: #000;
  font-family: 'Playfair Display', serif;
  font-size: 2.5rem;
  margin-top: -60px;
}

/* Highlight tombol filter aktif */
button.active {
  color: white !important;
  border-color: transparent !important;
  background-color: currentColor !important;
}

.btn-outline-danger.active {
  background-color: #dc3545 !important;
  border-color: #dc3545 !important;
  color: white !important;
}

.btn-outline-primary.active {
  background-color: #0d6efd !important;
  border-color: #0d6efd !important;
  color: white !important;
}

.btn-outline-secondary.active {
  background-color: #6c757d !important;
  border-color: #6c757d !important;
  color: white !important;
}

footer a:hover {
  text-decoration: underline;
}
</style>