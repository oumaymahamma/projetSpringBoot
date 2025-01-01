package io.reflectoring.projet.Controller;

import io.reflectoring.projet.Model.Product;
import io.reflectoring.projet.Service.CategoryService;
import io.reflectoring.projet.Model.Category;
import io.reflectoring.projet.Service.ProductService;
import io.reflectoring.projet.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/products Images/";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome"; // Vue correspondante (adminHome.html)
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory()); // Ajouter les catégories au modèle
        return "categories"; // Vue correspondante (categories.html)
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model) {
        model.addAttribute("category", new Category()); // Ajout d'un objet vide pour le formulaire
        return "categoriesAdd"; // Vue correspondante (categoriesAdd.html)
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category); // Utilisation de l'instance `categoryService`
        return "redirect:/admin/categories"; // Redirection vers la liste des catégories
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        categoryService.removeCategoryBy(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            model.addAttribute("category", category);
            return "categoriesAdd";
        } else {
            return "404";
        }
    }

    @GetMapping("/admin/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String productAddGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile productImage,
                                 @RequestParam("imgName") String imgName) throws IOException {
        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(category);
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        // Gestion de l'image
        String imageUUID;
        if (!productImage.isEmpty()) {
            imageUUID = productImage.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);

            // Création des répertoires nécessaires
            Files.createDirectories(fileNameAndPath.getParent());

            // Écriture du fichier sur le disque
            Files.write(fileNameAndPath, productImage.getBytes());
        } else {
            imageUUID = imgName; // Utilisation du nom d'image par défaut
        }

        product.setImageName(imageUUID);

        // Sauvegarde du produit
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.removeProductBy(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setPrice(product.getPrice());
            productDTO.setWeight(product.getWeight());
            productDTO.setDescription(product.getDescription());
            productDTO.setImageName(product.getImageName());

            model.addAttribute("categories", categoryService.getAllCategory());
            model.addAttribute("productDTO", productDTO);
            return "productAdd";
        } else {
            return "404";
        }
    }

}
