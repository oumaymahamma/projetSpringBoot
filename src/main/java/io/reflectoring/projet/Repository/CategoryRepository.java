package io.reflectoring.projet.Repository;

import io.reflectoring.projet.Model.Category;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findById(int id);

}
