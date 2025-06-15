# Tugas Besar Pemodelan Basis Objek

## **Judul**: Scentify

### **Deskripsi**
Scentify adalah sebuah website penjualan parfum yang menghadirkan berbagai pilihan aroma dari berbagai macam brand, baik lokal maupun internasional.  
Dirancang untuk memberikan pengalaman berbelanja parfum yang **mudah**, **elegan**, dan **terpercaya**.

## **📚 API Spesifikasi🤪**

### **1. Auth API (Authentication)**
| Method | Endpoint             | Deskripsi                         | Request Body (JSON)                                                                                  | Response                                           | Notes                         |
| ------ | -------------------- | --------------------------------- | ---------------------------------------------------------------------------------------------------- | -------------------------------------------------- | ----------------------------- |
| POST   | `/api/auth/login`    | Login user dan dapatkan token JWT | `{ "email": "string", "password": "string" }`                                                        | `{ "token": "JWT", "userId": 1, "role": "BUYER" }` | Token JWT + info user login   |
| POST   | `/api/auth/register` | Register user (buyer/admin)       | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string", "role": "BUYER" }` | User object                                        | Bisa untuk buyer maupun admin |


### 2. Admin API 
| Method | Endpoint                   | Deskripsi           | Request Body (JSON)                                                                                              | Response                    | Notes                            |
| ------ | -------------------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------- | --------------------------- | -------------------------------- |
| POST   | `/api/admin/products`      | Tambah produk baru  | `{ "productId": "P001", "name": "Parfum X", "brand": "Gucci", "price": 200000, "stock": 10, "category": "Men" }` | Produk object               | Token admin dibutuhkan           |
| PUT    | `/api/admin/products/{id}` | Update produk       | Same as above                                                                                                    | Produk yang diupdate        | Token admin dibutuhkan           |
| DELETE | `/api/admin/products/{id}` | Hapus produk        | N/A                                                                                                              | `"Produk berhasil dihapus"` | Token admin dibutuhkan           |
| GET    | `/api/products`            | Daftar semua produk | N/A                                                                                                              | List produk                 | Public endpoint untuk semua user |


### 3. Buyer API 
| Method | Endpoint                                | Deskripsi                    | Request Body                                           | Response                                                 | Notes                             |
| ------ | --------------------------------------- | ---------------------------- | ------------------------------------------------------ | -------------------------------------------------------- | --------------------------------- |
| POST   | `/api/cart/add`                         | Tambah produk ke cart        | `{ "buyerId": 2, "productId": "P001", "quantity": 3 }` | `"3x Produk P001 ditambahkan ke keranjang!"`             | Untuk buyer                       |
| GET    | `/api/cart/{buyerId}/items`             | Lihat isi keranjang          | N/A                                                    | List item keranjang (CartItemDTO)                        | Response termasuk image dan price |
| PUT    | `/api/buyer/{buyerId}/cart/{productId}` | Update jumlah produk di cart | `{ "quantity": 2 }`                                    | `"Jumlah produk P001 di keranjang diperbarui menjadi 2"` |                                   |
| DELETE | `/api/buyer/{buyerId}/cart/{productId}` | Hapus produk dari cart       | N/A                                                    | `"Produk P001 telah dihapus dari keranjang."`            |                                   |
| POST   | `/api/cart/{buyerId}/clear`             | Kosongkan keranjang          | N/A                                                    | `"Keranjang berhasil dikosongkan."`                      | Opsional                          |


## 4. Order & Payment API
| Method | Endpoint                      | Deskripsi                | Request Body           | Response                     | Notes                                             |
| ------ | ----------------------------- | ------------------------ | ---------------------- | ---------------------------- | ------------------------------------------------- |
| POST   | `/api/buyer/{buyerId}/order`  | Checkout order dari cart | `{ "method": "VISA" }` | Order object + status "PAID" | Otomatis kurangi stok + clear cart                |
| GET    | `/api/buyer/{buyerId}/orders` | Riwayat transaksi buyer  | N/A                    | List of `OrderHistoryDTO`    | Ringkas: ID, tanggal, status, total, item singkat |

## 5. Product API
| Method | Endpoint        | Deskripsi                  | Request Body | Response            | Notes                      |
| ------ | --------------- | -------------------------- | ------------ | ------------------- | -------------------------- |
| GET    | `/api/products` | Ambil seluruh produk aktif | N/A          | List produk lengkap | Public, bisa diakses semua |









