package org.perscholas.cafe_mvp.controller;


import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String cart(Model model, @SessionAttribute("currentCustomer") Customer customer) {
        Cart cart = customer.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("total", cartService.getTotalForCart(customer));
        return "cart";
    }

    @PostMapping("/add/{itemId}")
    public String add(@SessionAttribute("currentCustomer")Customer customer, @PathVariable Long itemId, @RequestParam int quantity) {
        cartService.addItemToCart(customer, itemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/add/remove/{itemId}")
    public String remove(@SessionAttribute("currentCustomer")Customer customer, @PathVariable Long itemId) {
        cartService.removeItemFromCart(customer, itemId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{itemId}")
    public String update(@SessionAttribute("currentCustomer")Customer customer, @PathVariable Long itemId, @RequestParam int quantity) {
        cartService.updateItemQuantity(customer, itemId, quantity);
        return "redirect:/cart";
    }
}
