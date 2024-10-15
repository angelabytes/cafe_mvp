package org.perscholas.cafe_mvp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Base64;


@Data
@Entity

public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private double price;

    @JsonProperty("image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "menu_section_id")
    private MenuSection menuSection;


    /** Keep track of removed items without actually deleting them from the database
     ** Possible use is seasonal menu
     **/
    private boolean isDeleted = false;

}
