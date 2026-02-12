package org.api;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DataBase.Entity.BookEntity;
import DataBase.Service.DatabaseService;

@WebServlet("/api/search/name")
public class Search extends HttpServlet {

	private DatabaseService service = new DatabaseService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String keyword = req.getParameter("keyword");
        
        System.out.println("name-input : "+ keyword);

        List<BookEntity> results;
		try {
			results = service.searchBook(keyword);
			new ObjectMapper().writeValue(resp.getWriter(), results);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
}
