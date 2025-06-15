package lv.venta.service;

import java.util.List;

import lv.venta.model.*;


public interface ICustomOrderService {

	List<CustomOrder> getAllOrders();

	CustomOrder getOrderById(long id) throws Exception;

	List<CustomOrder> getOrdersByUserId(long userId) throws Exception;

	void placeOrder(long userId, List<Long> foodItemIds, String specialInstructions) throws Exception;

	void updateOrderStatus(long orderId, String status) throws Exception;

	List<CustomOrder> getOrdersByRestaurantId(long restaurantId) throws Exception;

}
