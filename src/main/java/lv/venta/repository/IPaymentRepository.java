package lv.venta.repository;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Payment;

public interface IPaymentRepository extends CrudRepository<Payment, Long> {

}
