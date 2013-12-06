package com.example.controller;

import com.example.dao.Dao;
import com.example.exception.EmailExistsException;
import com.example.exception.InvalidUserNameOrPasswordException;
import com.example.model.HttpResponse;
import com.example.security.MyUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @ExceptionHandler({ InvalidUserNameOrPasswordException.class })
    @ResponseBody
    public HttpResponse loginException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        HttpResponse errorResponse = new HttpResponse();
        errorResponse.setMessage("Invalid Email or Password");
        errorResponse.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        return errorResponse;
    }

    @ExceptionHandler({ EmailExistsException.class })
    @ResponseBody
    public HttpResponse emailExistsException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        HttpResponse errorResponse = new HttpResponse();
        errorResponse.setMessage("Email already taken");
        errorResponse.setCode(HttpServletResponse.SC_CONFLICT);
        return errorResponse;
    }
}
