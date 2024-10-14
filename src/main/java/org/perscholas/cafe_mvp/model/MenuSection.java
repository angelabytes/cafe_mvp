package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class MenuSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name ="cafe_menu_id")
    private CafeMenu cafeMenu;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuItem> items = new ArrayList<>();


    public CafeMenu getCafeMenu() {
        return cafeMenu;
    }
    public void setCafeMenu(CafeMenu cafeMenu){
        this.id = cafeMenu.getId();
    }

}
