<template>
  <div class="container py-5">
    <h2 class="fw-bold fs-3 mb-4 border-bottom pb-3">Keranjang Saya</h2>

    <div v-if="cartItems.length > 0" class="cart-table">
      <table class="table align-middle">
        <thead>
          <tr class="bg-light">
            <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /></th>
            <th colspan="2">Produk</th>
            <th class="text-center">Harga</th>
            <th class="text-center">Jumlah</th>
            <th class="text-center">Total</th>
            <th class="text-center">Aksi</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in cartItems" :key="item.id">
            <td><input type="checkbox" :value="item.id" v-model="selectedItems" /></td>
            <td style="width: 80px">
              <img :src="item.image" alt="Product" class="img-thumbnail rounded" style="width: 70px" />
            </td>
            <td>
              <div class="fw-semibold">{{ item.name }}</div>
              <div class="text-muted small">{{ item.description }}</div>
            </td>
            <td class="text-center">Rp {{ item.price.toLocaleString() }}</td>
            <td class="text-center">
              <div class="btn-group">
                <button class="btn btn-sm btn-outline-secondary" @click="decreaseQuantity(item)">−</button>
                <span class="px-3 pt-1">{{ item.quantity }}</span>
                <button class="btn btn-sm btn-outline-secondary" @click="increaseQuantity(item)">+</button>
              </div>
            </td>
            <td class="text-center text-danger fw-bold">
              Rp {{ (item.price * item.quantity).toLocaleString() }}
            </td>
            <td class="text-center">
              <button class="btn btn-sm btn-outline-danger" @click="removeItem(item.id)">Hapus</button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="cart-summary bg-white p-4 mt-3 border-top d-flex flex-column flex-md-row justify-content-between align-items-start gap-4">
        <div class="w-100">
          <p class="mb-2">
            Dipilih: <strong>{{ totalItems }}</strong> produk
          </p>
          <p class="mb-1">
            Subtotal: <strong>Rp {{ totalPrice.toLocaleString() }}</strong>
          </p>
          <p v-if="voucherApplied" class="text-success mb-1">
            Diskon Voucher ({{ voucherCode.toUpperCase() }}): -{{ discountPercentage }}%
          </p>
          <div class="input-group mt-2 w-100" style="max-width: 320px;">
            <input
              v-model="voucherCode"
              type="text"
              class="form-control"
              placeholder="Kode Voucher (contoh: DISKON10)"
            />
            <button class="btn btn-outline-primary" @click="applyVoucher">Terapkan</button>
          </div>
          <p v-if="voucherError" class="text-danger small mt-2">{{ voucherError }}</p>
        </div>

        <div class="text-end w-100">
          <p class="fs-5 mb-2">Total Akhir:</p>
          <p class="text-danger fs-3 fw-bold">
            Rp {{ finalTotal.toLocaleString() }}
          </p>
          <button
            class="btn btn-animated-checkout mt-2 w-100"
            :disabled="selectedItems.length === 0"
            @click="goToPayment"
          >
            <span>Checkout</span>
            <i class="bi bi-arrow-right-circle-fill ms-2"></i>
          </button>
        </div>
      </div>
    </div>

    <p v-else class="text-center text-muted fs-5 py-5">Keranjang kamu masih kosong.</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cartItems: JSON.parse(localStorage.getItem("cartItems")) || [],
      selectedItems: [],
      voucherCode: "",
      voucherApplied: false,
      discountPercentage: 0,
      voucherError: ""
    };
  },
  computed: {
    selectAll: {
      get() {
        return this.selectedItems.length === this.cartItems.length && this.cartItems.length > 0;
      },
      set(value) {
        this.selectedItems = value ? this.cartItems.map(item => item.id) : [];
      }
    },
    totalItems() {
      return this.cartItems
        .filter(item => this.selectedItems.includes(item.id))
        .reduce((sum, item) => sum + item.quantity, 0);
    },
    totalPrice() {
      return this.cartItems
        .filter(item => this.selectedItems.includes(item.id))
        .reduce((sum, item) => sum + item.price * item.quantity, 0);
    },
    finalTotal() {
      if (this.voucherApplied) {
        return Math.floor(this.totalPrice * (1 - this.discountPercentage / 100));
      }
      return this.totalPrice;
    }
  },
  methods: {
    decreaseQuantity(item) {
      const found = this.cartItems.find(i => i.id === item.id);
      if (found && found.quantity > 1) {
        found.quantity--;
      } else {
        this.removeItem(item.id);
      }
      this.updateCart();
    },
    increaseQuantity(item) {
      const found = this.cartItems.find(i => i.id === item.id);
      if (found) {
        found.quantity++;
      }
      this.updateCart();
    },
    removeItem(id) {
      this.cartItems = this.cartItems.filter(item => item.id !== id);
      this.selectedItems = this.selectedItems.filter(selId => selId !== id);
      this.updateCart();
    },
    updateCart() {
      localStorage.setItem("cartItems", JSON.stringify(this.cartItems));
    },
    toggleSelectAll() {
      this.selectAll = !this.selectAll;
    },
    applyVoucher() {
      const code = this.voucherCode.trim().toLowerCase();
      if (code === "diskon10") {
        this.discountPercentage = 10;
        this.voucherApplied = true;
        this.voucherError = "";
      } else if (code === "diskon20") {
        this.discountPercentage = 20;
        this.voucherApplied = true;
        this.voucherError = "";
      } else {
        this.voucherApplied = false;
        this.discountPercentage = 0;
        this.voucherError = "Kode voucher tidak valid atau sudah kedaluwarsa.";
      }
    },
    goToPayment() {
      const user = localStorage.getItem("user");
      if (!user) {
        alert("Silakan login terlebih dahulu.");
        this.$router.push("/login");
        return;
      }
      this.$router.push({
        path: "/payment",
        query: { items: this.selectedItems.join(",") }
      });
    }
  }
};
</script>

<style scoped>
.cart-table {
  background: #fff;
  border: 1px solid #eee;
  border-radius: 8px;
  overflow-x: auto;
}
.cart-summary {
  border-radius: 8px;
}
.img-thumbnail {
  object-fit: cover;
  max-height: 70px;
}

.btn-animated-checkout {
  width: 10px;       /* Lebar button */
  height: 48px;       /* Tinggi button */
  background-color: #00b14f;
  color: white;
  font-weight: 600;
  font-size: 1.1rem;
  border: none;
  padding: 10px 2px;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn-animated-checkout:hover {
  background-color: #029d45;
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(0, 177, 79, 0.4);
}
.btn-animated-checkout:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
