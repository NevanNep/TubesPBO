<template>
  <div class="cart-page">
    <h2>Keranjang Saya</h2>

    <div v-if="!Array.isArray(cart) || cart.length === 0">
      <p>Keranjang kamu kosong.</p>
    </div>

    <div v-else>
      <table class="cart-table">
        <thead>
          <tr>
            <th>Produk</th>
            <th>Harga</th>
            <th>Jumlah</th>
            <th>Total</th>
            <th>Aksi</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in cart" :key="item.productId">
            <td class="product-cell">
              <img :src="resolveImage(item.image)" :alt="item.name" class="item-image" />
              <span>{{ item.name }}</span>
            </td>
            <td>Rp {{ (item.price || 0).toLocaleString('id-ID') }}</td>
            <td>
              <div class="quantity-control">
                <button @click="item.quantity > 1 && updateItemQty(item, item.quantity - 1)">−</button>
                <input
                  type="number"
                  v-model.number="item.quantity"
                  min="1"
                  @change="updateItem(item)"
                />
                <button @click="updateItemQty(item, item.quantity + 1)">+</button>
              </div>
            </td>
            <td class="item-total">
              Rp {{ (item.price * item.quantity).toLocaleString('id-ID') }}
            </td>
            <td>
              <button class="delete-btn" @click="removeItem(item.productId)">Hapus</button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="cart-summary">
        <div>
          <p>Dipilih: {{ cart.length }} produk</p>
          <p><strong>Subtotal:</strong> Rp {{ totalPrice.toLocaleString('id-ID') }}</p>
        </div>
        <button class="checkout-btn" @click="checkout">Checkout</button>
      </div>
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
    updateItemQty(item, newQty) {
      if (newQty < 1) return
      item.quantity = newQty
      this.updateItem(item)
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
  max-width: 1000px;
  margin: 40px auto;
  padding: 1rem;
  font-family: Poppins;
}

.cart-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 2rem;
}

.cart-table th,
.cart-table td {
  padding: 16px;
  text-align: left;
  border-bottom: 1px solid #eee;
  vertical-align: middle;
}

.cart-table th {
  background-color: #f9f9f9;
  font-weight: 600;
}

.product-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.item-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid #ddd;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 6px;
}

.quantity-control input {
  width: 50px;
  text-align: center;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.quantity-control button {
  background-color: #eee;
  border: none;
  padding: 6px 10px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
}

.quantity-control button:hover {
  background-color: #ddd;
}

.delete-btn {
  background-color: #ffebeb;
  color: #cc0000;
  border: 1px solid #cc0000;
  padding: 6px 10px;
  border-radius: 6px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #ffcccc;
}

.cart-summary {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #eee;
  padding-top: 1rem;
}

.checkout-btn {
  background-color: #45000D;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
}

.checkout-btn:hover {
  background-color: #700014;
}
</style>
