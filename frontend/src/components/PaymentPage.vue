<template>
  <div>
    <h2>Pembayaran</h2>
    <form @submit.prevent="processPayment">
      <div>
        <label for="paymentMethod">Metode Pembayaran:</label>
        <select id="paymentMethod" v-model="paymentMethod">
          <option value="credit-card">Kartu Kredit</option>
          <option value="paypal">PayPal</option>
          <option value="bank-transfer">Transfer Bank</option>
        </select>
      </div>
      <div>
        <label for="shippingAddress">Alamat Pengiriman:</label>
        <textarea id="shippingAddress" v-model="shippingAddress" required></textarea>
      </div>
      <button type="submit">Konfirmasi Pembayaran</button>
    </form>

    <div v-if="paymentSuccess">
      <h3>Terima kasih telah membeli produk!</h3>
      <p>Silakan beri ulasan untuk produk yang sudah Anda beli:</p>
      <ReviewPage :productId="productId" />
    </div>
  </div>
</template>

<script>
import ReviewPage from './ReviewPage.vue';

export default {
  components: {
    ReviewPage
  },
  data() {
    return {
      paymentMethod: 'credit-card',
      shippingAddress: '',
      paymentSuccess: false, // Status pembayaran
      productId: 1, // Produk yang dibeli (misalnya ID produk)
    };
  },
  methods: {
    processPayment() {
      // Proses pembayaran berhasil
      this.paymentSuccess = true;
      localStorage.setItem('orderStatus', JSON.stringify({ status: 'success', productId: this.productId }));
    }
  }
};
</script>

<style scoped>
textarea {
  width: 100%;
  height: 100px;
  padding: 10px;
  border: 1px solid #ccc;
}
button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  cursor: pointer;
}
</style>
