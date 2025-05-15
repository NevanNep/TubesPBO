# Tugas Besar Pemodelan Basis Objek

## **Judul**: Scentify

### **Deskripsi**
Scentify adalah sebuah website penjualan parfum yang menghadirkan berbagai pilihan aroma dari berbagai macam brand, baik lokal maupun internasional.  
Dirancang untuk memberikan pengalaman berbelanja parfum yang **mudah**, **elegan**, dan **terpercaya**.

---

## 📚 **API Spesifikasi**🤪

### **User API**

| **Method** | **Endpoint**       | **Deskripsi**           | **Request Body (JSON)**                                                                 | **Response**                                      | **Notes**                     |
|------------|--------------------|-------------------------|----------------------------------------------------------------------------------------|--------------------------------------------------|--------------------------------|
| POST       | `/api/users/register`  | Register user baru      | `{ "nama": "string", "email": "string", "password": "string", "alamat": "string" }`    | User object lengkap (JSON) yang baru dibuat      | Registrasi user baru          |
| POST       | `/api/users/login`     | Login user              | `{ "email": "string", "password": "string" }`                                          | `"Login Berhasil"` atau `"Login Gagal"`          | Validasi email & password     |


