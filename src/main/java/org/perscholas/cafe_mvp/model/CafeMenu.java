package org.perscholas.cafe_mvp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CafeMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("menu_sections")
    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuSection> menuSections = new ArrayList<>();
}
