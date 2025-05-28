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
      defaultProduct: {
        id: 1,
        name: 'Miss Dior',
        price: 3000000,
        description: 'Parfum elegan dengan aroma floral dan sentuhan jasmine.',
        image: '/images/parfumeMissDior.jpg',
        stock: 5,
      },
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

      // Kurangi stok dan simpan ulang
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
      let storedProducts = JSON.parse(localStorage.getItem('products')) || {};
      this.product = storedProducts[this.defaultProduct.id] || { ...this.defaultProduct };
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
    }
  },
  created() {
    this.loadProduct();
    this.reviews = this.generateSampleReviews();
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&display=swap');

.product-image {
  max-height: 400px;
  object-fit: cover;
  border-radius: 12px;
  transition: transform 0.3s ease;
}
.product-image:hover {
  transform: scale(1.03);
}
.section-title {
  font-family: 'Playfair Display', serif;
  font-size: 1.8rem;
  color: #000000;
  border-left: 5px solid #000000;
  padding-left: 12px;
  margin-bottom: 1rem;
}
</style>