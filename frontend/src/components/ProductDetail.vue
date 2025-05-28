<template>
  <div class="container my-5">
    <div class="row g-4 align-items-center">
      <!-- Gambar Produk -->
      <div class="col-md-5 text-center">
        <img :src="product.image" :alt="product.name" class="img-fluid rounded shadow product-image" />
      </div>

      <!-- Detail Produk -->
      <div class="col-md-7">
        <h2 class="fw-bold mb-3">{{ product.name }}</h2>
        <p class="text-muted">{{ product.description }}</p>
        <h4 class="text-danger fw-bold mb-2">Rp {{ product.price.toLocaleString() }}</h4>

        <!-- Stok Produk -->
        <p :class="{'text-success': product.stock > 0, 'text-danger': product.stock === 0}" class="fw-semibold mb-4">
          Stok: {{ product.stock > 0 ? product.stock : 'Habis' }}
        </p>

        <button
          @click="addToCart"
          class="btn btn-danger btn-lg"
          :disabled="product.stock === 0"
          :title="product.stock === 0 ? 'Produk ini sedang habis' : 'Tambah ke Keranjang'"
        >
          <i class="bi bi-cart-plus me-2"></i>
          {{ product.stock === 0 ? 'Stok Habis' : 'Tambah ke Keranjang' }}
        </button>
      </div>
    </div>

    <!-- Review Produk -->
    <div class="mt-5">
      <h4 class="section-title">Review Produk</h4>
      <div v-if="reviews.length === 0" class="text-muted fst-italic">Belum ada review.</div>
      <div v-else class="list-group mt-3">
        <div
          v-for="(review, index) in reviews"
          :key="index"
          class="list-group-item"
        >
          <div class="d-flex justify-content-between">
            <strong>{{ review.user }}</strong>
            <span class="text-warning"><i class="bi bi-star-fill"></i> {{ review.rating }}/5</span>
          </div>
          <p class="mb-0">{{ review.text }}</p>
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
          description: 'Parfum elegan dengan aroma floral dan sentuhan jasmine.',
          image: '/images/parfumeMissDior.jpg',
          stock: 5,
        },
        {
          id: 2,
          name: 'Dior Sauvage',
          price: 2700000,
          description: 'Aroma maskulin dengan wangi citrus dan amberwood.',
          image: '/images/Sauvage.jpg',
          stock: 8,
        },
        {
          id: 3,
          name: 'Chanel No. 5',
          price: 3200000,
          description: 'Parfum klasik dengan kombinasi floral dan aldehyde.',
          image: '/images/parfumeChanelNo5.jpg',
          stock: 3,
        },
      ],
      product: {},
      reviews: [],
      newReview: {
        user: '',
        rating: '',
        text: '',
      },
    };
  },
  methods: {
    addToCart() {
       const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));

    if (!loggedInUser) {
      alert('Silakan login terlebih dahulu untuk membeli produk.');
    this.$router.push('/login');
      return;
    }

      if (this.product.stock === 0) {
        alert('Maaf, produk ini sedang habis stok.');
        return;
      }

      const cart = JSON.parse(localStorage.getItem('cartItems')) || [];

      const existingIndex = cart.findIndex(p => p.id === this.product.id);
      if (existingIndex !== -1) {
        cart[existingIndex].quantity += 1;
      } else {
        cart.push({ ...this.product, quantity: 1 });
      }

      localStorage.setItem('cartItems', JSON.stringify(cart));

      this.product.stock -= 1;
      this.saveProductStock();

      alert('Produk berhasil ditambahkan ke keranjang!');
    },

    saveProductStock() {
      let storedProducts = JSON.parse(localStorage.getItem('products')) || {};
      storedProducts[this.product.id] = { ...this.product };
      localStorage.setItem('products', JSON.stringify(storedProducts));
    },

    loadProduct() {
      const id = parseInt(this.$route.params.id);
      let storedProducts = JSON.parse(localStorage.getItem('products')) || {};

      // Jika produk sudah pernah disimpan ke localStorage (karena perubahan stok, dll)
      if (storedProducts[id]) {
        this.product = storedProducts[id];
      } else {
        // Ambil dari daftar default
        this.product = this.products.find(p => p.id === id) || this.products[0];
      }
    },

    submitReview() {
      this.reviews.push({ ...this.newReview });
      this.newReview = { user: '', rating: '', text: '' };
      alert('Ulasan berhasil dikirim!');
    },

    generateSampleReviews() {
      return [
        { user: 'Ayu', rating: 5, text: 'Wangi elegan dan tahan lama.' },
        { user: 'Rani', rating: 4, text: 'Suka aromanya, cocok dipakai malam hari.' },
        { user: 'Dina', rating: 5, text: 'Perfume favoritku sejauh ini!' },
      ];
    },
  },
  created() {
    this.loadProduct();
    this.reviews = this.generateSampleReviews();
  },
};
</script>
//TES
