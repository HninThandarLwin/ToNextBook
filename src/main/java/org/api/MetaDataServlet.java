package org.api;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import DataBase.DTO.GenreTagDTO;
import DataBase.Service.DatabaseService;

@WebServlet("/api/meta")
public class MetaDataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ObjectMapper mapper = new ObjectMapper();
    private final DatabaseService service = new DatabaseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json; charset=UTF-8");

        String type = req.getParameter("type");
        String lang = req.getParameter("lang");
        if (lang == null) lang = "en";

//        System.out.println("MetaServlet hit");
//        System.out.println("type = " + type);
//        System.out.println("lang = " + lang);

        try {
            if ("genre".equals(type)) {
                List<GenreTagDTO> genres = service.getAllGenres(lang);

//                System.out.println("=== GENRE DTO LIST ===");
//                genres.forEach(g ->
//                    System.out.println("id=" + g.getId() + ", name=" + g.getName())
//                );

                mapper.writeValue(resp.getWriter(), genres);
                return;
            }

            if ("tag".equals(type)) {
                List<GenreTagDTO> tags = service.getAllTags(lang);

//                System.out.println("=== TAG DTO LIST ===");
//                tags.forEach(t ->
//                    System.out.println("id=" + t.getId() + ", name=" + t.getName())
//                );

                mapper.writeValue(resp.getWriter(), tags);
                return;
            }

            // ❌ invalid type
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(
                resp.getWriter(),
                new ErrorResponse("invalid type")
            );

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(
                resp.getWriter(),
                new ErrorResponse("server error")
            );
        }
    }

    private record ErrorResponse(String error) {}
}
