// src/api/productApi.js
import axios from './axiosInstance'

export const fetchAllProducts = async () => {
  try {
    const res = await axios.get('/products')
    return res.data
  } catch (err) {
    console.error('Error fetching all products:', err)
    throw err
  }
}

export const fetchProductById = async (id) => {
  try {
    const res = await axios.get(`/products/${id}`)
    return res.data
  } catch (err) {
    console.error(`Error fetching product by ID (${id}):`, err)
    throw err
  }
}

export const fetchProductsByCategory = async (category) => {
  try {
    const res = await axios.get('/products', {
      params: { category }
    })
    return res.data
  } catch (err) {
    console.error(`Error fetching products by category (${category}):`, err)
    throw err
  }
}

export const deleteProductById = async (productId) => {
  try {
    const res = await axios.delete(`/admin/products/${productId}`)
    return res.data
  } catch (err) {
    console.error(`Error deleting product (${productId}):`, err)
    throw err
  }
}
