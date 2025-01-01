package io.reflectoring.projet.Repository;

import io.reflectoring.projet.Model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository {
    Optional<Role> findByName(ERole name);
}
