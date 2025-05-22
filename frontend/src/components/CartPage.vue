<template>
  <div class="cart-container">
    <h2>Keranjang Belanja</h2>
    <div v-if="cartItems.length === 0" class="empty-cart">
      <p>Keranjang Anda kosong.</p>
    </div>
    <div v-else class="cart-items">
      <ul>
        <li v-for="item in cartItems" :key="item.id" class="cart-item">
          <div class="cart-item-info">
            <img :src="item.image" alt="item.name" class="cart-item-image" />
            <div class="cart-item-details">
              <h3>{{ item.name }}</h3>
              <p>Rp {{ item.price }} x {{ item.quantity }} = Rp {{ item.price * item.quantity }}</p>
            </div>
          </div>
          <button @click="removeItem(item)" class="remove-item">Hapus</button>
        </li>
      </ul>
      <p class="total-price">Total: Rp {{ totalPrice }}</p>
      <router-link to="/payment" class="proceed-to-payment">Lanjut ke Pembayaran</router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Inisialisasi cartItems dengan array kosong jika localStorage tidak ada datanya
      cartItems: JSON.parse(localStorage.getItem('cartItems')) || [] 
    };
  },
  computed: {
    totalPrice() {
      // Menghitung total harga
      return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
    }
  },
  methods: {
    removeItem(item) {
      // Hapus item dari keranjang dan update localStorage
      const index = this.cartItems.indexOf(item);
      if (index > -1) {
        this.cartItems.splice(index, 1);
        localStorage.setItem('cartItems', JSON.stringify(this.cartItems)); // Simpan kembali ke localStorage
      }
    }
  }
};
</script>

<style scoped>
.cart-container {
  padding: 20px;
}

h2 {
  font-size: 2rem;
  color: #333;
}

.empty-cart {
  text-align: center;
  font-size: 1.2rem;
  color: #666;
}

.cart-items {
  margin-top: 20px;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  margin: 10px 0;
  background-color: #f8f8f8;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.cart-item-info {
  display: flex;
}

.cart-item-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}

.cart-item-details {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.remove-item {
  background-color: red;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.remove-item:hover {
  background-color: #d32f2f;
}

.total-price {
  margin-top: 20px;
  font-size: 1.5rem;
  font-weight: bold;
}

.proceed-to-payment {
  display: inline-block;
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  text-decoration: none;
  margin-top: 20px;
  cursor: pointer;
}

.proceed-to-payment:hover {
  background-color: #0056b3;
}
</style>
