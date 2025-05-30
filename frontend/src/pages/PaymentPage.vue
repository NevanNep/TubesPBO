<template>
  <div class="payment-page container">
    <h1>Checkout</h1>

    <section class="shipping-address">
      <h2>Alamat Pengiriman</h2>
      <div v-if="selectedAddress" class="address-card">
        <p><strong>{{ selectedAddress.name }}</strong></p>
        <p>{{ selectedAddress.street }}, {{ selectedAddress.city }}, {{ selectedAddress.province }}</p>
        <p>Telp: {{ selectedAddress.phone }}</p>
        <button @click="showAddressForm = true">Ubah Alamat</button>
      </div>
      <div v-else>
        <p>Belum ada alamat pengiriman.</p>
        <button @click="showAddressForm = true">Tambah Alamat</button>
      </div>

      <div v-if="showAddressForm" class="address-form">
        <input v-model="newAddress.name" placeholder="Nama penerima" />
        <input v-model="newAddress.phone" placeholder="Nomor telepon" />
        <input v-model="newAddress.street" placeholder="Alamat lengkap" />
        <input v-model="newAddress.city" placeholder="Kota" />
        <input v-model="newAddress.province" placeholder="Provinsi" />
        <button @click="saveAddress">Simpan Alamat</button>
        <button @click="cancelAddress">Batal</button>
      </div>
    </section>

    <section class="order-summary">
      <h2>Ringkasan Pesanan</h2>
      <ul>
        <li v-for="item in cartItems" :key="item.id" class="order-item">
          <img :src="item.image" alt="gambar produk" class="product-image" />
          <span class="product-name">{{ item.name }} (x{{ item.quantity }})</span>
          <span class="product-price">Rp {{ formatPrice(item.price * item.quantity) }}</span>
        </li>
      </ul>

      <div class="cost-summary">
        <div>
          <span>Subtotal:</span>
          <span>Rp {{ formatPrice(subtotal) }}</span>
        </div>
        <div>
          <span>Ongkos Kirim:</span>
          <span>Rp {{ formatPrice(shippingCost) }}</span>
        </div>
        <div v-if="discount > 0">
          <span>Diskon:</span>
          <span>- Rp {{ formatPrice(discount) }}</span>
        </div>
        <div class="total">
          <strong>Total Bayar:</strong>
          <strong>Rp {{ formatPrice(total) }}</strong>
        </div>
      </div>
    </section>

    <section class="payment-method">
      <h2>Metode Pembayaran</h2>
      <div v-for="method in paymentMethods" :key="method.id" class="payment-option">
        <input
          type="radio"
          :id="method.id"
          v-model="selectedPaymentMethod"
          :value="method.id"
        />
        <label :for="method.id">{{ method.name }}</label>
      </div>
    </section>

    <button
      class="btn-confirm"
      :disabled="!canConfirm"
      @click="confirmPayment"
    >
      Konfirmasi Pembayaran
    </button>

    <div v-if="loading" class="loading-indicator">Memproses pembayaran...</div>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useCartStore } from '@/Stores/cartStore'

export default {
  setup() {
    const cartStore = useCartStore()

    // Contoh alamat tersimpan
    const savedAddresses = ref([
      {
        id: 1,
        name: 'Budi Santoso',
        phone: '08123456789',
        street: 'Jl. Merdeka No. 123',
        city: 'Jakarta',
        province: 'DKI Jakarta'
      }
    ])
    const selectedAddress = ref(savedAddresses.value[0] || null)
    const showAddressForm = ref(false)
    const newAddress = ref({
      name: '',
      phone: '',
      street: '',
      city: '',
      province: ''
    })

    const paymentMethods = ref([
      { id: 'bank_transfer', name: 'Transfer Bank' },
      { id: 'e_wallet', name: 'E-Wallet' },
      { id: 'credit_card', name: 'Kartu Kredit' }
    ])
    const selectedPaymentMethod = ref(null)

    const shippingCost = ref(15000)
    const discount = ref(0)

    const cartItems = computed(() => cartStore.items)
    const subtotal = computed(() => {
      return cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
    })
    const total = computed(() => subtotal.value + shippingCost.value - discount.value)

    function formatPrice(value) {
      return value.toLocaleString('id-ID')
    }

    function saveAddress() {
      if (
        !newAddress.value.name ||
        !newAddress.value.phone ||
        !newAddress.value.street ||
        !newAddress.value.city ||
        !newAddress.value.province
      ) {
        alert('Mohon isi semua data alamat.')
        return
      }
      const newId = savedAddresses.value.length + 1
      savedAddresses.value.push({ id: newId, ...newAddress.value })
      selectedAddress.value = savedAddresses.value[savedAddresses.value.length - 1]

      newAddress.value = { name: '', phone: '', street: '', city: '', province: '' }
      showAddressForm.value = false
    }

    function cancelAddress() {
      showAddressForm.value = false
    }

    const canConfirm = computed(() => {
      return selectedAddress.value && selectedPaymentMethod.value && cartItems.value.length > 0
    })

    const loading = ref(false)

    function confirmPayment() {
      if (!canConfirm.value) return

      loading.value = true

      setTimeout(() => {
        alert('Pembayaran berhasil! Terima kasih telah berbelanja.')
        loading.value = false
      }, 2000)
    }

    return {
      cartItems,
      formatPrice,
      subtotal,
      shippingCost,
      discount,
      total,
      paymentMethods,
      selectedPaymentMethod,
      selectedAddress,
      showAddressForm,
      newAddress,
      saveAddress,
      cancelAddress,
      canConfirm,
      confirmPayment,
      loading
    }
  }
}
</script>

<style scoped>
.payment-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 1rem;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h1, h2 {
  margin-bottom: 1rem;
}

.address-card,
.address-form,
.order-summary,
.payment-method {
  margin-bottom: 2rem;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.address-card p {
  margin: 0.2rem 0;
}

.address-form input {
  display: block;
  margin-bottom: 0.8rem;
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
}

.order-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.8rem;
}

.product-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  margin-right: 12px;
  border-radius: 6px;
}

.product-name {
  flex: 1;
}

.product-price {
  width: 120px;
  text-align: right;
}

.cost-summary div {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.3rem;
}

.cost-summary .total {
  font-size: 1.2rem;
  font-weight: bold;
  border-top: 1px solid #ddd;
  padding-top: 0.5rem;
}

.payment-option {
  margin-bottom: 0.5rem;
}

.btn-confirm {
  background-color: #27ae60;
  color: white;
  font-size: 1.1rem;
  padding: 1rem 2rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s ease;
}

.btn-confirm:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.loading-indicator {
  margin-top: 1rem;
  font-style: italic;
}
</style>
