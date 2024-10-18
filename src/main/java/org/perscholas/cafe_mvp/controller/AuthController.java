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

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        customer.setPassword(customer.getPassword().trim());
        customerService.createCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        Optional<Customer> customer = customerService.findCustomerByEmail(email);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            //Store customer in session
            model.addAttribute("customer", customer.get());
            logger.info("Customer logged in with ID {}", customer.get().getId());
            //Check if customer has a cart
            Cart cart = customer.get().getCart();
            if(cart == null) {
                cart = new Cart();
                cart.setCustomer(customer.get());
                cartService.saveCart(cart);
            }
            if(cart.getId() == null){
                throw new IllegalArgumentException("Cart ID can't be null after saving");
            }
            model.addAttribute("cartId", cart.getId());
            logger.info("Cart with ID {} created", cart.getId());
            return "redirect:/menu";
        }
        else {
            //Redirects to log in-form and displays error
            model.addAttribute("error", "Invalid email or password");
            model.addAttribute("customer", null);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("customer", null);
        return "redirect:/";
    }
}
