package org.perscholas.cafe_mvp.service;

import org.perscholas.cafe_mvp.model.CafeMenu;
import org.perscholas.cafe_mvp.model.MenuSection;
import org.perscholas.cafe_mvp.repository.CafeMenuRepository;
import org.perscholas.cafe_mvp.repository.MenuSectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuSectionService {
    @Autowired
    private MenuSectionRepository menuSectionRepository;

    @Autowired
    private CafeMenuRepository cafeMenuRepository;

    private final Logger logger = LoggerFactory.getLogger(MenuSectionService.class);

    public void addSectionToMenu(MenuSection menuSection) {
        CafeMenu cafeMenu = cafeMenuRepository.findAll().stream().findFirst().orElse(null);
        if(cafeMenu != null) {
            menuSection.setCafeMenu(cafeMenu);
            menuSectionRepository.save(menuSection);
            cafeMenu.getMenuSections().add(menuSection);
            cafeMenuRepository.save(cafeMenu);
            logger.info("Added section to the menu: {}", menuSection);
        }
        else{
            logger.warn("No cafe menu found");
        }
    }

    public void  removeSectionFromMenu(Long sectionId) {
        menuSectionRepository.deleteById(sectionId);
        logger.info("Removed section from the menu: {}", sectionId);
    }

}
