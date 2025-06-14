package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Restaurant;

public interface IRestaurantRepository extends CrudRepository<Restaurant, Long> {

}
