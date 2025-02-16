package org.perscholas.cafe_mvp.controller;


import org.perscholas.cafe_mvp.model.*;
import org.perscholas.cafe_mvp.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private CafeMenuService cafeMenuService;

    @Autowired
    private MenuSectionService menuSectionService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);



    @GetMapping
    public String getMenu(@RequestParam(value= "email", required = false) String email,
                          @RequestParam(value="cartId", required = false) Long cartId,
                          Model model) {

        CafeMenu cafeMenu = cafeMenuService.getCafeMenu();
        logger.info("Adding cafe menu to model: {}", cafeMenu);
        model.addAttribute("cafeMenu", cafeMenu);


        if (email != null && cartId != null) {
            Optional<Customer> customer = customerService.findCustomerByEmail(email);
            if (customer.isPresent()) {
                logger.info("Customer with email: {} found.", customer.get().getEmail());
                Cart cart = customer.get().getCart();
                if (cart == null) {
                    cart = new Cart();
                    cart.setCustomer(customer.get());
                    cartService.saveCart(cart);
                }

                if(cart.getId() == null){
                    throw new IllegalArgumentException("Cart Id can't be null");
                }
                cartId = cart.getId();
                model.addAttribute("cartId", cartId);
                model.addAttribute("email", email);
                logger.info("Cart with ID: {} found.", cartId);
            } else {
                logger.warn("Customer not found.");
            }
        }
        else{
            logger.info("Displaying menu without customer login");
        }
        return "menu";
    }

//    @GetMapping
//    public String showMenu(Model model) {
//        CafeMenu cafeMenu = cafeMenuService.getCafeMenu();
//        logger.info("Adding cafe menu to model: {}", cafeMenu);
//        model.addAttribute("cafeMenu", cafeMenu);
//        return "menu";
//    }

    @PostMapping("/add-section")
    public String addSection(Model model, MenuSection menuSection) {
        menuSectionService.addSectionToMenu(menuSection);
        return "redirect:/menu";
    }

    @PostMapping("add-item/{sectionId}")
    public String addItem(@ModelAttribute MenuItem menuItem, @PathVariable Long sectionId) {
        menuItemService.addItem(sectionId, menuItem);
        return "redirect:/menu";
    }

}
