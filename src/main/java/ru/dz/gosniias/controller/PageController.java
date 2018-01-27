package ru.dz.gosniias.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.dz.gosniias.service.IFunctionService;

/**
 *
 * @author vassaeve
 */
@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    IFunctionService iFunctions;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Locale locale, Model model) {
        return "functions";
    }

    @RequestMapping(value = "run-groovy", method = RequestMethod.GET)
    public String run_groovy(Locale locale, Model model) {
        return "run-groovy";
    }
}
