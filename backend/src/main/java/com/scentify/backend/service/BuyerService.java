package com.scentify.backend.service;

import com.scentify.backend.model.Buyer;
import com.scentify.backend.model.Product;
import com.scentify.backend.repository.BuyerRepository;
import com.scentify.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;

    public BuyerService(BuyerRepository buyerRepo, ProductRepository productRepo) {
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
    }

    // Method untuk registrasi buyer baru
    public Buyer saveBuyer(Buyer buyer) {
        return buyerRepo.save(buyer);
    }

    // Ambil buyer berdasar ID
    public Optional<Buyer> getBuyerById(Long id) {
        return buyerRepo.findById(id);
    }

    // Tambah produk ke cart buyer dengan validasi stok
    public String addToCart(Long buyerId, String productId, int quantity) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Cek stok produk (bagian solusi validasi stok)
        if (product.getStock() < quantity) {
            throw new RuntimeException("Stok produk tidak cukup");
        }

        // Tambah produk ke cart sebanyak quantity
        for (int i = 0; i < quantity; i++) {
            buyer.getCart().add(productId);
        }

        buyerRepo.save(buyer); // Simpan perubahan cart
        return quantity + "x Produk " + productId + " ditambahkan ke keranjang!";
    }

    // Ambil isi cart buyer
    public List<String> getCartByBuyerId(Long buyerId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        return buyer.getCart();
    }

    // Update quantity produk di cart buyer (bisa tambah atau kurang sesuai selisih)
    public String updateCartItemQuantity(Long buyerId, String productId, int newQuantity) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Hitung quantity produk saat ini di cart
        long currentQuantity = buyer.getCart().stream()
                .filter(id -> id.equals(productId))
                .count();

        int difference = newQuantity - (int) currentQuantity;

        if (difference > 0) {
            // Tambah produk sebanyak difference, cek stok cukup
            if (product.getStock() < difference) {
                throw new RuntimeException("Stok produk tidak cukup");
            }
            for (int i = 0; i < difference; i++) {
                buyer.getCart().add(productId);
            }
        } else if (difference < 0) {
            // Kurangi produk sebanyak abs(difference)
            int toRemove = Math.abs(difference);
            for (int i = 0; i < toRemove; i++) {
                if (!buyer.getCart().remove(productId)) {
                    break; // jika produk tidak ada di cart, hentikan
                }
            }
        }
        // Jika difference == 0, tidak ada perubahan

        buyerRepo.save(buyer);
        return "Jumlah produk " + productId + " di keranjang diperbarui menjadi " + newQuantity;
    }

    // Hapus produk dari cart buyer
    public String removeFromCart(Long buyerId, String productId) {
        Buyer buyer = buyerRepo.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        boolean removed = buyer.getCart().removeIf(id -> id.equals(productId));

        if (!removed) {
            throw new RuntimeException("Product not found in cart");
        }

        buyerRepo.save(buyer);
        return "Produk " + productId + " telah dihapus dari keranjang.";
    }
}
