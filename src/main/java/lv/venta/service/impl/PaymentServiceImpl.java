package lv.venta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.enums.PaymentMethod;
import lv.venta.model.CustomOrder;
import lv.venta.model.Payment;
import lv.venta.repository.ICustomOrderRepository;
import lv.venta.repository.IPaymentRepository;
import lv.venta.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepo;

    @Autowired
    private ICustomOrderRepository orderRepo;

    @Override
    public Payment getPaymentByOrderId(long orderId) throws Exception {
        CustomOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));

        Payment payment = order.getPayment();
        if (payment == null) {
            throw new Exception("No payment associated with this order.");
        }

        return payment;
    }

    @Override
    public void processPayment(long orderId, double amount, String method) throws Exception {
        CustomOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));

        if (order.getPayment() != null) {
            throw new Exception("Payment already exists for this order.");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setMethod(PaymentMethod.valueOf(method.toUpperCase()));
        payment.setSuccess(true); // success=true simüle ediliyor (gerçek kart çekimi yok)

        paymentRepo.save(payment);
    }

    @Override
    public boolean isPaymentSuccessful(long orderId) throws Exception {
        return getPaymentByOrderId(orderId).isSuccess();
    }
}
