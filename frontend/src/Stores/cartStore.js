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

    addToCart(product, quantity = 1) {
      // Cek login dulu
      const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'
      if (!isLoggedIn) {
        alert('Silakan login terlebih dahulu sebelum menambah produk ke keranjang.')
        return false
      }

      const existing = this.items.find(item => item.id === product.id)
      if (existing) {
        existing.quantity += quantity
      } else {
        this.items.push({ ...product, quantity })
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
          // Pakai splice agar reaktif
          this.items.splice(index, 1)
        }
        this.saveToLocalStorage()
      }
    },

    removeItem(productId) {
      const index = this.items.findIndex(item => item.id === productId)
      if (index !== -1) {
        // Pakai splice agar reaktif
        this.items.splice(index, 1)
        this.saveToLocalStorage()
      }
    },

    clearCart() {
      this.items = []
      this.saveToLocalStorage()
    }
  }
})
