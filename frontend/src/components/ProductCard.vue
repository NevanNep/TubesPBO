<template>
  <div class="product-card" @mouseover="hover = true" @mouseleave="hover = false">
    <div class="image-container">
      <img :src="product.image" :alt="product.name" class="product-image" />

      <div v-if="product.discount > 0" class="discount-badge">
        -{{ product.discount }}%
      </div>

      <div 
        class="quick-view" 
        v-if="hover" 
        @click="goToDetail"
        role="button"
        tabindex="0"
        @keydown.enter="goToDetail"
        @keydown.space.prevent="goToDetail"
      >
        QUICK VIEW
      </div>
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
          :aria-label="`Tambah ${product.name} ke keranjang`"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
            class="cart-icon"
            viewBox="0 0 24 24"
          >
            <circle cx="9" cy="21" r="1" />
            <circle cx="20" cy="21" r="1" />
            <path d="M1 1h4l2.68 13.39a2 2 0 002 1.61h9.72a2 2 0 002-1.61L23 6H6" />
          </svg>
        </button>
      </div>

      <div class="divider"></div>
      <p class="stock">Masih Tersedia</p>
    </div>
  </div>
</template>

<script>
import { useCartStore } from '@/Stores/cartStore'; // pastikan path-nya sesuai
import { mapStores } from 'pinia';

export default {
  props: {
    product: {
      type: Object,
      required: true,
      default: () => ({
        id: null,
        brand: '',
        name: '',
        image: '',
        price: 0,
        discount: 0,
        rating: 0,
        stock: 0,
        category: '',
      }),
    },
  },
  data() {
    return {
      hover: false,
    };
  },
  computed: {
    ...mapStores(useCartStore),
    discountedPrice() {
      if (!this.product || typeof this.product.price !== 'number') return 0;
      return this.product.discount > 0
        ? Math.round(this.product.price * (1 - this.product.discount / 100))
        : this.product.price;
    },
  },
  methods: {
    async addToCart() {
      if (!this.product) return;

      const success = await this.cartStore.addToCart(this.product, 1);

      if (success) {
        this.$toast?.success?.(`${this.product.name} berhasil ditambahkan ke keranjang`);
      } else {
        this.$toast?.error?.(`Gagal menambahkan produk ke keranjang`);
      }
    },
    goToDetail() {
      if (this.product?.id != null) {
        this.$router.push({
          name: 'ProductDetail',
          params: { id: this.product.id },
        });
      }
    },
  },
};
</script>



<style scoped>
.product-card {
  width: 220px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.image-container {
  position: relative;
  height: 240px;
  padding: 0;
  background: transparent;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 0;
  box-shadow: none;
}

.discount-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  width: 32px;
  height: 32px;
  background: #c62828;
  color: white;
  font-size: 0.65rem;
  font-weight: 700;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.quick-view {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: #45000D;
  color: #F7E7CE;
  text-align: center;
  padding: 8px 0;
  font-weight: 700;
  font-size: 0.85rem;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  opacity: 0;
  transform: translateY(100%);
  transition: opacity 0.25s ease, transform 0.25s ease;
}
.image-container:hover .quick-view {
  opacity: 1;
  transform: translateY(0);
}

.product-content {
  padding: 1rem;
}

.brand {
  font-size: 0.75rem;
  font-weight: 700;
  color: #222;
  letter-spacing: 0.05em;
  margin-bottom: 4px;
  text-transform: uppercase;
}

.product-name {
  font-size: 0.9rem;
  font-weight: 500;
  color: #45000D;
  margin-bottom: 8px;
  line-height: 1.2;
  height: 2.4rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.price-info {
  display: flex;
  flex-direction: column;
  margin-top: 0.5rem;
}

.price {
  display: flex;
  flex-direction: row;
  align-items: baseline;
  gap: 6px;
}

.original {
  font-size: 0.75rem;
  color: #999;
  text-decoration: line-through;
}

.final-price {
  font-size: 1rem;
  font-weight: 700;
  color: #45000D;
}

.final-price.no-discount {
  color: #222; /* Warna gelap jika tanpa diskon */
}

/* Wrapper rating dan cart agar sejajar horizontal */
.rating-cart-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
}

.star {
  color: #ddd;
  font-size: 1rem;
}

.star.filled {
  color: #EDBA6B;
}

.cart-btn {
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cart-icon {
  width: 22px;
  height: 22px;
  stroke: #45000D;
  transition: stroke 0.3s ease;
}

.cart-btn:hover .cart-icon {
  stroke: #9b1c1c;
}

.divider {
  height: 4px;
  background: #45000D;
  margin: 10px 0 5px;
  border-radius: 2px;
}

.stock {
  color: #999;
  font-size: 0.8rem;
}
</style>
