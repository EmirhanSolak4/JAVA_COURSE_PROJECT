package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.CustomOrder;

public interface ICustomOrderRepository extends CrudRepository<CustomOrder, Long> {

}
