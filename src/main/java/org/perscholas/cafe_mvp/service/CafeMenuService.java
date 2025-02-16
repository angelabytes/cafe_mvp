package org.perscholas.cafe_mvp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.perscholas.cafe_mvp.model.CafeMenu;
import org.perscholas.cafe_mvp.model.MenuSection;
import org.perscholas.cafe_mvp.repository.CafeMenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class CafeMenuService {

    private static final Logger logger = LoggerFactory.getLogger(CafeMenuService.class);

    @Autowired
    private CafeMenuRepository cafeMenuRepository;

    @Autowired
    private MenuSectionService menuSectionService;

    @PostConstruct
    public void loadMenu() throws IOException {
        if(cafeMenuRepository.count() > 0) {
            logger.info("Menu already exists");
            return;
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            CafeMenu cafeMenu = mapper.readValue(new ClassPathResource("menu.json").getInputStream(), CafeMenu.class);
            cafeMenuRepository.save(cafeMenu);
            logger.info("Menu saved successfully: {}", cafeMenu);
        }
        catch(IOException e){
            logger.error("Error loading menu from the JSON: {}",e.getMessage(), e);
            throw new RuntimeException("Failed to load menu from the JSON: " + e);
        }
    }

    public CafeMenu getCafeMenu() {
        CafeMenu menu = cafeMenuRepository.findAll().stream().findFirst().orElse(null);
        if (menu == null) {
            logger.warn("No menu found in the database");
        } else {
            logger.info("Retrieved menu: {}", menu);
        }
        return menu;
    }

    public void addMenuSection(MenuSection menuSection) {
        menuSectionService.addSectionToMenu(menuSection);
    }
}
