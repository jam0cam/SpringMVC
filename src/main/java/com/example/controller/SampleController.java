package com.example.controller;

import com.example.dao.Dao;
import com.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
@RequestMapping("/Sample")
public class SampleController {

    @Autowired
    Dao dao;

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public @ResponseBody Person getById(@PathVariable("id") String id) {
        return dao.getByResultId(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody ModelAndView home () {
        Person cmd = dao.getByResultId("1");

        return new ModelAndView("index", "command", cmd);
    }

}
