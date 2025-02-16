package org.perscholas.cafe_mvp.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.service.CartService;
import org.perscholas.cafe_mvp.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute ("customer") Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        customer.setPassword(customer.getPassword().trim());
        customerService.registerCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("customer", null);
        return "redirect:/";
    }
}
