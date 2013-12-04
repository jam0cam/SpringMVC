package com.example.controller;

import com.example.dao.Dao;
import com.example.security.MyUserContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 2:19 PM
 */
public class BaseController {

    @Autowired
    protected SimpleDateFormat dateFormatter;

    @Autowired
    protected Dao dao;

    @Autowired
    protected MyUserContext myUserContext;
}
