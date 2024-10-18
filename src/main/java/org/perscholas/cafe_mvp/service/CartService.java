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

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    Logger logger = LoggerFactory.getLogger(CartService.class);

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Optional<Cart> getCart(Long id) {
        return cartRepository.findById(id);
    }




    public void addItemToCart(Long cartId, Long menuItemId, int quantity) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new RuntimeException("Menu item not found"));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Menu item not found"));

        //goes through the list of cart items
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getMenuItem().getId().equals(menuItemId))
                .findFirst();
        if(existingCartItem.isPresent()) {
            //update if item is already in the cart
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setMenuItem(menuItem);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.addItem(newItem);

        }
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

    public BigDecimal getTotalForCart(Customer customer) {
        Cart cart = customer.getCart();
        return (cart !=null ) ? cart.calculateGrandTotal() : BigDecimal.ZERO;
    }
}
