package io.reflectoring.projet.Repository;

import io.reflectoring.projet.Service.list;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository {
    list<Category> findAll();

    void save(Category category);

    void deleteById(int id);

    Optional<Category> findById(int id);

    public interface categoryRepository extends JpaRepository<Category, Integer>{}
}
