package io.reflectoring.projet.Repository;

import io.reflectoring.projet.Model.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(@NotBlank @Size(min = 3, max = 20) String username);

  boolean existsByEmail(@NotBlank @Size(max = 50) @Email String email);

  Optional<User> findUserbyEmail(String email);
}
