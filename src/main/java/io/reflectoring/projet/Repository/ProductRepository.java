package io.reflectoring.projet.Repository;
import io.reflectoring.projet.Model.Product;
import io.reflectoring.projet.Repository.ProductRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends org.springframework.data.jpa.repository.JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(int id);

public static void setId(Object id) {
}}