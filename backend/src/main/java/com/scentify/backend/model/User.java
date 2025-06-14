package com.scentify.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class User implements Loginable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String email;
    private String password;
    private String alamat;
    private String username;

    @Column(nullable = false)
    private String role; // "ADMIN" atau "BUYER"

    public User() {
        // constructor kosong diperlukan oleh JPA
    }

    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public User(Long id, String nama, String email, String password, String alamat, String role) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.role = role;
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
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
