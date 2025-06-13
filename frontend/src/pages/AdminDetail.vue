<template>
  <div class="container my-4">
    <h2 class="mb-4 fw-bold">Edit Produk</h2>

    <form @submit.prevent="updateProduct" class="p-4 border rounded bg-light">
      <input v-model="product.name" placeholder="Nama Produk" class="form-control mb-3" />
      <input v-model="product.brand" placeholder="Brand" class="form-control mb-3" />
      <input v-model="product.price" type="number" placeholder="Harga" class="form-control mb-3" />
      <input v-model="product.image" placeholder="URL Gambar" class="form-control mb-3" />
      <button class="btn btn-success">Simpan Perubahan</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      product: {}
    };
  },
  mounted() {
    const id = Number(this.$route.params.id);
    const allProducts = JSON.parse(localStorage.getItem('products') || '[]');
    const found = allProducts.find(p => p.id === id);
    if (!found) {
      alert('Produk tidak ditemukan!');
      this.$router.push('/admin');
      return;
    }
    this.product = { ...found };
  },
  methods: {
    updateProduct() {
      const id = Number(this.$route.params.id);
      let allProducts = JSON.parse(localStorage.getItem('products') || '[]');
      const index = allProducts.findIndex(p => p.id === id);

      if (index !== -1) {
        allProducts[index] = { ...this.product };
        localStorage.setItem('products', JSON.stringify(allProducts));
        alert('Produk berhasil diperbarui!');
        this.$router.push('/admin');
      }
    }
  }
};
</script>
