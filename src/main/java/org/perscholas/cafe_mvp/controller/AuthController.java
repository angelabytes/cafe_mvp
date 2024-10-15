package org.perscholas.cafe_mvp.controller;

import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@SessionAttributes("currentCustomer")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        Optional<Customer> loggedCustomer = customerService.findCustomerByEmail(email);
        if (loggedCustomer.isPresent() && loggedCustomer.get().getPassword().equals(password)) {
            //Store customer in session
            Customer customer = loggedCustomer.get();
            return "redirect:/cart";
        }
        else {
            //Redirects to login-form and displays error
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("logout")
    public String logout(Model model) {
        model.asMap().remove("currentCustomer");
        return "redirect:/cart";
    }
}
