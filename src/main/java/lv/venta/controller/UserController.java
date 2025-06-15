package lv.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lv.venta.model.User;
import lv.venta.service.IUserService;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            User user = userService.getUserByEmailAndPassword(email, password);
            if (user != null && user.isEnabled()) {
                // TODO: Add user to session
                return "redirect:/?success=Login successful";
            }
            model.addAttribute("error", "Invalid credentials or account disabled");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        try {
            userService.insertUser(user);
            return "redirect:/users/login?success=Registration successful";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        try {
            // TODO: Get current user ID from session
            User user = userService.getUserById(1L);
            model.addAttribute("user", user);
            return "user-profile";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/users/login?error=" + e.getMessage();
        }
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute User user,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "user-profile";
        }
        
        try {
            userService.updateUser(user.getId(), user);
            return "redirect:/users/profile?success=Profile updated successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user-profile";
        }
    }

    @GetMapping("/admin")
    public String showAdminPanel(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "admin-panel";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/users/login?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/enable")
    public String enableUser(@PathVariable long id, @RequestParam boolean enabled, Model model) {
        try {
            userService.enableUser(id, enabled);
            return "redirect:/users/admin?success=User status updated successfully";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/users/admin?error=" + e.getMessage();
        }
    }

    @GetMapping("/{role}")
    public String getUsersByRole(@PathVariable String role, Model model) {
        try {
            List<User> users = userService.getUsersByRole(role);
            model.addAttribute("users", users);
            model.addAttribute("role", role);
            return "users-list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/users/admin?error=" + e.getMessage();
        }
    }
}