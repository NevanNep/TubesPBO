// src/stores/Product.js
import { defineStore } from 'pinia'
import productsData from '@/data/DataProduct'

export const useProductStore = defineStore('product', {
  state: () => ({
    products: productsData
  }),

  getters: {
    getAllProducts: (state) => {
      return [
        ...state.products.men,
        ...state.products.women,
        ...state.products.unisex
      ]
    },

    getProductsByCategory: (state) => {
      return (category) => state.products[category.toLowerCase()] || []
    },

    getBestSellers: (state) => {
      return [
        ...state.products.men,
        ...state.products.women,
        ...state.products.unisex
      ]
        .filter(
          (product) =>
            product.discount >= 30 || product.isNew || product.price > 2000000
        )
        .slice(0, 8)
    },

    // ✅ Tambahkan getter ini untuk ProductDetail.vue
    getProductById: (state) => {
      return (id) => {
        return [
          ...state.products.men,
          ...state.products.women,
          ...state.products.unisex
        ].find((product) => product.id === Number(id))
      }
    }
  }
})
