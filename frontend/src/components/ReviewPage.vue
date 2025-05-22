<template>
  <div>
    <h3>Ulasan Produk</h3>
    <form @submit.prevent="addReview">
      <div>
        <label for="reviewText">Tulis ulasan:</label>
        <textarea id="reviewText" v-model="reviewText" required></textarea>
      </div>
      <button type="submit">Kirim Ulasan</button>
    </form>
    <div v-if="reviews.length > 0">
      <h4>Ulasan yang telah diberikan:</h4>
      <ul>
        <li v-for="(review, index) in reviews" :key="index">
          {{ review.user }}: {{ review.text }} (Rating: {{ review.rating }} / 5)
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: ['productId'],
  data() {
    return {
      reviewText: '',
      reviews: JSON.parse(localStorage.getItem('reviews_' + this.productId)) || [] // Ambil ulasan berdasarkan productId
    };
  },
  methods: {
    addReview() {
      if (this.reviewText.trim() !== '') {
        const newReview = {
          user: 'User', // Ganti dengan nama pengguna yang login
          text: this.reviewText,
          rating: 5 // Default rating (bisa diubah sesuai kebutuhan)
        };
        this.reviews.push(newReview);
        localStorage.setItem('reviews_' + this.productId, JSON.stringify(this.reviews)); // Simpan ulasan untuk productId tertentu
        this.reviewText = ''; // Reset setelah kirim
      }
    }
  }
};
</script>

<style scoped>
textarea {
  width: 100%;
  height: 100px;
  padding: 10px;
  border: 1px solid #ccc;
}
button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  cursor: pointer;
}
</style>
