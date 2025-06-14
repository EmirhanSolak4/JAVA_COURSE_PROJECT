package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.Restaurant;
import lv.venta.model.FoodItem;
import lv.venta.service.IRestaurantService;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(restaurantService.getRestaurantById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            restaurantService.insertRestaurant(restaurant);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable long id, @RequestBody Restaurant restaurant) {
        try {
            restaurantService.updateRestaurant(id, restaurant);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable long id) {
        try {
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<FoodItem>> getRestaurantMenu(@PathVariable long id) {
        try {
            return ResponseEntity.ok(restaurantService.getMenuByRestaurantId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<Void> addFoodItemToRestaurant(@PathVariable long id, @RequestBody FoodItem foodItem) {
        try {
            restaurantService.addFoodItemToRestaurant(id, foodItem);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 