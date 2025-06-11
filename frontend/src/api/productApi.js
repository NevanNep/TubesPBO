// src/api/productApi.js
import axios from 'axios'

const API_URL = 'http://localhost:8080/'

// Ambil semua produk
export const fetchAllProducts = async () => {
  try {
    const res = await axios.get(`${API_URL}/products`)
    return res.data
  } catch (err) {
    console.error('Error fetching all products:', err)
    throw err
  }
}

// Ambil produk berdasarkan ID
export const fetchProductById = async (id) => {
  try {
    const res = await axios.get(`${API_URL}/products/${id}`)
    return res.data
  } catch (err) {
    console.error(`Error fetching product by ID (${id}):`, err)
    throw err
  }
}

// Ambil produk berdasarkan kategori
export const fetchProductsByCategory = async (category) => {
  try {
    const res = await axios.get(`${API_URL}/products`, {
      params: { category }
    })
    return res.data
  } catch (err) {
    console.error(`Error fetching products by category (${category}):`, err)
    throw err
  }
}
