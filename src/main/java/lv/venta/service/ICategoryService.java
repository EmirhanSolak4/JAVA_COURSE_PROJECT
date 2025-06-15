package lv.venta.service;

import java.util.List;
import lv.venta.model.Category;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(long id) throws Exception;
    void insertCategory(Category category);
    void updateCategory(long id, Category updated) throws Exception;
    void deleteCategory(long id) throws Exception;
}