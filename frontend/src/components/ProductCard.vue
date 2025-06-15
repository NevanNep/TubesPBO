<template>
  <div
    class="product-card"
    @mouseover="hover = true"
    @mouseleave="hover = false"
  >
    <div class="image-container">
      <img :src="resolvedImage" :alt="product.name" class="product-image" />
      <div v-if="product.discount > 0" class="discount-badge">
        -{{ product.discount }}%
      </div>

      <!-- QUICK VIEW -->
      <div
        class="quick-view"
        v-if="hover"
        @click="goToDetail"
        role="button"
        tabindex="0"
        @keydown.enter="goToDetail"
        @keydown.space.prevent="goToDetail"
      >
        DETAILS VIEW
      </div>
    </div>

    <div class="product-content">
      <h3 class="brand">{{ product.brand.toUpperCase() }}</h3>
      <p class="product-name" :title="product.name">{{ product.name }}</p>

      <!-- HARGA -->
      <div class="price-info">
        <div class="price">
          <template v-if="product.discount > 0">
            <span class="original">Rp {{ product.price.toLocaleString('id-ID') }}</span>
            <span class="final-price">Rp {{ discountedPrice.toLocaleString('id-ID') }}</span>
          </template>
          <template v-else>
            <span class="final-price no-discount">Rp {{ product.price.toLocaleString('id-ID') }}</span>
          </template>
        </div>
      </div>

      <!-- ADMIN MODE -->
      <div v-if="isAdmin" class="admin-actions">
        <router-link :to="`/admin/edit/${product.productId}`" class="btn">Edit</router-link>
        <button class="btn danger" @click="deleteProduct">Hapus</button>
      </div>

      <!-- BUYER MODE -->
      <div v-else class="rating-cart-wrapper">
        <div class="rating">
          <span
            v-for="n in 5"
            :key="n"
            class="star"
            :class="{ filled: n <= product.rating }"
          >★</span>
        </div>
        <button
          class="cart-btn"
          @click.stop="addToCart"
          :disabled="product.stock <= 0"
          :aria-disabled="product.stock <= 0"
          :aria-label="`Tambah ${product.name} ke keranjang`"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="cart-icon" viewBox="0 0 24 24" width="24" height="24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="9" cy="21" r="1" />
            <circle cx="20" cy="21" r="1" />
            <path d="M1 1h4l2.68 13.39a2 2 0 002 1.61h9.72a2 2 0 002-1.61L23 6H6" />
          </svg>
        </button>
      </div>

      <div class="divider"></div>
      <p class="stock" :style="{ color: product.stock > 0 ? '#999' : '#c62828' }">
        {{ product.stock > 0 ? 'Masih Tersedia' : 'Stok Habis' }}
      </p>
    </div>
  </div>
</template>

<script>
import { addToCart } from '@/api/cartApi'

export default {
  name: 'ProductCard',
  props: {
    product: {
      type: Object,
      required: true
    },
    quantity: {
      type: Number,
      default: 1
    },
    isAdmin: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      hover: false
    }
  },
  computed: {
    discountedPrice() {
      const price = this.product.price || 0
      const discount = this.product.discount || 0
      return Math.round(price * (1 - discount / 100))
    },
    resolvedImage() {
      const img = this.product.image || ''
      return img.startsWith('http') || img.startsWith('/') ? img : `/images/${img}`
    }
  },
  methods: {
    async addToCart() {
      const buyerId = localStorage.getItem('userId')
      if (!buyerId || !this.product?.productId) {
        this.$toast?.error?.('Anda harus login sebagai buyer untuk menambahkan produk ke keranjang.')
        return
      }
      if (this.product.stock <= 0) {
        this.$toast?.error?.('Stok produk habis.')
        return
      }

      try {
        await addToCart(Number(buyerId), this.product.productId, this.quantity)
        this.$toast?.success?.(`${this.product.name} berhasil ditambahkan ke keranjang.`)
      } catch (err) {
        this.$toast?.error?.('Gagal menambahkan produk ke keranjang.')
        console.error('Cart error:', err)
      }
    },
    deleteProduct() {
      this.$emit('delete-product', this.product.productId)
    },
    goToDetail() {
      if (this.product?.productId != null) {
        this.$router.push({
          name: 'ProductDetail',
          params: { id: this.product.productId }
        })
      }
    }
  }
}
</script>

<style scoped>
.product-card {
  width: 220px;
  flex: 0 0 auto;
  border-radius: 12px;
  background-color: #fff;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.12);
  overflow: hidden;
  position: relative;
  font-family: Poppins, sans-serif;
  display: flex;
  flex-direction: column;
  cursor: pointer;
  transition: box-shadow 0.3s ease;
}
.product-card:hover {
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.18);
}
.image-container {
  position: relative;
  text-align: center;
  padding: 40px;
}
.product-image {
  max-height: 120px;
  object-fit: contain;
  transform: scale(1.6);
}
.discount-badge {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: #b71c1c;
  color: white;
  font-size: 10px;
  font-weight: bold;
  border-radius: 50%;
  padding: 8px 3px;
  z-index: 2;
}
.quick-view {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #45000d;
  color: #f7e7ce;
  font-size: 12px;
  font-weight: bold;
  padding: 8px;
  text-align: center;
  cursor: pointer;
  z-index: 1;
}
.product-content {
  padding: 12px;
  flex: 1;
}
.brand {
  font-size: 13px;
  font-weight: bold;
  color: #000;
  margin-bottom: 4px;
}
.product-name {
  font-size: 13px;
  color: #444;
  margin-bottom: 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.price-info {
  margin-bottom: 8px;
}
.price {
  display: flex;
  flex-direction: column;
}
.original {
  font-size: 13px;
  color: #aaa;
  text-decoration: line-through;
}
.final-price {
  font-size: 15px;
  font-weight: bold;
  color: #750016;
}
.final-price.no-discount {
  margin-top: 4px;
}
.rating-cart-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 4px 0;
}
.rating .star {
  color: #ccc;
  font-size: 16px;
}
.rating .star.filled {
  color: #ebc58a;
}
.cart-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
}
.cart-btn:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}
.cart-icon {
  color: #750016;
}
.divider {
  height: 4px;
  background-color: #45000d;
  margin: 10px 0 6px;
  border-radius: 2px;
}
.stock {
  font-size: 12px;
}
.admin-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-top: 10px;
}
.btn {
  padding: 6px;
  background: #8d8c8a;
  color: white;
  border: none;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  text-decoration: none;
}
.btn:hover {
  background: #4b4b4a;
}
.btn.danger {
  background: #45000d;
}
.btn.danger:hover {
  background: #700014;
}
</style>
