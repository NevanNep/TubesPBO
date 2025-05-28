<template>
  <div class="card product-card shadow-sm mx-2 my-3 animate_animated animate_fadeInUp">
    <div class="img-wrapper position-relative overflow-hidden">
      <img :src="product.image" :alt="product.name" class="card-img-top product-image" />

      <!-- Badge New -->
      <span class="badge bg-danger position-absolute top-0 start-0 m-2 px-3 py-1 rounded-pill shadow-sm">
        New
      </span>

      <!-- Tombol Add to Cart -->
      <button
        @click="addToCart"
        class="btn btn-cart add-cart-btn position-absolute top-50 start-50 translate-middle"
      >
        <i class="bi bi-cart-plus me-1"></i> Keranjang
      </button>
    </div>

    <div class="card-body text-center">
      <h5 class="card-title product-name">{{ product.name }}</h5>
      <p class="card-text product-description text-truncate">{{ product.description }}</p>
      <p class="text-danger fw-bold product-price">Rp {{ product.price.toLocaleString() }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: ['product'],
  methods: {
    addToCart() {
      this.$emit('add-to-cart', this.product);
    },
  },
};
</script>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css";

.product-card {
  width: 18rem;
  border-radius: 16px;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.product-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 14px 24px rgba(0, 0, 0, 0.2);
}

.img-wrapper {
  height: 200px;
  position: relative;
  background: #f8f9fa;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}

.product-card:hover .product-image {
  transform: scale(1.08);
}

.add-cart-btn {
  opacity: 0;
  transition: all 0.3s ease;
  padding: 0.5rem 1rem;
  font-size: 0.85rem;
  border-radius: 30px;
  pointer-events: none;
  background: #dc3545;
  color: #fff;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(220, 53, 69, 0.5);
}

.img-wrapper:hover .add-cart-btn {
  opacity: 1;
  pointer-events: auto;
  transform: scale(1.05);
}

.add-cart-btn:hover {
  background: #bb2d3b;
}

.product-name {
  font-weight: 700;
  font-size: 1.2rem;
  color: #212529;
}

.product-description {
  color: #6c757d;
  font-size: 0.9rem;
  min-height: 2.5rem;
}

.product-price {
  font-size: 1.1rem;
}
</style>