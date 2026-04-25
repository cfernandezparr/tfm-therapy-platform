package com.carlos.tfm.therapy.User.Infrastructure.Repository;

import com.carlos.tfm.therapy.User.Domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}