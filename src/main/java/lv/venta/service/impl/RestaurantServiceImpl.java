package lv.venta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.FoodItem;
import lv.venta.model.Restaurant;
import lv.venta.repository.IRestaurantRepository;
import lv.venta.service.IRestaurantService;

@Service
public class RestaurantServiceImpl implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepo;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return (List<Restaurant>) restaurantRepo.findAll();
    }

    @Override
    public Restaurant getRestaurantById(long id) throws Exception {
        return restaurantRepo.findById(id)
                .orElseThrow(() -> new Exception("Restaurant not found with id: " + id));
    }

    @Override
    public void insertRestaurant(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
    }

    @Override
    public void updateRestaurant(long id, Restaurant updated) throws Exception {
        Restaurant existing = getRestaurantById(id);
        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        existing.setPhone(updated.getPhone());
        restaurantRepo.save(existing);
    }

    @Override
    public void deleteRestaurant(long id) throws Exception {
        if (!restaurantRepo.existsById(id)) {
            throw new Exception("Restaurant not found with id: " + id);
        }
        restaurantRepo.deleteById(id);
    }

    @Override
    public List<FoodItem> getMenuByRestaurantId(long restaurantId) throws Exception {
        return getRestaurantById(restaurantId).getFoodItems();
    }

    @Override
    public void addFoodItemToRestaurant(long restaurantId, FoodItem foodItem) throws Exception {
        Restaurant restaurant = getRestaurantById(restaurantId);
        restaurant.getFoodItems().add(foodItem);
        foodItem.setRestaurant(restaurant);
        restaurantRepo.save(restaurant);
    }
}
