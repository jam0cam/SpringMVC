package com.example.controller;

import com.example.dao.Dao;
import com.example.model.Player;
import com.example.security.MyUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 12:57 PM
 */

@Controller
@RequestMapping("/register")
public class RegisterController
{
    @Autowired
    Dao dao;

    @Autowired
    MyUserContext myUserContext;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView main () {
        return new ModelAndView("register", "command", new Player());
    }

    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView onSubmit(@ModelAttribute("command")Player command, BindingResult result) {

        if (!result.hasErrors()){

            Util.trimRegister(command);

            dao.insertRegistration(command);

            myUserContext.authenticateAndSetUser(command);
            return new ModelAndView("redirect:/home");
        } else {
            return new ModelAndView("register", "command", command);
        }
    }

}

