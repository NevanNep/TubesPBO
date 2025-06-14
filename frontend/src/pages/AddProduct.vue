<template>
  <div class="add-product container py-5">
    <h2 class="mb-4 text-center fw-bold">Tambah Produk Baru</h2>
    <form @submit.prevent="addProduct" class="border p-4 shadow-sm bg-white rounded">
      <div class="mb-3">
        <label class="form-label">Nama Produk:</label>
        <input v-model="product.nama" required class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">Brand:</label>
        <input v-model="product.brand" required class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">Harga:</label>
        <input v-model.number="product.price" type="number" required class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">Stok:</label>
        <input v-model.number="product.stock" type="number" required class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">Kategori:</label>
        <input v-model="product.category" required class="form-control" />
      </div>

      <button type="submit" class="btn btn-danger w-100 fw-semibold">
        Tambah
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const product = ref({
  nama: '',
  brand: '',
  price: 0,
  stock: 0,
  category: ''
})

const addProduct = async () => {
  const token = localStorage.getItem('token')

  const productId = 'P' + Date.now()

  try {
    const res = await fetch('http://localhost:8081/api/admin/products', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({
        productId,
        nama: product.value.nama,
        brand: product.value.brand,
        price: product.value.price,
        stock: product.value.stock,
        category: product.value.category
      })
    })

    if (!res.ok) {
      throw new Error('Gagal menambahkan produk.')
    }

    alert('Produk berhasil ditambahkan!')
    router.push('/admin') // kembali ke halaman admin
  } catch (err) {
    alert(`Gagal menambahkan: ${err.message}`)
  }
}
</script>

<style scoped>
h2 {
  font-family: 'Poppins', sans-serif;
}
</style>
