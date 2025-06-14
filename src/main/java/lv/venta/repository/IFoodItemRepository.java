package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.FoodItem;

public interface IFoodItemRepository extends CrudRepository<FoodItem, Long> {

}
