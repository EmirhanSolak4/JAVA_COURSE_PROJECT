package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.FoodItem;
import lv.venta.service.IFoodItemService;
import java.util.List;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {

    @Autowired
    private IFoodItemService foodItemService;

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(foodItemService.getFoodItemById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createFoodItem(@RequestBody FoodItem foodItem) {
        try {
            foodItemService.insertFoodItem(foodItem);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFoodItem(@PathVariable long id, @RequestBody FoodItem foodItem) {
        try {
            foodItemService.updateFoodItem(id, foodItem);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable long id) {
        try {
            foodItemService.deleteFoodItemById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(foodItemService.getFoodItemsByCategory(category));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurant(@PathVariable long restaurantId) {
        try {
            return ResponseEntity.ok(foodItemService.getFoodItemsByRestaurantId(restaurantId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@RequestParam String keyword) {
        return ResponseEntity.ok(foodItemService.searchFoodItemsByName(keyword));
    }
} 