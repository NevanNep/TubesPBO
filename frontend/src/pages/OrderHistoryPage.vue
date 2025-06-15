<!-- src/pages/OrderHistoryPage.vue -->
<template>
  <div class="history-page">
    <h2>Riwayat Transaksi</h2>

    <div v-if="orders.length === 0">
      <p>Belum ada transaksi yang tercatat.</p>
    </div>

    <div v-else class="order-list">
      <div v-for="order in orders" :key="order.orderId" class="order-card">
        <h3>ID Pesanan: {{ order.orderId }}</h3>
        <p><strong>Tanggal:</strong> {{ formatDate(order.orderDate) }}</p>
        <p><strong>Status:</strong> {{ order.status }}</p>
        <p><strong>Total:</strong> Rp {{ order.totalAmount.toLocaleString('id-ID') }}</p>

        <ul class="item-list">
          <li v-for="item in order.items" :key="item.productId">
            {{ item.productName }} x{{ item.quantity }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '@/api/axiosInstance'

const orders = ref([])

const fetchOrders = async () => {
  try {
    const buyerId = localStorage.getItem('userId')
    if (!buyerId) return
    const res = await axios.get(`/buyer/${buyerId}/orders`)
    orders.value = res.data
  } catch (err) {
    console.error('Gagal mengambil riwayat transaksi:', err)
  }
}

const formatDate = (dateStr) => {
  const d = new Date(dateStr)
  return d.toLocaleString('id-ID', {
    dateStyle: 'medium',
    timeStyle: 'short',
  })
}

onMounted(fetchOrders)
</script>

<style scoped>
.history-page {
  max-width: 800px;
  margin: auto;
  padding: 2rem;
  font-family: 'Poppins', sans-serif;
}

.order-card {
  border: 1px solid #ddd;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
  background: white;
}

.item-list {
  padding-left: 1rem;
  margin-top: 0.5rem;
  color: #333;
}

.item-list li {
  margin-bottom: 0.3rem;
}
</style>
