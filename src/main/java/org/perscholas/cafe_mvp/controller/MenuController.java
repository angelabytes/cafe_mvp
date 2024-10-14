package org.perscholas.cafe_mvp.controller;


import org.perscholas.cafe_mvp.model.CafeMenu;
import org.perscholas.cafe_mvp.model.MenuItem;
import org.perscholas.cafe_mvp.model.MenuSection;
import org.perscholas.cafe_mvp.service.CafeMenuService;
import org.perscholas.cafe_mvp.service.MenuItemService;
import org.perscholas.cafe_mvp.service.MenuSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private CafeMenuService cafeMenuService;

    @Autowired
    private MenuSectionService menuSectionService;

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String getMenu(Model model) {
        CafeMenu cafeMenu = cafeMenuService.getCafeMenu();
        logger.info("Adding cafe menu to model: {}", cafeMenu);
        model.addAttribute("cafemenu", cafeMenu);
        return "menu";
    }

    @PostMapping("/add-section")
    public String addSection(Model model, MenuSection menuSection) {
        menuSectionService.addSectionToMenu(menuSection);
        return "redirect:/menu";
    }

    @PostMapping("add-item/{sectionId}")
    public String addItem(@ModelAttribute MenuItem menuItem, @PathVariable Long sectionId) {
        menuItemService.addItem(sectionId, menuItem);
        return "redirect:/menu";
    }

}
