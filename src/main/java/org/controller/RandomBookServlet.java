package org.controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import DataBase.Entity.BookEntity;
import DataBase.Service.DatabaseService;

@WebServlet("/RandomBook")
public class RandomBookServlet extends HttpServlet {

    private DatabaseService service = new DatabaseService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        BookEntity book = service.getRandomBook();

        response.setContentType("application/json;charset=UTF-8");

        String json = String.format("""
        {
          "title": "%s",
          "title_En":"%s",
          "status": "%s",
          "description": "%s",
          "image": "%s"
        }
        """,
            book.getBookName_jp(),
            book.getBookName_en(),
            book.getBookStatus(),
            book.getDescription_jp(),
            book.getBookCover()
        );

        response.getWriter().write(json);
    }
}
