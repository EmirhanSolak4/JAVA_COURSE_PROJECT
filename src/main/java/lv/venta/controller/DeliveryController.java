package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.Delivery;
import lv.venta.service.IDeliveryService;
import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private IDeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(deliveryService.getDeliveryById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignDelivery(
            @RequestParam long orderId,
            @RequestParam String deliveryAddress) {
        try {
            deliveryService.assignDeliveryToOrder(orderId, deliveryAddress);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateDeliveryStatus(
            @PathVariable long id,
            @RequestParam String status) {
        try {
            deliveryService.updateDeliveryStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/courier/{courierName}")
    public ResponseEntity<List<Delivery>> getDeliveriesByCourier(@PathVariable String courierName) {
        return ResponseEntity.ok(deliveryService.getDeliveriesByCourierName(courierName));
    }
}