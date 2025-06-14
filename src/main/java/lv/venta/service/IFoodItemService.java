package lv.venta.service;

import lv.venta.model.*;

import java.util.List;



public interface IFoodItemService {

	List<FoodItem> getAllFoodItems();

	FoodItem getFoodItemById(long id) throws Exception;

	void insertFoodItem(FoodItem foodItem);

	void updateFoodItem(long id, FoodItem updated) throws Exception;

	void deleteFoodItemById(long id) throws Exception;

	List<FoodItem> getFoodItemsByCategory(String category);

	List<FoodItem> getFoodItemsByRestaurantId(long restaurantId) throws Exception;

	List<FoodItem> searchFoodItemsByName(String keyword);

}
