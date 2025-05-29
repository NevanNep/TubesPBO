// src/stores/cartStore.js
import { defineStore } from 'pinia'

function loadCartItems() {
  try {
    const stored = localStorage.getItem('cartItems')
    return stored ? JSON.parse(stored) : []
  } catch (error) {
    console.error('Gagal parsing cartItems dari localStorage:', error)
    return []
  }
}

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: loadCartItems()
  }),

  getters: {
    totalItems: state => state.items.reduce((sum, item) => sum + item.quantity, 0),
    totalPrice: state => state.items.reduce((sum, item) => sum + item.quantity * item.price, 0)
  },

  actions: {
    saveToLocalStorage() {
      localStorage.setItem('cartItems', JSON.stringify(this.items))
    },

    addToCart(product) {
      // Cek login dulu
      const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'
      if (!isLoggedIn) {
        alert('Silakan login terlebih dahulu sebelum menambah produk ke keranjang.')
        // Karena ini di store, tidak bisa akses this.$router langsung.
        // Jadi, bisa pakai event atau return false supaya komponen yang panggil bisa handle redirect
        return false
      }

      const existing = this.items.find(item => item.id === product.id)
      if (existing) {
        existing.quantity++
      } else {
        this.items.push({ ...product, quantity: 1 })
      }
      this.saveToLocalStorage()
      return true
    },

    decreaseQuantity(productId) {
      const index = this.items.findIndex(item => item.id === productId)
      if (index !== -1) {
        if (this.items[index].quantity > 1) {
          this.items[index].quantity--
        } else {
          this.items.splice(index, 1)
        }
        this.saveToLocalStorage()
      }
    },

    removeItem(productId) {
      this.items = this.items.filter(item => item.id !== productId)
      this.saveToLocalStorage()
    },

    clearCart() {
      this.items = []
      this.saveToLocalStorage()
    }
  }
})
