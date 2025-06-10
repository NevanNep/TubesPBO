<template>
  <div class="add-product">
    <h2>Tambah Produk Baru</h2>
    <form @submit.prevent="addProduct">
      <label>Nama Produk:</label>
      <input v-model="product.name" />

      <label>Harga:</label>
      <input v-model="product.price" type="number" />

      <!-- Tambah form lainnya -->

      <button type="submit">Tambah</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const product = ref({ name: '', price: 0 })

const addProduct = () => {
  // Simpan ke server
  console.log('Ditambahkan:', product.value)
  router.push('/admin')
}
</script>
