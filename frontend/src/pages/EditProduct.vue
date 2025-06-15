<template>
  <div class="edit-product">
    <h2>Edit Produk</h2>
    <form v-if="product" @submit.prevent="updateProduct">
      <input v-model="product.name" type="text" placeholder="Nama Produk" required />
      <input v-model="product.brand" type="text" placeholder="Brand" required />
      <input v-model="product.category" type="text" placeholder="Kategori" required />
      <input v-model.number="product.price" type="number" placeholder="Harga" required />
      <input v-model.number="product.stock" type="number" placeholder="Stok" required />
      <input v-model.number="product.discount" type="number" placeholder="Diskon (%)" />
      <input v-model="product.image" type="text" placeholder="URL Gambar" required />
      <input v-model.number="product.rating" type="number" step="0.1" placeholder="Rating" />

      <button type="submit">Simpan Perubahan</button>
    </form>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <p v-if="successMessage" class="success">{{ successMessage }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '@/api/axiosInstance'

const route = useRoute()
const router = useRouter()

const product = ref(null)
const errorMessage = ref('')
const successMessage = ref('')

// Ambil data produk berdasarkan ID dari URL
const fetchProduct = async () => {
  try {
    const res = await axios.get(`/products/${route.params.id}`)
    product.value = res.data
  } catch (err) {
    errorMessage.value = 'Gagal mengambil data produk.'
  }
}

// Update produk (PUT)
const updateProduct = async () => {
  try {
    // 🛠 Pastikan ID dikirim kembali ke backend!
    await axios.put(`/admin/products/${route.params.id}`, {
      ...product.value,
      id: route.params.id
    })
    successMessage.value = 'Produk berhasil diperbarui!'
    errorMessage.value = ''
    setTimeout(() => router.push('/admin'), 1500)
  } catch (err) {
    console.error('Update error:', err)
    errorMessage.value = 'Gagal memperbarui produk.'
    successMessage.value = ''
  }
}

onMounted(fetchProduct)
</script>

<style scoped>
.edit-product {
  max-width: 600px;
  margin: auto;
  padding: 2rem;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

input {
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
}

button {
  background: #45000D;
  color: white;
  padding: 0.75rem;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
}

button:hover {
  background: #700014;
}

.error {
  color: red;
  margin-top: 1rem;
}

.success {
  color: green;
  margin-top: 1rem;
}
</style>
