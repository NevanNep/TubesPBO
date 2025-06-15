<template>
  <div class="cart-page">
    <h2>Keranjang Belanja</h2>
    <div v-if="!Array.isArray(cart) || cart.length === 0">
      <p>Keranjang kamu kosong.</p>
    </div>
    <div v-else class="cart-list">
      <div v-for="item in cart" :key="item.productId" class="cart-item">
        <img :src="resolveImage(item.image)" :alt="item.name" class="item-image" />
        <div class="item-info">
          <h3>{{ item.name }}</h3>
          <p>Rp {{ (item.price || 0).toLocaleString('id-ID') }}</p>
          <div class="quantity-control">
            <label>Jumlah:</label>
            <input
              type="number"
              v-model.number="item.quantity"
              min="1"
              @change="updateItem(item)"
            />
          </div>
          <button @click="removeItem(item.productId)">Hapus</button>
        </div>
      </div>
      <hr />
      <h3>Total: Rp {{ totalPrice.toLocaleString('id-ID') }}</h3>
      <button class="checkout-btn" @click="checkout">Bayar Sekarang</button>
    </div>
  </div>
</template>

<script>
import { getCart, removeFromCart, updateCart } from '@/api/cartApi'
import { placeOrder } from '@/api/orderApi'

export default {
  name: 'CartPage',
  data() {
    return {
      cart: []
    }
  },
  computed: {
    totalPrice() {
      if (!Array.isArray(this.cart)) return 0
      return this.cart.reduce((sum, item) => {
        const price = typeof item.price === 'number' ? item.price : 0
        const qty = typeof item.quantity === 'number' ? item.quantity : 1
        return sum + (price * qty)
      }, 0)
    }
  },
  async mounted() {
    const buyerId = localStorage.getItem('userId')
    if (!buyerId) return

    try {
      const data = await getCart(buyerId)
      console.log('Cart response:', data) // 🔍 debugging
      this.cart = Array.isArray(data) ? data : []
    } catch (err) {
      console.error('Gagal mengambil data keranjang:', err)
      this.cart = []
    }
  },
  methods: {
    async removeItem(productId) {
      const buyerId = localStorage.getItem('userId')
      try {
        await removeFromCart(buyerId, productId)
        this.cart = this.cart.filter(item => item.productId !== productId)
      } catch (err) {
        console.error('Gagal menghapus produk:', err)
      }
    },
    async updateItem(item) {
      const buyerId = localStorage.getItem('userId')
      try {
        await updateCart(buyerId, item.productId, item.quantity)
      } catch (err) {
        console.error('Gagal update jumlah:', err)
      }
    },
    async checkout() {
      const buyerId = localStorage.getItem('userId')
      if (!buyerId) return alert('Login terlebih dahulu')

      const method = prompt('Masukkan metode pembayaran (misal: VISA)')
      if (!method) return

      try {
        await placeOrder(buyerId, method)
        alert('Pembayaran berhasil!')
        this.cart = []
      } catch (err) {
        console.error('Gagal memproses pembayaran:', err)
        alert('Pembayaran gagal')
      }
    },
    resolveImage(img) {
      if (!img) return '/images/default.jpeg'
      if (img.startsWith('http') || img.startsWith('/')) return img
      return `/images/${img}`
    }
  }
}
</script>

<style scoped>
.cart-page {
  max-width: 800px;
  margin: auto;
  padding: 2rem;
}

.cart-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.cart-item {
  display: flex;
  align-items: flex-start;
  gap: 1rem;
  padding: 1rem;
  border: 1px solid #eee;
  border-radius: 10px;
}

.item-image {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
}

.item-info {
  flex: 1;
}

.quantity-control input {
  width: 60px;
  padding: 4px;
}

.checkout-btn {
  margin-top: 1rem;
  padding: 10px 20px;
  background-color: #45000D;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.checkout-btn:hover {
  background-color: #700014;
}
</style>
