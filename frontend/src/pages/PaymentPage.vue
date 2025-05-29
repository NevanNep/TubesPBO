<template>
  <div class="container my-5" style="max-width: 650px;">
    <h2 class="mb-4 text-center fw-bold section-title">Pembayaran</h2>

    <!-- Form pembayaran hanya tampil jika belum bayar -->
    <form @submit.prevent="processPayment" v-if="!orderShipped" class="card p-4 shadow-sm border-0">
      <!-- Pilihan metode pembayaran -->
      <div class="mb-3">
        <label for="paymentMethod" class="form-label fw-semibold">Metode Pembayaran:</label>
        <div class="input-group">
          <span class="input-group-text">
            <i :class="paymentIcon[paymentMethod]" class="text-secondary"></i>
          </span>
          <select id="paymentMethod" v-model="paymentMethod" class="form-select" required>
            <option value="credit-card">Kartu Kredit</option>
            <option value="paypal">PayPal</option>
            <option value="bank-transfer">Transfer Bank</option>
          </select>
        </div>
      </div>

      <!-- Jika Transfer Bank, pilih bank -->
      <div v-if="paymentMethod === 'bank-transfer'" class="mb-3">
        <label for="selectedBank" class="form-label fw-semibold">Pilih Bank:</label>
        <select id="selectedBank" v-model="selectedBank" class="form-select" required>
          <option disabled value="">-- Pilih Bank --</option>
          <option value="BCA">Bank BCA</option>
          <option value="BNI">Bank BNI</option>
          <option value="Mandiri">Bank Mandiri</option>
          <option value="BRI">Bank BRI</option>
        </select>
      </div>

      <!-- Alamat pengiriman -->
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

      <!-- Tombol konfirmasi -->
      <button
        type="submit"
        class="btn btn-danger w-100 d-flex align-items-center justify-content-center gap-2"
        :disabled="loading || (paymentMethod === 'bank-transfer' && !selectedBank)"
        :title="loading ? 'Sedang diproses...' : 'Lanjutkan Pembayaran'"
      >
        <span v-if="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
        {{ loading ? 'Memproses Pembayaran...' : 'Konfirmasi Pembayaran' }}
      </button>
    </form>

    <!-- Pesan pesanan dikirim -->
    <transition name="fade">
      <div v-if="orderShipped && !showReview" class="text-center mt-5 p-4 shadow rounded bg-white">
        <h3 class="text-success fw-bold mb-3">✅ Terima kasih telah membeli produk!</h3>
        <p class="text-muted">Pesanan Anda sedang dikirim...</p>
      </div>
    </transition>

    <!-- Form review muncul setelah produk sampai -->
    <transition name="fade">
      <div v-if="showReview" class="text-center mt-5 p-4 shadow rounded bg-white">
        <p class="text-muted">Produk telah sampai. Silakan beri ulasan:</p>
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
      selectedBank: '',
      shippingAddress: '',
      loading: false,
      orderShipped: false,
      showReview: false,
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
        // Selesai bayar, pesanan dikirim
        this.orderShipped = true;
        this.loading = false;
        localStorage.setItem('orderStatus', JSON.stringify({ status: 'success', productId: this.productId }));

        // Simulasikan barang sampai 5 detik kemudian
        setTimeout(() => {
          this.showReview = true;
        }, 5000);
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
