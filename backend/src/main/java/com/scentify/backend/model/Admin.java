package com.scentify.backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        // constructor kosong untuk JPA
    }

    public Admin(Long id, String nama, String email, String password, String alamat,String role) {
        super(id, nama, email, password, alamat,role);
    }

    public void deleteProduct(String productId) {
        System.out.println("Produk " + productId + " berhasil dihapus!");
    }

    public void updateProduct(String productId, String newName) {
        System.out.println("Produk " + productId + " diupdate menjadi: " + newName);
    }
}
