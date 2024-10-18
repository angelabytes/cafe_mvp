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
import java.util.List;
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
        //get menu item
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        //get the cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        //checks if item is already in the cart
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getMenuItem().getId().equals(menuItemId))
                .findFirst();

        if(existingCartItem.isPresent()) {
            //updates item's quantity if it is already in the cart
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else{
            //create a new cart item if it isn't in the cart
            CartItem newItem = new CartItem();
            newItem.setMenuItem(menuItem);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
//            cart.addItem(newItem);
            cart.getItems().add(newItem);

        }
        cartRepository.save(cart);
    }
    @Transactional
    public void removeItemFromCart(Long cartId, Long menuItemId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem itemToRemove = cart.getItems().stream()
                .filter(cartItem -> cartItem.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

//    public void removeItemFromCart(Customer customer, Long itemId) {
//        Cart cart = customer.getCart();
//        if(cart != null) {
//            boolean removed = cart.getItems().removeIf(cartItem -> cartItem.getMenuItem().getId().equals(itemId));
//            cartRepository.save(cart);
//            if(!removed){
//                logger.info("Item not found for removal: itemId={}", itemId);
//            }
//        }
//
//    }

//    @Transactional
//    public void updateItemQuantity(Customer customer, Long itemId, int quantity) {
//        Cart cart = customer.getCart();
//        if(cart != null) {
//            for(CartItem cartItem : cart.getItems()) {
//                if(cartItem.getMenuItem().getId().equals(itemId)) {
//                    if(quantity <= 0) {
//                        removeItemFromCart(customer, itemId);
//                    }
//                    else{
//                        cartItem.setQuantity(quantity);
//                    }
//                    cartRepository.save(cart);
//                    return;
//                }
//            }
//        }
//    }

    public List<MenuItem> getAllMenuItems(){
        return menuItemRepository.findAll();
    }

    public BigDecimal getTotalForCart(Customer customer) {
        Cart cart = customer.getCart();
        return (cart !=null ) ? cart.calculateGrandTotal() : BigDecimal.ZERO;
    }
}
