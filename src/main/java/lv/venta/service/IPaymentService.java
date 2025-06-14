package lv.venta.service;

import lv.venta.model.Payment;

public interface IPaymentService {

	Payment getPaymentByOrderId(long orderId) throws Exception;

	void processPayment(long orderId, double amount, String method) throws Exception;

	boolean isPaymentSuccessful(long orderId) throws Exception;

}
