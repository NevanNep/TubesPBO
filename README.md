# Tugas Besar Pemodelan Basis Objek

## **Judul**: Scentify

### **Deskripsi**
Scentify adalah sebuah website penjualan parfum yang menghadirkan berbagai pilihan aroma dari berbagai macam brand, baik lokal maupun internasional.  
Dirancang untuk memberikan pengalaman berbelanja parfum yang **mudah**, **elegan**, dan **terpercaya**.

## **📚 API Spesifikasi🤪**

### **1. Auth API (Authentication)**
| **Method** | **Endpoint**         | **Deskripsi**                     | **Request Body (JSON)**                                                             | **Response**                                                                        | **Notes**                               |
| ---------- | -------------------- | --------------------------------- | ----------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | --------------------------------------- |
| POST       | `/api/auth/login`    | Login user dan dapatkan token JWT | `{ "email": "string", "password": "string" }`                                       | `{ "token": "JWT Token" }`                                                          | Login untuk mendapatkan token           |
| POST       | `/api/auth/register` | Register user baru                | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | Registrasi user baru (admin atau buyer) |

### 2. Admin API 
| **Method** | **Endpoint**                      | **Deskripsi**       | **Request Body (JSON)**                                                                                              | **Response**                                                                        | **Notes**                  |
| ---------- | --------------------------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | -------------------------- |
| POST       | `/api/admin/register`             | Register admin baru | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }`                                  | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | Untuk mendaftar admin baru |
| POST       | `/api/admin/products`             | Tambah produk baru  | `{ "productId": "P001", "nama": "string", "brand": "string", "price": 100000, "stock": 10, "category": "string" }`   | Produk baru ditambahkan atau error                                                  | Admin menambah produk baru |
| PUT        | `/api/admin/products/{productId}` | Update produk       | `{ "productId": "P001", "nama": "updated", "brand": "updated", "price": 120000, "stock": 5, "category": "updated" }` | Produk yang sudah diperbarui atau error                                             | Admin memperbarui produk   |
| DELETE     | `/api/admin/products/{productId}` | Hapus produk        | N/A (parameter lewat URL)                                                                                            | `"Product deleted"`                                                                 | Admin menghapus produk     |

### 3. Buyer API 
| **Method** | **Endpoint**                            | **Deskripsi**                | **Request Body (JSON)**                                                             | **Response**                                                                        | **Notes**                             |
| ---------- | --------------------------------------- | ---------------------------- | ----------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ------------------------------------- |
| POST       | `/api/buyer/register`                   | Register buyer baru          | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | Untuk mendaftar buyer baru            |
| POST       | `/api/buyer/{buyerId}/cart/{productId}` | Tambah produk ke cart        | `{ "quantity": 3 }` (jumlah produk yang ingin ditambahkan ke keranjang)             | `"Product added to cart successfully."`                                             | Buyer menambahkan produk ke keranjang |
| GET        | `/api/buyer/{buyerId}/cart`             | Melihat isi cart             | N/A                                                                                 | List produk di cart buyer                                                           | Menampilkan produk dalam cart buyer   |
| PUT        | `/api/buyer/{buyerId}/cart/{productId}` | Update jumlah produk di cart | `{ "quantity": 2 }`                                                                 | `"Jumlah produk di keranjang diperbarui"`                                           | Update jumlah produk dalam keranjang  |
| DELETE     | `/api/buyer/{buyerId}/cart/{productId}` | Hapus produk dari cart       | N/A (parameter lewat URL)                                                           | `"Product removed from cart"`                                                       | Buyer menghapus produk dari keranjang |
| POST       | `/api/buyer/{buyerId}/order`            | Membuat order baru dari cart | `{ "paymentMethod": { "method": "VISA" } }`                                         | Order object yang baru dibuat dan statusnya                                         | Buyer membuat order dari keranjang    |
| GET        | `/api/buyer/{buyerId}/orders`           | Melihat riwayat order buyer  | N/A                                                                                 | List orders yang dilakukan buyer                                                    | Menampilkan riwayat order buyer       |

## 4. Product API
| **Method** | **Endpoint**    | **Deskripsi**          | **Request Body (JSON)** | **Response**                     | **Notes**                 |
| ---------- | --------------- | ---------------------- | ----------------------- | -------------------------------- | ------------------------- |
| GET        | `/api/products` | Dapatkan daftar produk | N/A                     | List produk yang ada di database | Menampilkan daftar produk |








