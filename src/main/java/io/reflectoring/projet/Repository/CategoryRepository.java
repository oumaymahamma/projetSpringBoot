package io.reflectoring.projet.Repository;

import io.reflectoring.projet.Model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


}
