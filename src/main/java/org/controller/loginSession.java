package org.controller;


import java.io.IOException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import DataBase.Entity.UserEntity;
import DataBase.Service.UserService;

@WebServlet("/login")
@MultipartConfig
public class loginSession extends HttpServlet {

	private UserService userService = new UserService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	req.setCharacterEncoding("UTF-8");
        System.out.println("Login servlet hit");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println("email = " + email);
        System.out.println("password = " + password);

        //check the email here
        //("test@test.com".equals(email) && "1234".equals(password))
       
        UserEntity user = userService.login(email, password);

//        System.out.println("user = " + user.toString());

        req.getSession().setAttribute("loginUser", user);        
        resp.setStatus(HttpServletResponse.SC_OK);
        
    }
}
