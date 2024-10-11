package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity

public class MenuSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuItem> items;
}
