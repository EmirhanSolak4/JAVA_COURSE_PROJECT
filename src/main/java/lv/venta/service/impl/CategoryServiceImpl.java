package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lv.venta.model.Category;
import lv.venta.repository.ICategoryRepository;
import lv.venta.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        for (Category c : categoryRepository.findAll()) {
            list.add(c);
        }
        return list;
    }

    @Override
    public Category getCategoryById(long id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found with id: " + id));
    }

    @Override
    public void insertCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(long id, Category updated) throws Exception {
        Category existing = getCategoryById(id);
        existing.setName(updated.getName());
        categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(long id) throws Exception {
        if (!categoryRepository.existsById(id)) {
            throw new Exception("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
} 