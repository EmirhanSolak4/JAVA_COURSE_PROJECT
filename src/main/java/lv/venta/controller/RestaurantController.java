package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lv.venta.model.Restaurant;
import lv.venta.model.FoodItem;
import lv.venta.service.IRestaurantService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    @GetMapping
    public String getAllRestaurants(Model model) {
        try {
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            return "restaurants";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "restaurants";
        }
    }

    @GetMapping("/{id}")
    public String getRestaurantById(@PathVariable long id, Model model) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("menu", restaurantService.getMenuByRestaurantId(id));
            return "restaurant-detail";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant-form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        try {
            model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
            return "restaurant-form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @PostMapping
    public String createRestaurant(@Valid @ModelAttribute Restaurant restaurant, 
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "restaurant-form";
        }
        
        try {
            restaurantService.insertRestaurant(restaurant);
            return "redirect:/restaurants?success=Restaurant created successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "restaurant-form";
        }
    }

    @PostMapping("/{id}")
    public String updateRestaurant(@PathVariable long id, 
                                 @Valid @ModelAttribute Restaurant restaurant,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "restaurant-form";
        }
        
        try {
            restaurantService.updateRestaurant(id, restaurant);
            return "redirect:/restaurants?success=Restaurant updated successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "restaurant-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteRestaurant(@PathVariable long id, Model model) {
        try {
            restaurantService.deleteRestaurant(id);
            return "redirect:/restaurants?success=Restaurant deleted successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @GetMapping("/{id}/menu")
    public String getRestaurantMenu(@PathVariable long id, Model model) {
        try {
            model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
            model.addAttribute("menu", restaurantService.getMenuByRestaurantId(id));
            return "restaurant-menu";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @GetMapping("/{id}/menu/add")
    public String showAddMenuItemForm(@PathVariable long id, Model model) {
        try {
            model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
            model.addAttribute("foodItem", new FoodItem());
            return "menu-item-form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants/" + id + "/menu?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/menu")
    public String addMenuItem(@PathVariable long id, 
                            @Valid @ModelAttribute FoodItem foodItem,
                            BindingResult bindingResult,
                            Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            try {
                model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
                return "menu-item-form";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "redirect:/restaurants/" + id + "/menu?error=" + e.getMessage();
            }
        }
        
        try {
            restaurantService.addFoodItemToRestaurant(id, foodItem);
            return "redirect:/restaurants/" + id + "/menu?success=Menu item added successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
            return "menu-item-form";
        }
    }
}