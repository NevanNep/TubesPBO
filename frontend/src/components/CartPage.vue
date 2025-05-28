<template>
  <div class="container py-5">
    <h2 class="text-center mb-5 fw-bold section-title">Keranjang Belanja</h2>

    <!-- Produk di keranjang -->
    <div v-if="cartItems.length > 0" class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
      <div v-for="item in cartItems" :key="item.id" class="col">
        <div class="card product-card h-100 shadow-sm border-0 position-relative overflow-hidden">
          <!-- Custom Checkbox -->
          <label class="custom-checkbox" :for="'select-' + item.id" tabindex="0">
            <input
              type="checkbox"
              :id="'select-' + item.id"
              v-model="selectedItems"
              :value="item.id"
              class="checkbox-input"
            />
            <span class="checkmark">
              <svg viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="20 6 9 17 4 12" />
              </svg>
            </span>
          </label>

          <router-link :to="'/product/' + item.id" class="d-block overflow-hidden">
            <img :src="item.image" :alt="item.name" class="card-img-top product-image" />
          </router-link>

          <div class="card-body text-center d-flex flex-column">
            <h5 class="card-title fw-semibold text-truncate" :title="item.name">{{ item.name }}</h5>
            <p class="card-text text-muted small text-truncate" :title="item.description">{{ item.description }}</p>
            <p class="text-danger fw-bold fs-5 mb-1">Rp {{ item.price.toLocaleString() }}</p>
            <p class="mb-2">Jumlah: {{ item.quantity }}</p>
            <button @click="decreaseQuantity(item)" class="btn btn-outline-danger btn-sm mx-auto px-4">
              <i class="bi bi-dash-lg"></i> Kurangi
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Kalau keranjang kosong -->
    <p v-else class="text-center text-muted fst-italic fs-5 mt-5">
      Keranjang belanja kosong.
    </p>

    <!-- Summary dan tombol bayar -->
    <div class="summary mt-5 p-4 rounded shadow-sm bg-white text-center">
      <p class="fs-5 mb-2">
        Total Barang: 
        <span class="badge bg-secondary px-3 py-2 fs-6">{{ totalItems }}</span>
      </p>
      <p class="fs-4 fw-bold text-danger mb-3">
        Total Harga: Rp {{ totalPrice.toLocaleString() }}
      </p>

      <button
        class="btn btn-custom-payment"
        :disabled="selectedItems.length === 0"
        @click="goToPayment"
        :title="selectedItems.length === 0 ? 'Pilih produk terlebih dahulu' : ''"
      >
        <i class="bi bi-credit-card me-2"></i> Lanjutkan ke Pembayaran
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cartItems: JSON.parse(localStorage.getItem('cartItems')) || [],
      selectedItems: [],
    };
  },
  computed: {
    totalPrice() {
      return this.cartItems
        .filter(item => this.selectedItems.includes(item.id))
        .reduce((sum, item) => sum + item.price * item.quantity, 0);
    },
    totalItems() {
      return this.cartItems
        .filter(item => this.selectedItems.includes(item.id))
        .reduce((sum, item) => sum + item.quantity, 0);
    }
  },
  methods: {
    decreaseQuantity(product) {
      let cartItems = [...this.cartItems];
      const index = cartItems.findIndex(item => item.id === product.id);
      if (index !== -1) {
        if (cartItems[index].quantity > 1) {
          cartItems[index].quantity -= 1;
        } else {
          cartItems.splice(index, 1);
          this.selectedItems = this.selectedItems.filter(id => id !== product.id);
        }
      }
      localStorage.setItem('cartItems', JSON.stringify(cartItems));
      this.cartItems = cartItems;
    },
     goToPayment() {
    // Cek login dulu
    const user = localStorage.getItem('user');
    if (!user) {
      alert('Silakan login terlebih dahulu sebelum melanjutkan pembayaran.');
      this.$router.push('/login');
      return;
    }
    // Kalau sudah login, lanjut ke halaman payment
    if (this.selectedItems.length > 0) {
      this.$router.push({
        path: '/payment',
        query: { items: this.selectedItems.join(',') }
      });
    } else {
      alert('Silakan pilih produk yang ingin dibeli!');
    }
  }
}
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600;700&display=swap');

body {
  background-color: #f8f9fa;
}

.section-title {
  font-family: 'Playfair Display', serif;
  font-size: 2.8rem;
  color: #222;
  margin-top: -50px;
  letter-spacing: 1.5px;
}

.product-card {
  border-radius: 12px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  background: #fff;
}
.product-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.12);
}

.product-image {
  height: 250px;
  width: 100%;
  object-fit: cover;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  transition: transform 0.4s ease;
}
.product-image:hover {
  transform: scale(1.1);
}

.custom-checkbox {
  position: absolute;
  top: 15px;
  left: 15px;
  display: inline-block;
  width: 28px;
  height: 28px;
  cursor: pointer;
  z-index: 10;
  user-select: none;
  border-radius: 6px;
  background: #eee;
  transition: background-color 0.3s ease;
}
.custom-checkbox:focus-within {
  outline: 2px solid #dc3545;
  outline-offset: 2px;
}
.checkbox-input {
  opacity: 0;
  width: 0;
  height: 0;
  position: absolute;
}
.checkmark {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  pointer-events: none;
  width: 18px;
  height: 18px;
  border-radius: 4px;
  background-color: transparent;
  transition: background-color 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}
.checkbox-input:checked + .checkmark {
  background-color: #dc3545;
  box-shadow: 0 0 10px #dc3545aa;
}
.checkbox-input:checked + .checkmark svg {
  stroke-dashoffset: 0;
  stroke: #fff;
}

.btn-outline-danger {
  border-radius: 30px;
  font-weight: 600;
  letter-spacing: 0.05em;
  transition: background-color 0.3s ease, color 0.3s ease;
}
.btn-outline-danger:hover {
  background-color: #dc3545;
  color: white;
}

/* ✅ Styling tombol pembayaran diperbarui */
.btn-custom-payment {
  background-color: #dc3545;
  color: #fff;
  font-weight: 600;
  border-radius: 30px;
  padding: 10px 24px;
  font-size: 1rem;
  width: 100%;
  max-width: 280px;
  margin: 0 auto;
  box-shadow: 0 4px 14px rgba(220, 53, 69, 0.4);
  transition: background-color 0.3s ease, box-shadow 0.3s ease, transform 0.2s ease;
  display: block;
}
.btn-custom-payment:disabled {
  background-color: #f5b3b6;
  box-shadow: none;
  color: #9e9e9e;
  cursor: not-allowed;
  transform: none;
}
.btn-custom-payment:hover:enabled {
  background-color: #b02a37;
  box-shadow: 0 8px 18px rgba(176, 42, 55, 0.5);
  transform: translateY(-1px);
}
.btn-custom-payment:active:enabled {
  transform: translateY(0);
  box-shadow: 0 5px 12px rgba(176, 42, 55, 0.4);
}

/* Summary box */
.summary {
  max-width: 320px;
  padding: 16px 20px;
  border-radius: 10px;
  font-size: 0.9rem;
  box-shadow: 0 6px 18px rgba(0,0,0,0.06);
  margin-left: auto;
  margin-right: auto;
}
.summary .badge {
  font-size: 0.5rem;
  padding: 3px 8px;
  border-radius: 3px;
}
.summary .fw-bold {
  font-size: 2.2rem;
}
</style>
//TES