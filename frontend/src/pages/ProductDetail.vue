<template>
  <div v-if="product" class="product-detail-container">
    <!-- Gambar Produk -->
    <div class="image-section">
      <img
        :src="product.image"
        :alt="product.name"
        class="main-image"
      />
      <div class="thumbnail-container">
        <img :src="product.image" class="thumbnail" />
        <img :src="product.image" class="thumbnail" />
        <img :src="product.image" class="thumbnail" />
      </div>
    </div>

    <!-- Informasi Produk -->
    <div class="info-section">
      <h1 class="product-name">{{ product.name }}</h1>
      <h3 class="product-brand">{{ product.brand }}</h3>

      <p class="product-price">
        Rp {{ product.price.toLocaleString('id-ID') }}
        <span
          v-if="product.oldPrice"
          class="old-price"
        >Rp {{ product.oldPrice.toLocaleString('id-ID') }}</span>
      </p>

      <p v-if="product.discount" class="product-discount">
        Discount: {{ product.discount }}%
      </p>

      <div class="product-rating">
        Rating:
        <span v-for="n in 5" :key="n">
          <i
            class="fas"
            :class="n <= product.rating ? 'fa-star' : 'fa-star text-gray-300'"
          ></i>
        </span>
        <span class="rating-value">({{ product.rating }}/5)</span>
      </div>

      <p class="product-description">
        {{ product.description || 'Deskripsi produk belum tersedia.' }}
      </p>

      <div class="quantity-actions">
        <label class="quantity-label">Jumlah:</label>
        <div class="quantity-control">
          <button @click="decreaseQty" class="qty-btn">-</button>
          <span class="qty-value">{{ quantity }}</span>
          <button @click="increaseQty" class="qty-btn">+</button>
        </div>
      </div>

      <div class="action-buttons">
        <button @click="addToCart" class="btn btn-primary">
          <i class="fas fa-shopping-bag"></i>
          Tambah Keranjang
        </button>
        <button class="btn btn-outline">
          <i class="far fa-heart"></i>
          Tambah Wishlist
        </button>
      </div>
    </div>
  </div>

  <div v-else class="not-found">
    Produk tidak ditemukan.
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'
import { useProductStore } from '@/Stores/Product'
import { useCartStore } from '@/Stores/cartStore'

const route = useRoute()
const router = useRouter()
const productStore = useProductStore()
const cartStore = useCartStore()

const product = ref(null)
const quantity = ref(1)

onMounted(async () => {
  const id = parseInt(route.params.id)

  // Jika produk belum di-load, fetch dari API dulu
  if (!productStore.products.length) {
    await productStore.fetchProducts()
  }

  product.value = productStore.getProductById(id)
})

function increaseQty() {
  quantity.value++
}
function decreaseQty() {
  if (quantity.value > 1) quantity.value--
}

function addToCart() {
  if (product.value) {
    const success = cartStore.addToCart(product.value, quantity.value)
    if (!success) {
      router.push('/login')
    } else {
      alert('Produk berhasil ditambahkan ke keranjang!')
    }
  }
}
</script>


<style scoped>
.product-detail-container {
  display: flex;
  max-width: 1100px;
  margin: 2rem auto;
  gap: 2rem;
  padding: 1rem;
}

.image-section {
  flex: 1;
}

.main-image {
  width: 100%;
  max-height: 400px;
  object-fit: contain;
  border-radius: 12px;
  border: 1px solid #ddd;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

.thumbnail-container {
  display: flex;
  gap: 0.5rem;
  margin-top: 0.75rem;
}

.thumbnail {
  width: 80px;
  height: 80px;
  object-fit: contain;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.info-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.product-name {
  font-size: 1.75rem;
  font-weight: bold;
  color: #45000D;
}

.product-brand {
  color: #7B3F00;
  font-size: 1.125rem;
}

.product-price {
  font-size: 1.5rem;
  font-weight: bold;
  color: #45000D;
}

.old-price {
  font-size: 0.875rem;
  color: #999;
  text-decoration: line-through;
  margin-left: 0.5rem;
}

.product-discount {
  color: #D7263D;
  font-weight: 600;
}

.product-rating {
  color: #FFC107;
}

.rating-value {
  font-size: 0.875rem;
  color: #555;
  margin-left: 0.25rem;
}

.product-description {
  color: #555;
  line-height: 1.5;
  margin-top: 1rem;
}

.quantity-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-top: 1.5rem;
}

.quantity-label {
  font-weight: 500;
}

.quantity-control {
  display: flex;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
}

.qty-btn {
  padding: 0.4rem 0.8rem;
  background-color: #eee;
  border: none;
  font-size: 1rem;
  cursor: pointer;
}

.qty-value {
  padding: 0 1rem;
}

.action-buttons {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.btn {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.6rem 1rem;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.95rem;
}

.btn-primary {
  background-color: #D7263D;
  color: white;
  border: none;
}

.btn-primary:hover {
  background-color: #b81e32;
}

.btn-outline {
  border: 2px solid #D7263D;
  color: #D7263D;
  background-color: transparent;
}

.btn-outline:hover {
  background-color: #fef0f1;
}

.not-found {
  text-align: center;
  color: red;
  margin-top: 3rem;
}
</style>
