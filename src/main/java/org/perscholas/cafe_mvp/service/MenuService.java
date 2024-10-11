package org.perscholas.cafe_mvp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.perscholas.cafe_mvp.model.CafeMenu;
import org.perscholas.cafe_mvp.model.MenuItem;
import org.perscholas.cafe_mvp.model.MenuSection;
import org.perscholas.cafe_mvp.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

//    @PostConstruct
//    public void loadMenu() throws IOException {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            CafeMenu cafeMenu = mapper.readValue(new ClassPathResource("menu.json").getInputStream(), CafeMenu.class);
//            for(MenuSection section : cafeMenu.getMenuSections()) {
//                for(MenuItem item : section.getItems()) {
//                    item.setImageURL(item.getImageURL());
//                }
//            }
//
//            menuRepository.save(cafeMenu);
//            logger.info("Menu loaded successfully: {}", cafeMenu);
//        } catch (IOException e) {
//            logger.error("Failed to load menu", e);
//            throw e;
//        }
//    }

    @PostConstruct
    public void loadMenu() throws IOException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            CafeMenu cafeMenu = mapper.readValue(new ClassPathResource("menu.json").getInputStream(), CafeMenu.class);

            menuRepository.save(cafeMenu);
            logger.info("Menu saved");
        }
        catch(IOException e){
            logger.error(e.getMessage());
            throw e;
        }

    }

    public CafeMenu getCafeMenu() {
        CafeMenu menu = menuRepository.findAll().stream().findFirst().orElse(null);
        if (menu == null) {
            logger.warn("No menu found in the database");
        } else {
            logger.info("Retrieved menu: {}", menu);
        }
        return menu;
    }
}
