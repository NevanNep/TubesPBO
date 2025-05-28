<template>
  <div class="container my-5" style="max-width: 650px;">
    <h2 class="mb-4 text-center fw-bold section-title">Pembayaran</h2>

    <form @submit.prevent="processPayment" v-if="!paymentSuccess" class="card p-4 shadow-sm border-0">
      <div class="mb-3">
        <label for="paymentMethod" class="form-label fw-semibold">Metode Pembayaran:</label>
        <div class="input-group">
          <span class="input-group-text">
            <i :class="paymentIcon[paymentMethod]" class="text-secondary"></i>
          </span>
          <select
            id="paymentMethod"
            v-model="paymentMethod"
            class="form-select"
            required
          >
            <option value="credit-card">Kartu Kredit</option>
            <option value="paypal">PayPal</option>
            <option value="bank-transfer">Transfer Bank</option>
          </select>
        </div>
      </div>

      <div class="mb-3">
        <label for="shippingAddress" class="form-label fw-semibold">Alamat Pengiriman:</label>
        <textarea
          id="shippingAddress"
          v-model="shippingAddress"
          required
          class="form-control"
          rows="4"
          placeholder="Masukkan alamat lengkap..."
        ></textarea>
      </div>

      <button type="submit" class="btn btn-success w-100 d-flex align-items-center justify-content-center gap-2" :disabled="loading">
        <span v-if="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
        {{ loading ? 'Memproses Pembayaran...' : 'Konfirmasi Pembayaran' }}
      </button>
    </form>

    <transition name="fade">
      <div v-if="paymentSuccess" class="text-center mt-5 p-4 shadow rounded bg-white">
        <h3 class="text-success fw-bold mb-3">✅ Terima kasih telah membeli produk!</h3>
        <p class="text-muted">Silakan beri ulasan untuk produk yang sudah Anda beli:</p>
        <ReviewPage :productId="productId" />
      </div>
    </transition>
  </div>
</template>

<script>
import ReviewPage from './ReviewPage.vue';

export default {
  components: {
    ReviewPage,
  },
  data() {
    return {
      paymentMethod: 'credit-card',
      shippingAddress: '',
      paymentSuccess: false,
      loading: false,
      productId: 1,
      paymentIcon: {
        'credit-card': 'bi bi-credit-card',
        paypal: 'bi bi-paypal',
        'bank-transfer': 'bi bi-bank',
      },
    };
  },
  methods: {
    processPayment() {
      this.loading = true;
      setTimeout(() => {
        this.paymentSuccess = true;
        this.loading = false;
        localStorage.setItem(
          'orderStatus',
          JSON.stringify({ status: 'success', productId: this.productId })
        );
      }, 1500);
    },
  },
};
</script>

<style scoped>
@import url('https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css');

.section-title {
  font-size: 2.5rem;
  color: #000000;
  font-family: 'Playfair Display', serif;
  letter-spacing: 1px;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.6s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
//TES