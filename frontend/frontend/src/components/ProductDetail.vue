<template>
  <div>
    <h2>{{ product.name }}</h2>
    <p>{{ product.description }}</p>
    <p>Harga: Rp {{ product.price }}</p>
    <button @click="addToCart">Tambah ke Keranjang</button>
    
    <!-- Tampilkan dummy reviews -->
    <h3>Review Produk:</h3>
    <div v-for="(review, index) in reviews" :key="index">
      <p><strong>{{ review.user }}</strong>: {{ review.text }} (Rating: {{ review.rating }} / 5)</p>
    </div>

    <!-- Komponen Review -->
    <ReviewPage :productId="product.id" />
  </div>
</template>

<script>
import ReviewPage from './ReviewPage.vue'; // Mengimpor komponen ReviewPage.vue

export default {
  components: {
    ReviewPage
  },
  data() {
    return {
      product: {
        id: 1,
        name: 'Miss Dior',
        price: 3000000,
        description: 'A timeless floral fragrance with aldehydes and jasmine notes.',
        image: 'images/parfumeMissDior.jpg'
      },
      reviews: [] // Dummy reviews akan dimasukkan di sini
    };
  },
  methods: {
    addToCart() {
      alert('Produk ditambahkan ke keranjang!');
    },
    generateRandomReview() {
      const randomReviews = [
        { user: 'Alice', text: 'Produk luar biasa! Suka sekali.', rating: 5 },
        { user: 'Bob', text: 'Cukup bagus, namun sedikit mahal.', rating: 3 },
        { user: 'Charlie', text: 'Wangi tahan lama dan elegan.', rating: 4 },
        { user: 'Dave', text: 'Wangi sangat kuat, tidak cocok dengan saya.', rating: 2 },
        { user: 'Eve', text: 'Perfume terbaik yang pernah saya coba!', rating: 5 }
      ];

      // Ambil 3 review acak
      let reviews = [];
      for (let i = 0; i < 3; i++) {
        const randomIndex = Math.floor(Math.random() * randomReviews.length);
        reviews.push(randomReviews[randomIndex]);
      }

      return reviews;
    }
  },
  created() {
    // Generate dan set dummy reviews saat komponen dimuat
    this.reviews = this.generateRandomReview();
  }
};
</script>

<style scoped>
button {
  background-color: #007bff;
  color: white;
  padding: 10px;
  cursor: pointer;
}
</style>
