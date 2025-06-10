<template>
  <div class="admin-page">
    <h2 class="text-2xl font-bold mb-4">Kelola Produk</h2>

    <!-- Form Tambah Produk -->
    <form @submit.prevent="addProduct" class="box-form mb-8 p-4 border rounded bg-gray-50">
      <h3 class="font-semibold mb-2">Tambah Produk Baru</h3>
      <input v-model="newProduct.name" placeholder="Nama Produk" required class="input" />
      <input v-model="newProduct.brand" placeholder="Brand" required class="input" />
      <input v-model="newProduct.price" type="number" placeholder="Harga" required class="input" />
      <input v-model="newProduct.image" placeholder="URL Gambar" required class="input" />
      <button class="btn btn-danger mt-2">Tambah Produk</button>
    </form>

    <!-- Daftar Produk -->
    <div class="produk-grid">
      <div v-for="product in products" :key="product.id" class="produk-card">
        <img :src="product.image" alt="product" class="w-full h-48 object-cover mb-2" />
        <h4 class="font-semibold">{{ product.brand }}</h4>
        <p>{{ product.name }}</p>
        <p class="text-danger fw-semibold">Rp {{ product.price.toLocaleString() }}</p>
        <router-link :to="{ name: 'AdminDetail', params: { id: product.id } }" class="btn btn-outline-dark mt-2">Detail & Edit</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      products: JSON.parse(localStorage.getItem('products') || '[]'),
      newProduct: {
        name: '',
        brand: '',
        price: '',
        image: '',
      }
    };
  },
  methods: {
    addProduct() {
      const id = Date.now();
      this.products.push({ ...this.newProduct, id });
      localStorage.setItem('products', JSON.stringify(this.products));
      this.newProduct = { name: '', brand: '', price: '', image: '' };
    }
  }
};
</script>

<style scoped>
.input {
  display: block;
  margin-bottom: 8px;
  padding: 6px;
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 6px;
}
.produk-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 16px;
}
.produk-card {
  border: 1px solid #ddd;
  padding: 12px;
  border-radius: 8px;
  background: #fff;
  text-align: center;
}
</style>
