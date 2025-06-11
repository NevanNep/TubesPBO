// src/stores/Product.js
import { defineStore } from 'pinia'
import {
  fetchAllProducts,
  fetchProductById,
  fetchProductsByCategory
} from '@/api/productApi'

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [],
    loading: false,
    error: null
  }),

  getters: {
    getAllProducts: (state) => state.products,

    getProductById: (state) => {
      return (id) => state.products.find((p) => p.id === id)
    },

    getProductsByCategory: (state) => {
      return (category) =>
        state.products.filter((product) =>
          product.category.toLowerCase() === category.toLowerCase()
        )
    },

    getBestSellers: (state) => {
      return state.products
        .filter(
          (product) =>
            product.discount >= 30 || product.isNew || product.price > 2000000
        )
        .slice(0, 8)
    },

    // Opsional: Group by category untuk tampilan ShopByCategories
    groupedByCategory: (state) => {
      const grouped = {
        men: [],
        women: [],
        unisex: []
      }
      state.products.forEach((product) => {
        const cat = product.category?.toLowerCase()
        if (grouped[cat]) grouped[cat].push(product)
      })
      return grouped
    }
  },

  actions: {
    async fetchProducts() {
      this.loading = true
      this.error = null
      try {
        const response = await fetchAllProducts()
        this.products = response
      } catch (err) {
        this.error = 'Gagal memuat produk'
        console.error(err)
      } finally {
        this.loading = false
      }
    },

    async fetchProductDetail(id) {
      try {
        const product = await fetchProductById(id)
        const exists = this.products.find((p) => p.id === product.id)
        if (!exists) this.products.push(product)
      } catch (err) {
        console.error('Gagal memuat detail produk:', err)
      }
    },

    async fetchProductsByCategory(category) {
      this.loading = true
      this.error = null
      try {
        const result = await fetchProductsByCategory(category)
        this.products = result
      } catch (err) {
        this.error = 'Gagal memuat produk kategori'
        console.error(err)
      } finally {
        this.loading = false
      }
    }
  }
})
