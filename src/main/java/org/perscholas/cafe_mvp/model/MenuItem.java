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

    private String name;
    private String description;
    private double price;


//    @Lob
    @JsonProperty("image_url")
    private String imageUrl;
//    private byte[] imageURL;

//    public void setImageURL(String imageURL) {
//        try{
//            this.imageURL = Base64.getDecoder().decode(imageURL);
//        }
//        catch(Exception e){
//            throw new RuntimeException("Failed to set image URL", e);
//        }
//    }
//
//    public String getImageURL() {
//        if(imageURL == null){
//            return null;
//        }
//        else{
//            return Base64.getEncoder().encodeToString(this.imageURL);
//        }
//    }

}
