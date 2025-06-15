package lv.venta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.enums.PaymentMethod;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @NotNull(message = "Payment method is required")
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private boolean success;

    @NotNull(message = "Order is required")
    @OneToOne
    @JoinColumn(name = "order_id")
    private CustomOrder order;
}
