package org.perscholas.cafe_mvp.controller;

import jakarta.servlet.http.HttpSession;
import org.perscholas.cafe_mvp.model.CafeMenu;
import org.perscholas.cafe_mvp.model.Customer;
import org.perscholas.cafe_mvp.service.CafeMenuService;
import org.perscholas.cafe_mvp.service.CustomerService;
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
    private CafeMenuService cafeMenuService;

    @Autowired
    private CustomerService customerService;

//    @GetMapping("/")
//    public String getHome(Model model) {
//        model.addAttribute("cafemenu", new CafeMenu());
//        return "home";
//    }

    @GetMapping("/")
    public String getHome(Model model, HttpSession session) {
        Customer currentCustomer = (Customer) session.getAttribute("currentCustomer");
        model.addAttribute("cafemenu", new CafeMenu());
        model.addAttribute("currentCustomer", currentCustomer);
        return "home";
    }


//    @GetMapping("/menu")
//    public String getMenu(Model model) {
//        CafeMenu cafeMenu = cafeMenuService.getCafeMenu();
//        logger.info("Adding cafeMenu to model: {}", cafeMenu);
//        model.addAttribute("cafemenu", cafeMenu);
//        return "menu";
//    }


    //Later use
    /*
     *
     * @GetMapping("/contact")
     * public String contact() {
     *     return "contact";
     * }
     *@GetMapping("/about")
     * public String getAbout() {
     * return "about";
     * }
     */

}
