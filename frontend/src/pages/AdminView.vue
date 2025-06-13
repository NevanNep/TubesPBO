<template>
  <div class="container py-4">
    <h2 class="text-center fw-bold mb-4" style="font-size: 2.5rem;">🛒 Kelola Produk</h2>

    <!-- Form Tambah Produk -->
    <div class="card shadow-sm mb-5">
      <div class="card-body">
        <h5 class="card-title fw-semibold mb-3">Tambah Produk Baru</h5>
        <form @submit.prevent="addProduct" class="row g-3">
          <div class="col-md-4">
            <input v-model="newProduct.productId" type="text" class="form-control" placeholder="Product ID (P001)" required />
          </div>
          <div class="col-md-4">
            <input v-model="newProduct.nama" type="text" class="form-control" placeholder="Nama Produk" required />
          </div>
          <div class="col-md-4">
            <input v-model="newProduct.brand" type="text" class="form-control" placeholder="Brand" required />
          </div>
          <div class="col-md-4">
            <input v-model="newProduct.category" type="text" class="form-control" placeholder="Kategori" required />
          </div>
          <div class="col-md-4">
            <input v-model="newProduct.price" type="number" class="form-control" placeholder="Harga (Rp)" required />
          </div>
          <div class="col-md-4">
            <input v-model="newProduct.stock" type="number" class="form-control" placeholder="Stok" required />
          </div>
          <div class="col-12 d-flex justify-content-end">
            <button class="btn btn-danger px-4 fw-semibold">Tambah</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Daftar Produk -->
    <div class="row">
      <div v-for="product in products" :key="product.productId" class="col-md-4 mb-4 d-flex align-items-stretch">
        <div class="card shadow-sm h-100">
          <img :src="product.image || 'https://via.placeholder.com/300x200?text=No+Image'" alt="Product Image" class="card-img-top" style="height: 200px; object-fit: cover;" />
          <div class="card-body d-flex flex-column justify-content-between">
            <div>
              <h5 class="fw-bold">{{ product.nama }}</h5>
              <p class="text-muted mb-1">{{ product.brand }} | {{ product.category }}</p>
              <p class="fw-semibold">Rp {{ formatPrice(product.price) }} (Stok: {{ product.stock }})</p>
            </div>
            <div class="mt-3">
              <router-link :to="{ name: 'AdminDetail', params: { id: product.productId } }" class="btn btn-sm btn-outline-primary me-2">Edit</router-link>
              <button class="btn btn-sm btn-outline-danger" @click="deleteProduct(product.productId)">Hapus</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="products.length === 0" class="text-center text-muted mt-5">
      Belum ada produk yang ditambahkan.
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newProduct: {
        productId: '',
        nama: '',
        brand: '',
        category: '',
        price: '',
        stock: ''
      },
      products: []
    };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    formatPrice(value) {
      return parseInt(value).toLocaleString('id-ID');
    },
    async fetchProducts() {
      try {
        const response = await fetch('http://localhost:8081/api/products');
        if (!response.ok) throw new Error('Gagal mengambil produk');
        this.products = await response.json();
      } catch (err) {
        alert('Gagal memuat data produk.');
      }
    },
    async addProduct() {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8081/api/admin/products', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(this.newProduct)
        });

        if (!response.ok) throw new Error('Gagal menambahkan produk');
        alert('Produk berhasil ditambahkan!');
        this.newProduct = { productId: '', nama: '', brand: '', category: '', price: '', stock: '' };
        this.fetchProducts();
      } catch (err) {
        alert(err.message);
      }
    },
    async deleteProduct(id) {
      if (!confirm('Yakin ingin menghapus produk ini?')) return;

      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`http://localhost:8081/api/admin/products/${id}`, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!response.ok) throw new Error('Gagal menghapus produk');
        alert('Produk berhasil dihapus!');
        this.fetchProducts();
      } catch (err) {
        alert(err.message);
      }
    }
  }
};
</script>

<style scoped>
h2 {
  font-family: 'Poppins', sans-serif;
}
.card-title {
  font-size: 1.25rem;
}
.card-body h5 {
  font-size: 1.2rem;
}
.card-body p {
  margin: 0;
}
</style>
