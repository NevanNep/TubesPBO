<template>
  <div class="product-card" @mouseover="hover = true" @mouseleave="hover = false">
    <!-- Badge Diskon -->
    <div v-if="product.discount > 0" class="discount-badge">-{{ product.discount }}%</div>

    <!-- Gambar Produk -->
    <div class="image-container">
      <img :src="resolvedImage" :alt="product.name" class="product-image" />
    </div>

    <!-- Quick View saat hover -->
    <div v-if="hover" class="quick-view" @click="goToDetail" tabindex="0" role="button">
      QUICK VIEW
    </div>

    <!-- Konten -->
    <div class="product-content">
      <h3 class="brand">{{ product.brand.toUpperCase() }}</h3>
      <p class="product-name">{{ product.name }}</p>

      <!-- Harga -->
      <div class="price-info">
        <span class="original-price">Rp {{ product.price.toLocaleString('id-ID') }}</span>
        <span class="discount-text">-{{ product.discount }}%</span>
        <div class="final-price">Rp {{ discountedPrice.toLocaleString('id-ID') }}</div>
      </div>

      <!-- Rating dan Cart -->
      <div class="rating-cart">
        <div class="rating">
          <span v-for="n in 5" :key="n" class="star" :class="{ filled: n <= Math.round(product.rating) }">★</span>
        </div>
        <svg class="cart-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none"
          stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
          viewBox="0 0 24 24">
          <circle cx="9" cy="21" r="1" />
          <circle cx="20" cy="21" r="1" />
          <path d="M1 1h4l2.68 13.39a2 2 0 002 1.61h9.72a2 2 0 002-1.61L23 6H6" />
        </svg>
      </div>

      <!-- Garis merah -->
      <div class="divider"></div>

      <!-- Status stok -->
      <p class="stock" :class="{ out: product.stock <= 0 }">
        {{ product.stock > 0 ? 'Masih Tersedia' : 'Stok Habis' }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    product: Object
  },
  data() {
    return { hover: false }
  },
  computed: {
    discountedPrice() {
      return Math.round(this.product.price * (1 - this.product.discount / 100));
    },
    resolvedImage() {
      const img = this.product.image || '';
      return img.startsWith('http') || img.startsWith('/') ? img : `/images/${img}`;
    }
  },
  methods: {
    goToDetail() {
      if (this.product?.id != null) {
        this.$router.push({ name: 'ProductDetail', params: { id: this.product.id } });
      }
    }
  }
}
</script>

<style scoped>
.product-card {
  width: 220px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  position: relative;
  font-family: sans-serif;
  cursor: pointer;
}

.discount-badge {
  position: absolute;
  top: 10px;
  right: 10px;
  background: #c62828;
  color: #fff;
  font-size: 12px;
  font-weight: bold;
  border-radius: 50%;
  padding: 6px 4px;
  z-index: 2;
}

.image-container {
  text-align: center;
  padding: 20px;
}

.product-image {
  max-height: 120px;
  object-fit: contain;
}

.quick-view {
  background: #45000D;
  color: white;
  text-align: center;
  font-weight: bold;
  padding: 8px 0;
  font-size: 13px;
  cursor: pointer;
}

.product-content {
  padding: 12px;
}

.brand {
  font-size: 14px;
  font-weight: bold;
  margin: 8px 0 4px;
  color: #000;
}

.product-name {
  font-size: 13px;
  color: #555;
  margin-bottom: 10px;
}

.price-info {
  font-size: 13px;
  margin-bottom: 10px;
}

.original-price {
  text-decoration: line-through;
  color: #999;
  margin-right: 4px;
}

.discount-text {
  color: #c62828;
  font-size: 13px;
}

.final-price {
  font-size: 15px;
  color: #45000D;
  font-weight: bold;
  margin-top: 4px;
}

.rating-cart {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.rating .star {
  color: #ccc;
  font-size: 16px;
}

.rating .star.filled {
  color: #D8AE6C;
}

.cart-icon {
  color: #000;
}

.divider {
  height: 5px;
  background: #45000D;
  margin: 10px 0;
}

.stock {
  font-size: 13px;
  color: #777;
}

.stock.out {
  color: #45000D;
}
</style>
