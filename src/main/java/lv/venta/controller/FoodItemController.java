package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lv.venta.model.FoodItem;
import lv.venta.service.IFoodItemService;
import lv.venta.service.IRestaurantService;
import lv.venta.service.ICategoryService;
@Controller
@RequestMapping("/food-items")
public class FoodItemController {

    @Autowired
    private IFoodItemService foodItemService;
    
    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String getAllFoodItems(Model model) {
        model.addAttribute("foodItems", foodItemService.getAllFoodItems());
        return "food-items";
    }

    @GetMapping("/{id}")
    public String getFoodItemById(@PathVariable long id, Model model) {
        try {
            model.addAttribute("foodItem", foodItemService.getFoodItemById(id));
            return "food-item-detail";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/food-items?error=" + e.getMessage();
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("foodItem", new FoodItem());
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "food-item-form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        try {
            model.addAttribute("foodItem", foodItemService.getFoodItemById(id));
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "food-item-form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/food-items?error=" + e.getMessage();
        }
    }

    @PostMapping
    public String createFoodItem(@Valid @ModelAttribute FoodItem foodItem, 
                               BindingResult bindingResult, 
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "food-item-form";
        }
        
        try {
            foodItemService.insertFoodItem(foodItem);
            return "redirect:/food-items?success=Food item created successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "food-item-form";
        }
    }

    @PostMapping("/{id}")
    public String updateFoodItem(@PathVariable long id, 
                               @Valid @ModelAttribute FoodItem foodItem,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "food-item-form";
        }
        
        try {
            foodItemService.updateFoodItem(id, foodItem);
            return "redirect:/food-items?success=Food item updated successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("restaurants", restaurantService.getAllRestaurants());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "food-item-form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteFoodItem(@PathVariable long id, Model model) {
        try {
            foodItemService.deleteFoodItemById(id);
            return "redirect:/food-items?success=Food item deleted successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/food-items?error=" + e.getMessage();
        }
    }

    @GetMapping("/category/{category}")
    public String getFoodItemsByCategory(@PathVariable String category, Model model) {
        try {
            model.addAttribute("foodItems", foodItemService.getFoodItemsByCategory(category));
            model.addAttribute("category", category);
            return "food-items";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/food-items?error=" + e.getMessage();
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public String getFoodItemsByRestaurant(@PathVariable long restaurantId, Model model) {
        try {
            model.addAttribute("foodItems", foodItemService.getFoodItemsByRestaurantId(restaurantId));
            return "food-items";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/restaurants?error=" + e.getMessage();
        }
    }

    @GetMapping("/search")
    public String searchFoodItems(@RequestParam String keyword, Model model) {
        try {
            model.addAttribute("foodItems", foodItemService.searchFoodItemsByName(keyword));
            model.addAttribute("keyword", keyword);
            return "food-items";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/food-items?error=" + e.getMessage();
        }
    }
}