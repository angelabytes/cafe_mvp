package org.perscholas.cafe_mvp.service;

import org.perscholas.cafe_mvp.model.Cart;
import org.perscholas.cafe_mvp.model.CartItem;
import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.model.MenuItem;
import org.perscholas.cafe_mvp.repository.CartRepository;
import org.perscholas.cafe_mvp.repository.MenuItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addItemToCart(Customer customer, Long itemId, int quantity) {

        //Check for invalid item or quantity
        MenuItem item = menuItemRepository.findById(itemId).orElse(null);
        if(item == null || quantity <= 0) {
            logger.warn("Invalid item or quantity: itemId={}, quantity={}", itemId, quantity);
            return;
        }

        //Get cart for the customer
        Cart cart = customer.getCart();
        if(cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            customer.setCart(cart);
            cartRepository.save(cart);
        }

        //Create a cart item and add it to the customer's cart
        CartItem cartItem = new CartItem(null, item, quantity, cart);
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Customer customer, Long itemId) {
        Cart cart = customer.getCart();
        if(cart != null) {
            boolean removed = cart.getItems().removeIf(cartItem -> cartItem.getMenuItem().getId().equals(itemId));
            cartRepository.save(cart);
            if(!removed){
                logger.info("Item not found for removal: itemId={}", itemId);
            }
        }

    }

    @Transactional
    public void updateItemQuantity(Customer customer, Long itemId, int quantity) {
        Cart cart = customer.getCart();
        if(cart != null) {
            for(CartItem cartItem : cart.getItems()) {
                if(cartItem.getMenuItem().getId().equals(itemId)) {
                    if(quantity <= 0) {
                        removeItemFromCart(customer, itemId);
                    }
                    else{
                        cartItem.setQuantity(quantity);
                    }
                    cartRepository.save(cart);
                    return;
                }
            }
        }
    }

    public double getTotalForCart(Customer customer) {
        Cart cart = customer.getCart();
        return (cart !=null ) ? cart.calculateGrandTotal() : 0.00;
    }
}
