package lv.venta.model;

import java.util.List;
import java.util.Locale.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Food item name is required")
    @Size(min = 2, max = 100, message = "Food item name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private boolean available;

    @NotNull(message = "Restaurant is required")
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull(message = "Category is required")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToMany(mappedBy = "cart")
    private List<User> users;
}