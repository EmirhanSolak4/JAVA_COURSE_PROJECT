package lv.venta.service;

import java.util.List;

import lv.venta.model.*;


public interface IDeliveryService {

	List<Delivery> getAllDeliveries();

	Delivery getDeliveryById(long id) throws Exception;

	void assignDeliveryToOrder(long orderId, String deliveryAddress) throws Exception;

	void updateDeliveryStatus(long deliveryId, String status) throws Exception;

	List<Delivery> getDeliveriesByCourierName(String courierName);

}
