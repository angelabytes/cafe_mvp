package org.perscholas.cafe_mvp.controller;


import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping
    public String viewCart(@RequestParam Long cartId, Model model) {
//        logger.info("Viewing Cart with id {}", cartId);
//        Optional<Cart> cart = cartService.getCart(cartId);
//        if (cart.isPresent()) {
//            model.addAttribute("cart", cart.get());
//            return "cart";
//        }
//        else {
//            model.addAttribute("error", "Cart not found");
//            return "redirect:/";
//        }
        logger.info("Viewing Cart with id {}", cartId);
        if(cartId == null){
            model.addAttribute("error", "Cart ID is required to view the cart");
            return "redirect:/";
        }
        Optional<Cart> cart = cartService.getCart(cartId);
        if(cart.isPresent()) {
            model.addAttribute("cart", cart.get());
            return "cart";
        }
        else {


            model.addAttribute("error", "Cart not found");
            return "redirect:/";
        }
    }

    @PostMapping("/add")
    public String addItemToCart(@RequestParam(required = false) Long cartId,
                                @RequestParam Long menuItemId,
                                @RequestParam int quantity,
                                @RequestParam(required = false) String email,
                                Model model) {

//        if(cartId == null) {
//            model.addAttribute("error", "Cart id is null");
//            return "redirect:/";
//        }
        //if cart wasn't created because the user isn't logged in, create one
        if(cartId == null){
            Cart newCart = new Cart();
            cartService.saveCart(newCart);
            cartId = newCart.getId();;
            logger.info("New cart with ID: {} was created.", cartId);
        }

        logger.info("Adding Item {} with a quantity of {} to Cart with id {}", menuItemId, quantity, cartId);
        cartService.addItemToCart(cartId, menuItemId, quantity);
        Optional<Cart> cart = cartService.getCart(cartId);
        if(cart.isPresent()) {
            model.addAttribute("cart", cart.get());
//            model.addAttribute("email", email);
            model.addAttribute("cartId", cartId);
            return "cart";
        }
        else {
            model.addAttribute("error", "Cart not found");
            return "redirect:/";
        }
    }
}
