<template>
  <div class="review-section mt-5">
    <h3 class="section-title">Ulasan Produk</h3>

    <!-- Form Ulasan -->
    <form @submit.prevent="addReview" class="p-3 rounded shadow-sm bg-light mt-3">
      <div class="mb-3">
        <label for="reviewText" class="form-label">Tulis ulasan:</label>
        <textarea
          id="reviewText"
          v-model="reviewText"
          required
          class="form-control"
          placeholder="Bagikan pengalaman Anda menggunakan produk ini..."
        ></textarea>
      </div>

      <div class="mb-3">
        <label for="rating" class="form-label">Rating:</label>
        <select v-model="reviewRating" id="rating" required class="form-select">
          <option disabled value="">Pilih rating</option>
          <option v-for="n in 5" :key="n" :value="n">{{ n }}</option>
        </select>
      </div>

      <button type="submit" class="btn btn-success w-100">
        <i class="bi bi-send me-2"></i>Kirim Ulasan
      </button>
    </form>

    <!-- Daftar Ulasan -->
    <div v-if="reviews.length > 0" class="mt-5">
      <h5 class="fw-bold mb-3">Ulasan dari Pembeli:</h5>
      <div
        v-for="(review, index) in reviews"
        :key="index"
        class="border-bottom pb-3 mb-3"
      >
        <div class="d-flex justify-content-between">
          <strong>{{ review.user }}</strong>
          <span class="text-warning">
            <i class="bi bi-star-fill"></i> {{ review.rating }} / 5
          </span>
        </div>
        <p class="mb-0">{{ review.text }}</p>
      </div>
    </div>

    <div v-else class="text-muted mt-4 fst-italic">Belum ada ulasan untuk produk ini.</div>
  </div>
</template>

<script>
export default {
  props: ['productId'],
  data() {
    return {
      reviewText: '',
      reviewRating: '',
      reviews: [],
    };
  },
  methods: {
    addReview() {
      if (this.reviewText.trim() && this.reviewRating) {
        const newReview = {
          user: 'User', // Ganti dengan nama user yang login
          text: this.reviewText,
          rating: this.reviewRating,
        };
        this.reviews.push(newReview);
        localStorage.setItem('reviews_' + this.productId, JSON.stringify(this.reviews));
        this.reviewText = '';
        this.reviewRating = '';
      }
    },
    loadReviews() {
      const saved = localStorage.getItem('reviews_' + this.productId);
      this.reviews = saved ? JSON.parse(saved) : [];
    }
  },
  mounted() {
    this.loadReviews();
  }
};
</script>

<style scoped>
.section-title {
  font-size: 1.8rem;
  font-family: 'Playfair Display', serif;
  border-left: 5px solid #28a745;
  padding-left: 12px;
  color: #28a745;
}
textarea {
  resize: none;
}
</style>