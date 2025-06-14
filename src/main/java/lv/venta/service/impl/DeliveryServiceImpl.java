package lv.venta.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.enums.DeliveryStatus;
import lv.venta.model.CustomOrder;
import lv.venta.model.Delivery;
import lv.venta.repository.ICustomOrderRepository;
import lv.venta.repository.IDeliveryRepository;
import lv.venta.service.IDeliveryService;

@Service
public class DeliveryServiceImpl implements IDeliveryService {

    @Autowired
    private IDeliveryRepository deliveryRepo;

    @Autowired
    private ICustomOrderRepository orderRepo;

    @Override
    public List<Delivery> getAllDeliveries() {
        return (List<Delivery>) deliveryRepo.findAll();
    }

    @Override
    public Delivery getDeliveryById(long id) throws Exception {
        return deliveryRepo.findById(id)
                .orElseThrow(() -> new Exception("Delivery not found with id: " + id));
    }

    @Override
    public void assignDeliveryToOrder(long orderId, String deliveryAddress) throws Exception {
        CustomOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));

        if (order.getDelivery() != null) {
            throw new Exception("Delivery already assigned to this order.");
        }

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryAddress(deliveryAddress);
        delivery.setCourierName("Not Assigned");
        delivery.setEstimatedTime(LocalDateTime.now().plusHours(1));
        delivery.setStatus(DeliveryStatus.PREPARING);

        deliveryRepo.save(delivery);
    }

    @Override
    public void updateDeliveryStatus(long deliveryId, String status) throws Exception {
        Delivery delivery = getDeliveryById(deliveryId);
        DeliveryStatus newStatus = DeliveryStatus.valueOf(status.toUpperCase());
        delivery.setStatus(newStatus);
        deliveryRepo.save(delivery);
    }

    @Override
    public List<Delivery> getDeliveriesByCourierName(String courierName) {
        List<Delivery> results = new ArrayList<>();
        for (Delivery d : deliveryRepo.findAll()) {
            if (d.getCourierName() != null && d.getCourierName().equalsIgnoreCase(courierName)) {
                results.add(d);
            }
        }
        return results;
    }
}
