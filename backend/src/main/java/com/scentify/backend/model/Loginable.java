package com.scentify.backend.model;
public interface Loginable {
    boolean login(String email, String password);
    void logout();
}