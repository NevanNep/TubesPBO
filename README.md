# Tugas Besar Pemodelan Basis Objek

## **Judul**: Scentify

### **Deskripsi**
Scentify adalah sebuah website penjualan parfum yang menghadirkan berbagai pilihan aroma dari berbagai macam brand, baik lokal maupun internasional.  
Dirancang untuk memberikan pengalaman berbelanja parfum yang **mudah**, **elegan**, dan **terpercaya**.

---

| **Method** | **Endpoint**         | **Deskripsi**                     | **Request Body (JSON)**                                                             | **Response**                                                                        | **Notes**                               |
| ---------- | -------------------- | --------------------------------- | ----------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | --------------------------------------- |
| POST       | `/api/auth/login`    | Login user dan dapatkan token JWT | `{ "email": "string", "password": "string" }`                                       | `{ "token": "JWT Token" }`                                                          | Login untuk mendapatkan token           |
| POST       | `/api/auth/register` | Register user baru                | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }` | Registrasi user baru (admin atau buyer) |



