package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.CustomOrder;
import lv.venta.service.ICustomOrderService;
import lv.venta.service.IFoodItemService;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ICustomOrderService orderService;

    @Autowired
    private IFoodItemService foodItemService;

    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable long id, Model model) {
        try {
            model.addAttribute("order", orderService.getOrderById(id));
            return "order-detail";
        } catch (Exception e) {
            return "redirect:/orders?error=" + e.getMessage();
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new CustomOrder());
        model.addAttribute("foodItems", foodItemService.getAllFoodItems());
        return "order-form";
    }

    @PostMapping
    public String createOrder(@RequestParam List<Long> foodItemIds, @RequestParam(required = false) String specialInstructions) {
        try {
            // TODO: Get current user ID from session
            long userId = 1L;
            orderService.placeOrder(userId, foodItemIds, specialInstructions);
            return "redirect:/orders";
        } catch (Exception e) {
            return "redirect:/orders/create?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable long id, @RequestParam String status) {
        try {
            orderService.updateOrderStatus(id, status);
            return "redirect:/orders/" + id;
        } catch (Exception e) {
            return "redirect:/orders/" + id + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/user")
    public String getUserOrders(Model model) {
        try {
            // TODO: Get current user ID from session
            long userId = 1L;
            List<CustomOrder> orders = orderService.getOrdersByUserId(userId);
            model.addAttribute("orders", orders);
            return "orders";
        } catch (Exception e) {
            return "redirect:/orders?error=" + e.getMessage();
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public String getRestaurantOrders(@PathVariable long restaurantId, Model model) {
        try {
            // TODO: Implement getOrdersByRestaurantId in service
            return "redirect:/restaurants/" + restaurantId;
        } catch (Exception e) {
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }
}