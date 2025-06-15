package lv.venta.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.enums.DeliveryStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Courier name is required")
    @Size(min = 2, max = 100, message = "Courier name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-ZğüşıöçĞÜŞİÖÇ\\s]+$", message = "Courier name can only contain letters and spaces")
    private String courierName;

    @NotNull(message = "Estimated delivery time is required")
    @Future(message = "Estimated delivery time must be in the future")
    private LocalDateTime estimatedTime;

    @NotBlank(message = "Delivery address is required")
    @Size(min = 5, max = 200, message = "Delivery address must be between 5 and 200 characters")
    private String deliveryAddress;

    private boolean delivered;
    
    @NotNull(message = "Delivery status is required")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @NotNull(message = "Order is required")
    @OneToOne
    @JoinColumn(name = "order_id")
    private CustomOrder order;
}
