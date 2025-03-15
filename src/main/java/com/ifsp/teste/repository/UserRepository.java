package com.ifsp.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifsp.teste.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
