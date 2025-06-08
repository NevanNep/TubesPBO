<template>
  <div class="admin-page container mt-4">
    <h2>Admin Panel - Kelola Produk</h2>

    <!-- Form tambah / edit produk -->
    <form @submit.prevent="submitForm" class="mb-4">
      <div class="mb-3">
        <label>Brand</label>
        <input v-model="form.brand" type="text" class="form-control" required />
      </div>
      <div class="mb-3">
        <label>Nama Produk</label>
        <input v-model="form.name" type="text" class="form-control" required />
      </div>
      <div class="mb-3">
        <label>Harga</label>
        <input v-model.number="form.price" type="number" class="form-control" required />
      </div>
      <div class="mb-3">
        <label>Harga Lama (Old Price)</label>
        <input v-model.number="form.oldPrice" type="number" class="form-control" />
      </div>
      <div class="mb-3 form-check">
        <input v-model="form.isNew" type="checkbox" class="form-check-input" id="isNewCheck" />
        <label for="isNewCheck" class="form-check-label">Produk Baru (isNew)</label>
      </div>
      <div class="mb-3">
        <label>Diskon (%)</label>
        <input v-model.number="form.discount" type="number" min="0" max="100" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Rating</label>
        <input v-model.number="form.rating" type="number" step="0.1" min="0" max="5" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Gambar URL</label>
        <input v-model="form.image" type="text" class="form-control" placeholder="Masukkan URL gambar" />
      </div>

      <button type="submit" class="btn btn-primary me-2">
        {{ isEditing ? 'Update Produk' : 'Tambah Produk' }}
      </button>
      <button type="button" class="btn btn-secondary" @click="resetForm" v-if="isEditing">
        Batal
      </button>
    </form>

    <!-- Daftar produk -->
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Brand</th>
          <th>Nama</th>
          <th>Harga</th>
          <th>Harga Lama</th>
          <th>Baru</th>
          <th>Diskon</th>
          <th>Rating</th>
          <th>Gambar</th>
          <th>Aksi</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.brand }}</td>
          <td>{{ product.name }}</td>
          <td>{{ formatPrice(product.price) }}</td>
          <td>{{ product.oldPrice ? formatPrice(product.oldPrice) : '-' }}</td>
          <td>
            <span v-if="product.isNew" class="badge bg-success">Baru</span>
            <span v-else>-</span>
          </td>
          <td>{{ product.discount ? product.discount + '%' : '-' }}</td>
          <td>{{ product.rating.toFixed(1) }}</td>
          <td>
            <img :src="product.image" alt="gambar produk" width="80" />
          </td>
          <td>
            <button class="btn btn-warning btn-sm me-2" @click="editProduct(product)">Edit</button>
            <button class="btn btn-danger btn-sm" @click="deleteProduct(product.id)">Hapus</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'

export default {
  name: 'AdminPage',
  setup() {
    const products = ref([
      // Contoh produk awal (bisa diganti dari data lengkap)
      {
        id: 1,
        brand: 'CAROLINA HERRERA',
        name: 'VIP Black Elixir for Men EDP 100 ml',
        price: 1650000,
        oldPrice: 0,
        isNew: true,
        discount: 0,
        rating: 4.7,
        image: 'https://via.placeholder.com/100'
      },
      {
        id: 2,
        brand: 'CAROLINA HERRERA',
        name: 'Bad Boy Elixir EDP 100 ml',
        price: 1930000,
        oldPrice: 0,
        isNew: true,
        discount: 0,
        rating: 4.6,
        image: 'https://via.placeholder.com/100'
      }
    ])

    const form = reactive({
      id: null,
      brand: '',
      name: '',
      price: null,
      oldPrice: 0,
      isNew: false,
      discount: 0,
      rating: 0,
      image: ''
    })

    const isEditing = ref(false)

    function resetForm() {
      form.id = null
      form.brand = ''
      form.name = ''
      form.price = null
      form.oldPrice = 0
      form.isNew = false
      form.discount = 0
      form.rating = 0
      form.image = ''
      isEditing.value = false
    }

    function submitForm() {
      if (isEditing.value) {
        const index = products.value.findIndex(p => p.id === form.id)
        if (index !== -1) {
          products.value[index] = { ...form }
        }
      } else {
        const newId = products.value.length ? Math.max(...products.value.map(p => p.id)) + 1 : 1
        products.value.push({ ...form, id: newId })
      }
      resetForm()
    }

    function editProduct(product) {
      form.id = product.id
      form.brand = product.brand
      form.name = product.name
      form.price = product.price
      form.oldPrice = product.oldPrice
      form.isNew = product.isNew
      form.discount = product.discount
      form.rating = product.rating
      form.image = product.image
      isEditing.value = true
    }

    function deleteProduct(id) {
      if (confirm('Yakin ingin menghapus produk ini?')) {
        products.value = products.value.filter(p => p.id !== id)
      }
    }

    function formatPrice(price) {
      return 'Rp ' + price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    }

    return {
      products,
      form,
      isEditing,
      resetForm,
      submitForm,
      editProduct,
      deleteProduct,
      formatPrice
    }
  }
}
</script>

<style scoped>
.admin-page img {
  border-radius: 8px;
}
</style>
