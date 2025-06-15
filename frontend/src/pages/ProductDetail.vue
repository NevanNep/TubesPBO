<template>
  <div v-if="product" class="product-detail-container">
    <!-- Image Section -->
    <div class="image-section">
      <img :src="product.image" :alt="product.name" class="main-image" />
      <div class="thumbnail-container">
        <img :src="product.image" class="thumbnail" alt="Thumbnail" v-for="i in 3" :key="i" />
      </div>
    </div>

    <!-- Info Section -->
    <div class="info-section">
      <!-- Brand & Name -->
      <h3 class="product-brand">{{ product.brand }}</h3>
      <h1 class="product-name">{{ product.name }}</h1>

      <!-- Harga & Diskon -->
      <p class="product-price">
        Rp {{ discountedPrice.toLocaleString('id-ID') }}
        <span v-if="product.discount" class="old-price">
          Rp {{ product.price.toLocaleString('id-ID') }}
        </span>
      </p>
      <p v-if="product.discount" class="product-discount">
        Diskon: {{ product.discount }}%
      </p>

      <!-- Rating -->
      <div class="product-rating">
        Rating:
        <span v-for="n in 5" :key="n">
          <i class="fas" :class="n <= product.rating ? 'fa-star' : 'fa-star text-gray-300'"></i>
        </span>
        <span class="rating-value">({{ product.rating }}/5)</span>
      </div>

      <!-- Deskripsi -->
      <p class="product-description">
        {{ product.description || 'Deskripsi produk belum tersedia.' }}
      </p>

      <!-- Jumlah dan Tombol -->
      <div class="quantity-actions">
        <label class="quantity-label">Jumlah:</label>
        <div class="quantity-control">
          <button @click="decreaseQty" class="qty-btn">-</button>
          <span class="qty-value">{{ quantity }}</span>
          <button @click="increaseQty" class="qty-btn">+</button>
        </div>
      </div>

      <div class="action-buttons">
        <button @click="addToCartHandler" class="btn btn-primary">
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

  <div v-else class="not-found">Produk tidak ditemukan.</div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '@/api/axiosInstance'
import { addToCart } from '@/api/cartApi'

const route = useRoute()
const router = useRouter()

const product = ref(null)
const quantity = ref(1)

const loadProduct = async (id) => {
  try {
    const { data } = await axios.get(`/products/${id}`)
    product.value = data
  } catch (error) {
    alert('Produk tidak ditemukan.')
    router.push('/')
  }
}

const discountedPrice = computed(() => {
  if (!product.value?.discount || !product.value?.price) return product.value?.price
  return product.value.price - (product.value.price * product.value.discount / 100)
})

const increaseQty = () => quantity.value++
const decreaseQty = () => {
  if (quantity.value > 1) quantity.value--
}

const addToCartHandler = async () => {
  const buyerId = localStorage.getItem('userId')
  if (!buyerId || !product.value?.productId) {
    alert('Anda harus login sebagai buyer untuk menambahkan produk ke keranjang.')
    return
  }
  if (product.value.stock <= 0) {
    alert('Stok produk habis.')
    return
  }

  try {
    await addToCart(Number(buyerId), product.value.productId, quantity.value)
    alert(`${product.value.name} berhasil ditambahkan ke keranjang.`)
  } catch (err) {
    console.error('Cart error:', err)
    alert('Gagal menambahkan produk ke keranjang.')
  }
}

onMounted(() => {
  loadProduct(route.params.id)
})

watch(() => route.params.id, (newId) => {
  loadProduct(newId)
})
</script>

<style scoped>
.product-detail-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: flex-start;
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  gap: 2rem;
  font-family: 'Poppins', sans-serif;
}

.image-section {
  flex: 1;
  min-width: 300px;
}

.main-image {
  width: 100%;
  height: auto;
  border-radius: 12px;
  object-fit: cover;
}

.thumbnail-container {
  margin-top: 1rem;
  display: flex;
  gap: 0.5rem;
}

.thumbnail {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #ccc;
}

.info-section {
  flex: 1.2;
  min-width: 300px;
}

.product-brand {
  font-size: 1.2rem;
  font-weight: 600;
  color: #45000D;
  margin-bottom: 0.25rem;
}

.product-name {
  font-size: 1.75rem;
  font-weight: bold;
  color: #45000d;
  margin-bottom: 1rem;
}

.product-price {
  font-size: 1.5rem;
  font-weight: 600;
  color: #45000D;
}

.old-price {
  text-decoration: line-through;
  color: #999;
  margin-left: 0.5rem;
}

.product-discount {
  color: #e60023;
  font-weight: 600;
  margin-bottom: 1rem;
}

.product-rating {
  margin: 1rem 0;
  color: #ebc58a;
  font-size: 1rem;
}

.rating-value {
  margin-left: 0.5rem;
  color: #333;
}

.product-description {
  margin: 1rem 0;
  line-height: 1.6;
  color: #444;
}

.quantity-actions {
  margin: 1rem 0;
}

.quantity-label {
  font-weight: 500;
  margin-right: 0.5rem;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.qty-btn {
  width: 32px;
  height: 32px;
  font-size: 1.25rem;
  background: #eee;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.qty-value {
  font-size: 1rem;
  min-width: 24px;
  text-align: center;
}

.action-buttons {
  margin-top: 1.5rem;
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.btn {
  padding: 0.75rem 1.5rem;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  border: none;
}

.btn-primary {
  background: #45000d;
  color: white;
}

.btn-outline {
  background: transparent;
  border: 2px solid #45000d;
  color: #45000d;
}

.btn-primary:hover {
  background: #700014;
}

.not-found {
  padding: 3rem;
  text-align: center;
  font-size: 1.2rem;
  color: #999;
}
</style>
