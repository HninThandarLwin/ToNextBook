package org.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/Home", "/Profile", "/Search"})
public class Page extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	String uri = req.getRequestURI(); 
        // /ToNextBook/Home

        String page = "home";
        
        req.setAttribute("isProfilePage", false);
        if (uri.endsWith("/Profile")) {
        	page = "profile";
        	req.setAttribute("isProfilePage", true);
        }
        if (uri.endsWith("/Search")) page = "search";

        req.setAttribute("page", page);

        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp")
           .forward(req, resp);
    }
}
