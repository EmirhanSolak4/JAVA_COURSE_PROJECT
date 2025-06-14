package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.CustomOrder;
import lv.venta.service.ICustomOrderService;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private ICustomOrderService orderService;

    @GetMapping
    public ResponseEntity<List<CustomOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomOrder> getOrderById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(orderService.getOrderById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CustomOrder>> getOrdersByUser(@PathVariable long userId) {
        try {
            return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/place")
    public ResponseEntity<Void> placeOrder(
            @RequestParam long userId,
            @RequestBody List<Long> foodItemIds,
            @RequestParam(required = false) String specialInstructions) {
        try {
            orderService.placeOrder(userId, foodItemIds, specialInstructions);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable long id,
            @RequestParam String status) {
        try {
            orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 