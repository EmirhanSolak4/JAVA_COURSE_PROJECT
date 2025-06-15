package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lv.venta.model.Payment;
import lv.venta.service.IPaymentService;
import lv.venta.service.ICustomOrderService;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private ICustomOrderService orderService;

    @GetMapping("/order/{orderId}")
    public String showPaymentForm(@PathVariable long orderId, Model model) {
        try {
            model.addAttribute("order", orderService.getOrderById(orderId));
            model.addAttribute("payment", new Payment());
            return "payment-form";
        } catch (Exception e) {
            return "redirect:/orders/" + orderId + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam long orderId, @RequestParam double amount, @RequestParam String method) {
        try {
            paymentService.processPayment(orderId, amount, method);
            return "redirect:/orders/" + orderId + "?success=Payment processed successfully";
        } catch (Exception e) {
            return "redirect:/payments/order/" + orderId + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/{id}/status")
    public String getPaymentStatus(@PathVariable long id, Model model) {
        try {
            Payment payment = paymentService.getPaymentByOrderId(id);
            model.addAttribute("payment", payment);
            return "payment-status";
        } catch (Exception e) {
            return "redirect:/orders?error=" + e.getMessage();
        }
    }
}