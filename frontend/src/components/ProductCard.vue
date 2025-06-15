<template>
  <div class="product-card" @mouseover="hover = true" @mouseleave="hover = false">
    <div class="image-container">
      <img :src="resolvedImage" :alt="product.name" class="product-image" />
      <div v-if="product.discount > 0" class="discount-badge">-{{ product.discount }}%</div>
      <div 
        class="quick-view" 
        v-if="hover" 
        @click="goToDetail"
        role="button"
        tabindex="0"
        @keydown.enter="goToDetail"
        @keydown.space.prevent="goToDetail"
      >QUICK VIEW</div>
    </div>

    <div class="product-content">
      <h3 class="brand">{{ product.brand.toUpperCase() }}</h3>
      <p class="product-name" :title="product.name">{{ product.name }}</p>

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

      <div class="rating-cart-wrapper">
        <div class="rating">
          <span v-for="n in 5" :key="n" class="star" :class="{ filled: n <= product.rating }">★</span>
        </div>
        <!-- Ikon cart sebagai tombol -->
        <button 
          class="cart-btn" 
          @click.stop="addToCart"
          :disabled="product.stock <= 0"
          :aria-disabled="product.stock <= 0"
          :aria-label="`Tambah ${product.name} ke keranjang`"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor"
            stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="cart-icon" viewBox="0 0 24 24">
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
  props: {
    product: {
      type: Object,
      required: true
    },
    quantity: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      hover: false
    }
  },
  computed: {
    discountedPrice() {
      if (!this.product || typeof this.product.price !== 'number') return 0;
      return this.product.discount > 0
        ? Math.round(this.product.price * (1 - this.product.discount / 100))
        : this.product.price;
    },
    resolvedImage() {
      const img = this.product.image || '';
      if (img.startsWith('http') || img.startsWith('/')) return img;
      return `/images/${img}`;
    }
  },
  methods: {
    async addToCart() {
      const buyerId = localStorage.getItem('userId');
      if (!buyerId || !this.product?.productId) {
        this.$toast?.error?.('Anda harus login sebagai buyer untuk menambahkan produk ke keranjang.');
        return;
      }

      if (this.product.stock <= 0) {
        this.$toast?.error?.('Stok produk habis.');
        return;
      }

      try {
        await addToCart(Number(buyerId), this.product.productId, this.quantity);
        this.$toast?.success?.(`${this.product.name} berhasil ditambahkan ke keranjang.`);
      } catch (err) {
        this.$toast?.error?.('Gagal menambahkan produk ke keranjang.');
        console.error('Cart error:', err);
      }
    },
    goToDetail() {
      if (this.product?.productId != null) {
        this.$router.push({
          name: 'ProductDetail',
          params: { id: this.product.productId },
        });
      }
    }
  }
}
</script>

<style scoped>
/* Tetap gunakan style CSS milikmu yang sebelumnya */
</style>
