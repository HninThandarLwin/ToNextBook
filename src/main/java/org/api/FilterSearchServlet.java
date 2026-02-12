package org.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DataBase.DTO.FilterRequestDTO;
import DataBase.Service.DatabaseService;

@WebServlet("/api/search/filter")
public class FilterSearchServlet extends HttpServlet {

	private DatabaseService service = new DatabaseService();
	static ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");

		// Read JSON body
		FilterRequestDTO data = mapper.readValue(req.getInputStream(), FilterRequestDTO.class);

		List<Integer> genreIds = data.genres;
		List<Integer> tagIds = data.tags;

		//Search in database
		var results = service.searchBooksByGenresAndTags(genreIds, tagIds);

		if (results.isEmpty()) {
			mapper.writeValue(resp.getWriter(),
					Map.of("message", "選択されたジャンルとタグに一致する小説は見つかりませんでした。"));
			return; 
		}
		//Return JSON To Front
		mapper.writeValue(resp.getWriter(), results);
	}
}
