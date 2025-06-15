// src/api/orderApi.js
import axios from './axiosInstance'

// ✅ Checkout order (POST /buyer/{buyerId}/order)
export const placeOrder = async (buyerId, method) => {
  return axios.post(`/buyer/${buyerId}/order`, {
    method
  })
}

// (Opsional) Ambil riwayat order buyer
export const getOrderHistory = async (buyerId) => {
  const res = await axios.get(`/buyer/${buyerId}/orders`)
  return res.data
}
