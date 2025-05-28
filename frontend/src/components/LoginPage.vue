<template>
  <div class="container my-5">
    <h2 class="mb-4 fw-bold text-center">Dashboard Pembeli</h2>

    <div v-if="!loggedInUser" class="alert alert-warning text-center">
      Silakan <router-link to="/login" class="text-danger">login</router-link> terlebih dahulu.
    </div>

    <div v-else>
      <h5 class="mb-3">Halo, {{ loggedInUser.name || loggedInUser.email }}</h5>

      <h4 class="mb-3">History Pembelian</h4>

      <div v-if="purchaseHistory.length === 0" class="text-muted fst-italic">
        Belum ada produk yang dibeli.
      </div>

      <div v-else class="list-group mb-4">
        <div
          v-for="(item, index) in purchaseHistory"
          :key="index"
          class="list-group-item d-flex justify-content-between align-items-center"
        >
          <div>
            <strong>{{ item.name }}</strong> <br />
            Jumlah: {{ item.quantity }} <br />
            Harga: Rp {{ item.price.toLocaleString() }}
          </div>

          <div>
            <button
              v-if="!hasReviewed(item.id)"
              @click="openReviewForm(item)"
              class="btn btn-outline-primary btn-sm"
            >
              Beri Ulasan
            </button>
            <span v-else class="badge bg-success">Sudah Diulas</span>
          </div>
        </div>
      </div>

      <!-- Form Ulasan -->
      <div v-if="showReviewForm" class="card p-4 mb-4">
        <h5 class="mb-3">Ulas Produk: {{ currentReviewProduct.name }}</h5>

        <div class="mb-3">
          <label for="reviewText" class="form-label">Ulasan</label>
          <textarea
            id="reviewText"
            v-model="newReview.text"
            rows="3"
            class="form-control"
            placeholder="Tulis ulasanmu di sini..."
          ></textarea>
        </div>

        <div class="mb-3">
          <label for="reviewRating" class="form-label">Rating</label>
          <select id="reviewRating" v-model.number="newReview.rating" class="form-select">
            <option disabled value="">Pilih rating</option>
            <option v-for="n in 5" :key="n" :value="n">{{ n }}</option>
          </select>
        </div>

        <button class="btn btn-primary me-2" @click="submitReview">Kirim Ulasan</button>
        <button class="btn btn-secondary" @click="cancelReview">Batal</button>
      </div>

      <!-- Tampilkan Review yang sudah dibuat user -->
      <div v-if="myReviews.length > 0">
        <h4>Review Saya</h4>
        <div class="list-group">
          <div
            v-for="(review, idx) in myReviews"
            :key="idx"
            class="list-group-item border rounded mb-2"
          >
            <strong>{{ getProductNameById(review.productId) }}</strong>
            <div>
              Rating: <span class="text-warning">{{ review.rating }}/5</span>
            </div>
            <p class="mb-0 fst-italic">"{{ review.text }}"</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loggedInUser: null,
      purchaseHistory: [],
      showReviewForm: false,
      currentReviewProduct: null,
      newReview: {
        text: '',
        rating: null,
      },
      myReviews: [],
      products: [
        {
          id: 1,
          name: 'Miss Dior',
          price: 3000000,
        },
        {
          id: 2,
          name: 'Dior Sauvage',
          price: 2700000,
        },
        {
          id: 3,
          name: 'Chanel No. 5',
          price: 3200000,
        },
      ],
    };
  },
  methods: {
    loadUser() {
      this.loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    },
    loadPurchaseHistory() {
      // Contoh: ambil cartItems sebagai history pembelian
      // kamu bisa modifikasi sesuai data riil pembelian
      this.purchaseHistory = JSON.parse(localStorage.getItem('cartItems')) || [];
    },
    loadMyReviews() {
      const allUserReviews = JSON.parse(localStorage.getItem('userReviews')) || [];
      if (this.loggedInUser) {
        this.myReviews = allUserReviews.filter(r => r.userEmail === this.loggedInUser.email);
      } else {
        this.myReviews = [];
      }
    },
    hasReviewed(productId) {
      return this.myReviews.some(r => r.productId === productId);
    },
    openReviewForm(product) {
      this.currentReviewProduct = product;
      this.showReviewForm = true;
      this.newReview.text = '';
      this.newReview.rating = null;
    },
    cancelReview() {
      this.showReviewForm = false;
      this.currentReviewProduct = null;
    },
    submitReview() {
      if (!this.newReview.text.trim() || !this.newReview.rating) {
        alert('Tolong isi ulasan dan rating terlebih dahulu.');
        return;
      }
      const allUserReviews = JSON.parse(localStorage.getItem('userReviews')) || [];

      allUserReviews.push({
        productId: this.currentReviewProduct.id,
        userEmail: this.loggedInUser.email,
        user: this.loggedInUser.name || this.loggedInUser.email,
        rating: this.newReview.rating,
        text: this.newReview.text.trim(),
      });

      localStorage.setItem('userReviews', JSON.stringify(allUserReviews));

      alert('Terima kasih atas ulasanmu!');
      this.showReviewForm = false;
      this.loadMyReviews();
    },
    getProductNameById(id) {
      const product = this.products.find(p => p.id === id);
      return product ? product.name : 'Produk tidak diketahui';
    },
  },
  mounted() {
    this.loadUser();
    this.loadPurchaseHistory();
    this.loadMyReviews();
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
}

h2 {
  font-family: 'Playfair Display', serif;
}

.list-group-item {
  background: #fff;
}

textarea {
  resize: none;
}
</style>
