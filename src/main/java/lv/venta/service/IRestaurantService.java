package lv.venta.service;

import java.util.List;

import lv.venta.model.*;




public interface IRestaurantService {

	List<Restaurant> getAllRestaurants();

	lv.venta.model.Restaurant getRestaurantById(long id) throws Exception;

	void insertRestaurant(lv.venta.model.Restaurant restaurant);

	void updateRestaurant(long id, lv.venta.model.Restaurant updated) throws Exception;

	void deleteRestaurant(long id) throws Exception;

	java.util.List<FoodItem> getMenuByRestaurantId(long restaurantId) throws Exception;

	void addFoodItemToRestaurant(long restaurantId, lv.venta.model.FoodItem foodItem) throws Exception;

}
