import axios from 'axios'

const API_BASE = 'http://localhost:8081/api'

export const addToCart = async (buyerId, productId, quantity) => {
  return axios.post(`${API_BASE}/buyer/${buyerId}/cart/${productId}`, {
    quantity
  })
}

export const getCart = async (buyerId) => {
  const res = await axios.get(`${API_BASE}/buyer/${buyerId}/cart`)
  return res.data
}

export const updateCart = async (buyerId, productId, quantity) => {
  return axios.put(`${API_BASE}/buyer/${buyerId}/cart/${productId}`, {
    quantity
  })
}

export const removeFromCart = async (buyerId, productId) => {
  return axios.delete(`${API_BASE}/buyer/${buyerId}/cart/${productId}`)
}
