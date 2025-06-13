<template>
  <div class="container py-4">
    <h2 class="text-center fw-bold mb-4" style="font-size: 2.5rem;">🛒 Kelola Produk</h2>

    <!-- Form Tambah Produk -->
    <div class="card shadow-sm mb-5">
      <div class="card-body">
        <h5 class="card-title fw-semibold mb-3">Tambah Produk Baru</h5>
        <form @submit.prevent="addProduct" class="row g-3">
          <div class="col-md-6">
            <input v-model="newProduct.name" type="text" class="form-control" placeholder="Nama Produk" required />
          </div>
          <div class="col-md-6">
            <input v-model="newProduct.brand" type="text" class="form-control" placeholder="Brand" required />
          </div>
          <div class="col-md-6">
            <input v-model="newProduct.price" type="number" class="form-control" placeholder="Harga (Rp)" required />
          </div>
          <div class="col-md-6">
            <input v-model="newProduct.image" type="url" class="form-control" placeholder="URL Gambar" required />
          </div>
          <div class="col-12 d-flex justify-content-end">
            <button class="btn btn-danger px-4 fw-semibold">Tambah</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Daftar Produk -->
    <div class="row">
      <div
        v-for="product in products"
        :key="product.id"
        class="col-md-4 mb-4 d-flex align-items-stretch"
      >
        <div class="card shadow-sm h-100">
          <img
            :src="product.image"
            alt="Product Image"
            class="card-img-top"
            style="height: 200px; object-fit: cover;"
          />
          <div class="card-body d-flex flex-column justify-content-between">
            <div>
              <h5 class="fw-bold">{{ product.name }}</h5>
              <p class="text-muted mb-1">{{ product.brand }}</p>
              <p class="fw-semibold">Rp {{ formatPrice(product.price) }}</p>
            </div>
            <div class="mt-3">
              <router-link
                :to="{ name: 'AdminDetail', params: { id: product.id } }"
                class="btn btn-sm btn-outline-primary me-2"
              >
                Edit
              </router-link>
              <button
                class="btn btn-sm btn-outline-danger"
                @click="deleteProduct(product.id)"
              >
                Hapus
              </button>
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
      products: JSON.parse(localStorage.getItem('products') || '[]'),
      newProduct: {
        name: '',
        brand: '',
        price: '',
        image: ''
      }
    };
  },
  methods: {
    addProduct() {
      const id = Date.now();
      this.products.push({ ...this.newProduct, id });
      localStorage.setItem('products', JSON.stringify(this.products));
      this.newProduct = { name: '', brand: '', price: '', image: '' };
    },
    deleteProduct(id) {
      if (confirm('Yakin ingin menghapus produk ini?')) {
        this.products = this.products.filter(p => p.id !== id);
        localStorage.setItem('products', JSON.stringify(this.products));
      }
    },
    formatPrice(value) {
      return parseInt(value).toLocaleString('id-ID');
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
