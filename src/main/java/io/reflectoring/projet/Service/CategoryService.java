package io.reflectoring.projet.Service;

import io.reflectoring.projet.Model.Category;
import io.reflectoring.projet.Repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void removeCategoryBy(int id) {categoryRepository.deleteById(id);
    }
    public Category getCategoryById(int id) {return categoryRepository.findById(id);}
    }
