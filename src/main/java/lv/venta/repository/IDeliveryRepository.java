package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Delivery;

public interface IDeliveryRepository extends CrudRepository<Delivery, Long> {

}
