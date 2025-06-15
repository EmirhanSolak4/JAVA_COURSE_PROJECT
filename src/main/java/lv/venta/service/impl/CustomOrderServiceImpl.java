package lv.venta.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.service.ICustomOrderService;
import lv.venta.enums.OrderStatus;
import lv.venta.model.CustomOrder;
import lv.venta.model.FoodItem;
import lv.venta.model.User;
import lv.venta.repository.*;

@Service
public class CustomOrderServiceImpl implements ICustomOrderService {

    @Autowired
    private ICustomOrderRepository orderRepo;

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private IFoodItemRepository foodItemRepo;

    @Override
    public List<CustomOrder> getAllOrders() {
        return (List<CustomOrder>) orderRepo.findAll();
    }

    @Override
    public CustomOrder getOrderById(long id) throws Exception {
        return orderRepo.findById(id)
                .orElseThrow(() -> new Exception("Order not found with id: " + id));
    }

    @Override
    public List<CustomOrder> getOrdersByUserId(long userId) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));
        return user.getOrders();
    }

    @Override
    public void placeOrder(long userId, List<Long> foodItemIds, String specialInstructions) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));

        List<FoodItem> items = new ArrayList<>();
        for (Long id : foodItemIds) {
            FoodItem item = foodItemRepo.findById(id)
                    .orElseThrow(() -> new Exception("Food item not found with id: " + id));
            items.add(item);
        }

        CustomOrder order = new CustomOrder();
        order.setUser(user);
        order.setItems(items);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setSpecialInstructions(specialInstructions);

        orderRepo.save(order);
    }

    @Override
    public void updateOrderStatus(long orderId, String status) throws Exception {
        CustomOrder order = getOrderById(orderId);
        OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
        order.setStatus(newStatus);
        orderRepo.save(order);
    }

    @Override
    public List<CustomOrder> getOrdersByRestaurantId(long restaurantId) throws Exception {
        List<CustomOrder> results = new ArrayList<>();
        for (CustomOrder order : orderRepo.findAll()) {
            for (FoodItem item : order.getItems()) {
                if (item.getRestaurant().getId() == restaurantId) {
                    results.add(order);
                    break;
                }
            }
        }
        return results;
    }
}
