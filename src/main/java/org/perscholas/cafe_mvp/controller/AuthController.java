package org.perscholas.cafe_mvp.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@SessionAttributes("currentCustomer")
public class AuthController {

    private final CustomerService customerService;

    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

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
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        Optional<Customer> loggedCustomer = customerService.findCustomerByEmail(email);
        if (loggedCustomer.isPresent() && loggedCustomer.get().getPassword().equals(password)) {
            //Store customer in session
            Customer customer = loggedCustomer.get();
            session.setAttribute("customer", customer);
            return "redirect:/";
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
        return "redirect:/home";
    }
}
