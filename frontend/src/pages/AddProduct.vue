<template>
  <div class="add-product">
    <h2>Tambah Produk Baru</h2>
    <form @submit.prevent="submitProduct">
      <input v-model="productId" type="text" placeholder="Product ID (misal: P0010)" required />
      <input v-model="name" type="text" placeholder="Nama Produk" required />
      <input v-model="brand" type="text" placeholder="Brand" required />
      <input v-model="category" type="text" placeholder="Kategori" required />
      <input v-model="price" type="number" placeholder="Harga" required />
      <input v-model="stock" type="number" placeholder="Stok" required />
      <input v-model="discount" type="number" placeholder="Diskon (%)" />
      <input v-model="image" type="text" placeholder="URL Gambar" required />
      <input v-model="rating" type="number" step="0.1" placeholder="Rating" />

      <button type="submit">Tambah Produk</button>
    </form>

    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <p v-if="successMessage" class="success">{{ successMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from '@/api/axiosInstance'
import { useRouter } from 'vue-router'

const router = useRouter()

const productId = ref('')
const name = ref('')
const brand = ref('')
const category = ref('')
const price = ref(null)
const stock = ref(null)
const discount = ref(0)
const image = ref('')
const rating = ref(0)

const errorMessage = ref('')
const successMessage = ref('')

const submitProduct = async () => {
  try {
    await axios.post('/admin/products', {
      productId: productId.value,
      name: name.value,
      brand: brand.value,
      category: category.value,
      price: price.value,
      stock: stock.value,
      discount: discount.value,
      image: image.value,
      rating: rating.value
    })

    successMessage.value = 'Produk berhasil ditambahkan!'
    errorMessage.value = ''
    setTimeout(() => router.push('/admin'), 1000)
  } catch (err) {
    console.error('Gagal menambahkan produk:', err)
    errorMessage.value = 'Gagal menambahkan produk.'
    successMessage.value = ''
  }
}
</script>

<style scoped>
.add-product {
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
