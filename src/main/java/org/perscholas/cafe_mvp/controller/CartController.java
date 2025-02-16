package org.perscholas.cafe_mvp.controller;


import jakarta.servlet.http.HttpSession;
import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.model.MenuItem;
import org.perscholas.cafe_mvp.service.CartService;
import org.perscholas.cafe_mvp.service.MenuItemService;
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

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String viewCart(@RequestParam(required = false) Long cartId, HttpSession session, Model model) {
        //Check session for cartId, if it doesn't exist attempts to create one
        if(cartId == null){
            cartId = (Long) session.getAttribute("cartId");
        }

        //Checks again if a cart was created but throws an error if it wasn't
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
    public String addItemToCart(@RequestParam Long menuItemId,
                                @RequestParam int quantity,
                                HttpSession session,
                                Model model) {


        Optional<MenuItem> menuItem = menuItemService.findMenuItemById(menuItemId);
        if(menuItem.isEmpty()){
            model.addAttribute("error", "Menu item is not found");
            return "redirect:/menu";
        }

        //Get cartId from current session
        Long cartId = (Long) session.getAttribute("cartId");

        if(cartId == null){
            Cart newCart = new Cart();
            cartService.saveCart(newCart);
            cartId = newCart.getId();
            session.setAttribute("cartId", cartId);
            logger.info("New cart with ID: {} was created.", cartId);
        }

        try{
            logger.info("Adding Item {} with a quantity of {} to Cart with id {}", menuItemId, quantity, cartId);
            cartService.addItemToCart(cartId, menuItemId, quantity);
        }
        catch (RuntimeException e){
            logger.error("Error adding item to cart: {}", e.getMessage());
            model.addAttribute("error", "Could not add item to cart. Please try again.");
            return "redirect:/menu";
        }

        model.addAttribute("menuItemId", menuItemId);

        //Fetch updated cart
        Optional<Cart> cart = cartService.getCart(cartId);
        if(cart.isPresent()) {
            model.addAttribute("cart", cart.get());
            model.addAttribute("cartId", cartId);
            return "cart";
        }
        else {
            model.addAttribute("error", "Cart not found");
            return "redirect:/";
        }
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam Long menuItemId, @RequestParam Long cartId, Model model) {
        try{
            cartService.removeItemFromCart(cartId, menuItemId);
        }
        catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }


    @PostMapping("/update")
    public String updateItemCart(@RequestParam Long menuItemId,
                                 @RequestParam int quantity,
                                 @RequestParam Long cartId,
                                 Model model) {
        try{
            cartService.updateItemsInCart(cartId, menuItemId, quantity);
        }
        catch(RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }
}
