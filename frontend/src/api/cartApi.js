// src/api/cartApi.js
import axios from './axiosInstance'

// ✅ Tambahkan produk ke keranjang
export const addToCart = async (buyerId, productId, quantity) => {
  return axios.post('/cart/add', {
    buyerId,
    productId,
    quantity
  })
}

// ✅ Ambil semua isi keranjang buyer
export const getCart = async (buyerId) => {
  const res = await axios.get(`/cart/${buyerId}/items`)
  return res.data
}

// ✅ Update jumlah produk di keranjang
export const updateCart = async (buyerId, productId, quantity) => {
  return axios.put(`/cart/${buyerId}/cart/${productId}`, {
    quantity
  })
}

// ✅ Hapus produk dari keranjang
export const removeFromCart = async (buyerId, productId) => {
  return axios.delete(`/cart/${buyerId}/cart/${productId}`)
}

// ✅ Kosongkan seluruh keranjang
export const clearCart = async (buyerId) => {
  return axios.post(`/cart/${buyerId}/clear`)
}

// ✅ Hitung total harga di keranjang
export const getCartTotal = async (buyerId) => {
  const res = await axios.get(`/cart/${buyerId}/total`)
  return res.data
}
