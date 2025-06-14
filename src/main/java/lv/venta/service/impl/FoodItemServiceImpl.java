package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.FoodItem;
import lv.venta.model.Restaurant;
import lv.venta.repository.IFoodItemRepository;
import lv.venta.repository.IRestaurantRepository;
import lv.venta.service.IFoodItemService;

@Service
public class FoodItemServiceImpl implements IFoodItemService {

    @Autowired
    private IFoodItemRepository foodRepo;

    @Autowired
    private IRestaurantRepository restaurantRepo;

    @Override
    public List<FoodItem> getAllFoodItems() {
        return (List<FoodItem>) foodRepo.findAll();
    }

    @Override
    public FoodItem getFoodItemById(long id) throws Exception {
        return foodRepo.findById(id)
                .orElseThrow(() -> new Exception("Food item not found with id: " + id));
    }

    @Override
    public void insertFoodItem(FoodItem foodItem) {
        foodRepo.save(foodItem);
    }

    @Override
    public void updateFoodItem(long id, FoodItem updated) throws Exception {
        FoodItem existing = getFoodItemById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setAvailable(updated.isAvailable());
        existing.setCategory(updated.getCategory());
        foodRepo.save(existing);
    }

    @Override
    public void deleteFoodItemById(long id) throws Exception {
        if (!foodRepo.existsById(id)) {
            throw new Exception("Food item not found with id: " + id);
        }
        foodRepo.deleteById(id);
    }

    @Override
    public List<FoodItem> getFoodItemsByCategory(String category) {
        List<FoodItem> filtered = new ArrayList<>();
        for (FoodItem item : foodRepo.findAll()) {
            if (item.getCategory().toString().equalsIgnoreCase(category)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurantId(long restaurantId) throws Exception {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant not found with id: " + restaurantId));
        return restaurant.getFoodItems();
    }

    @Override
    public List<FoodItem> searchFoodItemsByName(String keyword) {
        List<FoodItem> results = new ArrayList<>();
        for (FoodItem item : foodRepo.findAll()) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }
}
