package org.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DataBase.DTO.FavSearchResultDTO;
import DataBase.Entity.FavoriteEntity;
import DataBase.Entity.UserEntity;
import DataBase.Service.DatabaseService;

@WebServlet("/similarSearch")
public class SimilarSearchServlet extends HttpServlet {

	private DatabaseService service = new DatabaseService();
	static ObjectMapper mapper = new ObjectMapper();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	 req.setCharacterEncoding("UTF-8");
         resp.setCharacterEncoding("UTF-8");
         resp.setContentType("application/json;charset=UTF-8");

         // get params
         String[] bookArray = req.getParameterValues("books");

         // validation
         if (bookArray == null || bookArray.length == 0) {
             mapper.writeValue(resp.getWriter(), List.of()); // return empty JSON array
             return;
         }

         // convert to List
         List<String> books = Arrays.asList(bookArray);

         // call service
         List<FavoriteEntity> resulteEntity = service.searchBookByFav(books);
         
         List<FavSearchResultDTO> response = new ArrayList<>();
         
         for (FavoriteEntity fav : resulteEntity) {
        	 FavSearchResultDTO dto = new FavSearchResultDTO();
        	    dto.userId = fav.getId(); // userId
        	    dto.userName = service.getEntityById(UserEntity.class, fav.getId()).getUserName(); // 👈 already exists or easy query
        	    dto.readingStatus = fav.getReadingStatus();
        	    dto.books = fav.getBooks();

        	    response.add(dto);
        	}

         // return JSON
         mapper.writeValue(resp.getWriter(), response);
     }
}
