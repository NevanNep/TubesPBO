<template>
  <div class="container mt-5">
    <h2 class="mb-3 fw-bold">Edit Produk</h2>

    <form @submit.prevent="updateProduct" class="p-4 border bg-light rounded">
      <input v-model="product.name" class="input mb-2" placeholder="Nama Produk" />
      <input v-model="product.brand" class="input mb-2" placeholder="Brand" />
      <input v-model="product.price" type="number" class="input mb-2" placeholder="Harga" />
      <input v-model="product.image" class="input mb-3" placeholder="URL Gambar" />

      <button class="btn btn-success me-2">Simpan Perubahan</button>
      <button @click.prevent="deleteProduct" class="btn btn-danger">Hapus Produk</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      product: null
    };
  },
  created() {
    const id = parseInt(this.$route.params.id);
    const products = JSON.parse(localStorage.getItem('products') || '[]');
    this.product = products.find(p => p.id === id);
  },
  methods: {
    updateProduct() {
      const products = JSON.parse(localStorage.getItem('products'));
      const index = products.findIndex(p => p.id === this.product.id);
      products[index] = this.product;
      localStorage.setItem('products', JSON.stringify(products));
      alert("Produk diperbarui!");
      this.$router.push('/admin');
    },
    deleteProduct() {
      let products = JSON.parse(localStorage.getItem('products'));
      products = products.filter(p => p.id !== this.product.id);
      localStorage.setItem('products', JSON.stringify(products));
      alert("Produk dihapus.");
      this.$router.push('/admin');
    }
  }
};
</script>
