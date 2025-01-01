package io.reflectoring.projet.Service;

import io.reflectoring.projet.Model.Product;
import io.reflectoring.projet.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public static Object getAllProduct() {

        return null;
    }

    public static void removeProductBy(int id) {
    }

    // Méthode pour récupérer tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void addProduct(Product product) {
        productRepository.save(product);
    }

public void deleteProductById(long id) {
        productRepository.deleteById(id);
}
public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
}
public List<Product> getALLProductsByCategory(int id) {
        return productRepository.findAllByCategory_Id(id);
}

    public Object getALLProductsByCategoryId(int id) {
        return null;
    }

}
