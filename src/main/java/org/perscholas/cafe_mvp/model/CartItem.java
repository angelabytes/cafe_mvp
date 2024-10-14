package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    private int quantity;


    public double getPrice() {
        return quantity * menuItem.getPrice();
    }


}
