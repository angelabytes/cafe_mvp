package org.perscholas.cafe_mvp.service;

import org.perscholas.cafe_mvp.model.MenuItem;
import org.perscholas.cafe_mvp.model.MenuSection;
import org.perscholas.cafe_mvp.repository.MenuItemRepository;
import org.perscholas.cafe_mvp.repository.MenuSectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuSectionRepository menuSectionRepository;

    private final Logger logger = LoggerFactory.getLogger(MenuItemService.class);


    public void addItem(Long sectionId, MenuItem menuItem) {
        Optional<MenuSection> section = Optional.ofNullable(menuSectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Menu section not found")));
        section.get().getItems().add(menuItem);
        menuItem.setMenuSection(section.get());
        menuItemRepository.save(menuItem);
        logger.info("Added menu item: {}", menuItem);
    }
}
