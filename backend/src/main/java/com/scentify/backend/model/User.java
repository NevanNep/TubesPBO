package com.scentify.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users") // Opsional, sesuai nama tabel di DB
public class User implements Loginable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String email;
    private String password;
    private String alamat;

    public User() {
        // constructor kosong diperlukan oleh JPA
    }

    public User(Long id, String nama, String email, String password, String alamat) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
    }

    @Override
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    @Override
    public void logout() {
        System.out.println("Logout berhasil untuk: " + this.email);
    }

    // Getter dan Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}
