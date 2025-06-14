package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.Payment;
import lv.venta.service.IPaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrder(@PathVariable long orderId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/process")
    public ResponseEntity<Void> processPayment(
            @RequestParam long orderId,
            @RequestParam double amount,
            @RequestParam String method) {
        try {
            paymentService.processPayment(orderId, amount, method);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/order/{orderId}/status")
    public ResponseEntity<Boolean> checkPaymentStatus(@PathVariable long orderId) {
        try {
            return ResponseEntity.ok(paymentService.isPaymentSuccessful(orderId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 