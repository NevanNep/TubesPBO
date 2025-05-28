package com.scentify.backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        // constructor kosong untuk JPA
    }

    public Admin(Long id, String nama, String email, String password, String alamat) {
        super(id, nama, email, password, alamat);
    }

    public void deleteProduct(String productId) {
        System.out.println("Produk " + productId + " berhasil dihapus!");
    }

    public void updateProduct(String productId, String newName) {
        System.out.println("Produk " + productId + " diupdate menjadi: " + newName);
    }
}
