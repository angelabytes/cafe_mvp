package org.perscholas.cafe_mvp.controller;

import org.perscholas.cafe_mvp.model.CafeMenu;
import org.perscholas.cafe_mvp.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("cafemenu", new CafeMenu());
        return "home";
    }

//
//    @GetMapping("/menu")
//    public String getMenu(Model model) {
//        model.addAttribute("cafemenu", menuService.getCafeMenu());
//        return "menu";
//    }

    @GetMapping("/menu")
    public String getMenu(Model model) {
        CafeMenu cafeMenu = menuService.getCafeMenu();
        logger.info("Adding cafeMenu to model: {}", cafeMenu);
        model.addAttribute("cafemenu", cafeMenu);
        return "menu";
    }
}
