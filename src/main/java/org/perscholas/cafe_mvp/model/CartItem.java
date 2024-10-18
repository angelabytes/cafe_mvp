package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;


    public BigDecimal getPrice() {
        return new BigDecimal(quantity).multiply(menuItem.getPrice());
    }

}