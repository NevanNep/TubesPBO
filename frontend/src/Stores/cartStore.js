// src/stores/cartStore.js
import { defineStore } from 'pinia'
import axios from 'axios'

function getBuyerId() {
  try {
    return localStorage.getItem('buyerId')
  } catch {
    return null
  }
}

function getAuthHeaders() {
  const token = localStorage.getItem('token')
  return {
    headers: {
      Authorization: `Bearer ${token}`
    }
  }
}

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: []
  }),

  getters: {
    totalItems: state => state.items.reduce((sum, item) => sum + item.quantity, 0),
    totalPrice: state => state.items.reduce((sum, item) => sum + item.quantity * item.price, 0)
  },

  actions: {
    async fetchCart() {
      const buyerId = getBuyerId()
      if (!buyerId) return

      try {
        const response = await axios.get(`/api/buyer/${buyerId}/cart`, getAuthHeaders())
        this.items = response.data
      } catch (err) {
        console.error('Gagal mengambil data cart:', err.response?.data || err.message)
      }
    },

    async addToCart(product, quantity = 1) {
      const buyerId = getBuyerId();
      if (!buyerId) {
        alert('Silakan login terlebih dahulu.');
        return false;
      }
    
      try {
        await axios.post(
          `/api/cart/${buyerId}/add`,
          { productId: product.id, quantity },
          getAuthHeaders()
        );
        await this.fetchCart();
        return true;
      } catch (err) {
        console.error('Error addToCart:', err.response?.data || err.message);
        alert('Gagal menambahkan ke keranjang:\n' + (err.response?.data || err.message));
        return false;
      }
    },
    
    

    async updateQuantity(productId, quantity) {
      const buyerId = getBuyerId()
      if (!buyerId) return

      try {
        await axios.put(
          `/api/buyer/${buyerId}/cart/${productId}`,
          { quantity },
          getAuthHeaders()
        )
        await this.fetchCart()
      } catch (err) {
        console.error('Gagal update quantity:', err.response?.data || err.message)
      }
    },

    async removeItem(productId) {
      const buyerId = getBuyerId()
      if (!buyerId) return

      try {
        await axios.delete(
          `/api/buyer/${buyerId}/cart/${productId}`,
          getAuthHeaders()
        )
        await this.fetchCart()
      } catch (err) {
        console.error('Gagal hapus item:', err.response?.data || err.message)
      }
    },

    clearCart() {
      this.items = []
    }
  }
})
