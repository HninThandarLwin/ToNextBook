package DataBase.Util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import jakarta.persistence.EntityManager;

import org.NextBook.App;
import org.NextBook.SyousetsuApiTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DataBase.Entity.BookEntity;
import DataBase.Entity.FavoriteEntity;
import DataBase.Entity.GenreEntity;
import DataBase.Entity.TagEntity;
import DataBase.Entity.UserEntity;
import DataBase.Service.DatabaseService;

public class DataUtil {		// adding jsons data to database as primary data
	
	static String apiUrl = "https://api.syosetu.com/novelapi/api/?out=json&lim=5";   
	static EntityManager em = EntityManagerFactoryProvider.createEntityManager();
	static DatabaseService databaseService = new DatabaseService();
	static ObjectMapper mapper = new ObjectMapper();
	
	//使ってない
	public static void addingBooks_API() {
        JSONArray resultArray = SyousetsuApiTest.get(apiUrl);  // get method retrun book entity jsonArray
        
        String title = "Default",author = "Default", description = "Default", link = "Default";
        int status = 1;
        BookStatus bookStatus = BookStatus.ONGOING;

        for (int i = 1; i < resultArray.length(); i++) { 
            JSONObject novel = resultArray.getJSONObject(i);
            title = novel.getString("title");
            author = novel.getString("writer");
            status = novel.getInt("end");	// syousetsu Api returns 1 or 0
            description = novel.getString("story");
            link = novel.getString("ncode");
            
            if(status == 0) {
            		bookStatus = BookStatus.COMPLETE;
            }
            
            BookEntity book = new BookEntity();
            book.setBookName_jp(title);
            book.setAuthorName(author);
            book.setBookStatus(bookStatus);
            book.setDescription_jp(description);
            book.setRedirect(link);
            databaseService.add(book);
            
            System.out.println(link);
            System.out.println("Book Table created!");
        }   
	}
	
	public static void addingBook_json() throws StreamReadException, DatabindException, IOException {
		try {
			InputStream resourceJSON = DataUtil.class.getResourceAsStream("/books.json");
			
			if (resourceJSON == null ) {
                throw new RuntimeException("❌ Book.json not found!");
            }else {
            		System.out.println("📂Book Resource path: " + resourceJSON);
            }
			String resourceString = new Scanner(resourceJSON, StandardCharsets.UTF_8)
                    .useDelimiter("\\A")
                    .next();

			JSONArray resourceJsonArray = new JSONArray(resourceString);
			
			for (int i = 0; i < resourceJsonArray.length(); i++) {
	            JSONObject obj = resourceJsonArray.getJSONObject(i);
	            String name_en = obj.getString("bookNameEn");
	            String name_jp = obj.getString("bookNameJp");
	            String author = obj.getString("authorName");
	            String des_en = obj.getString("descriptionEn");
	            String des_jp = obj.getString("descriptionJp");
	            String cover = obj.getString("coverPhotoUrl");
	            String url = obj.getString("redirectUrl");
	            int status = obj.getInt("status");
	            BookStatus bookStatus = BookStatus.ONGOING;
	            if(status == 0) {
	            		bookStatus = BookStatus.COMPLETE;
	            }
	            
	            JSONArray genreArr = obj.getJSONArray("genre");
	            JSONArray tagArr = obj.getJSONArray("tags");
	            
	            Set<GenreEntity> genres = databaseService.mapJsonToEntities(
	                    genreArr, "genre_en", GenreEntity.class, em);

	            Set<TagEntity> tags = databaseService.mapJsonToEntities(
	                    tagArr, "tag_en", TagEntity.class, em);

	            BookEntity book = new BookEntity(
	            		name_en, name_jp, author, cover, url, bookStatus, des_jp
	            		);
//	            BookEntity book = new BookEntity();
	            book.setBookName_en(name_en);
	            if(!genres.isEmpty() && !tags.isEmpty()) {

		            book.setGenres(genres); 
		            book.setTags(tags);
//		    		System.out.println("Genre passing to set method in book Entity!");
//		    		System.out.println("Genre passed from DataUtil.jsonArray"+genres.toString());
	            }else {
	            		System.out.println("Genre/Tags empty in DataUtil!!");
	            }
	            
	            if(des_en != null) { book.setDescription_en(des_en);}
	           
	            databaseService.add(book);
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void adding_fav() throws StreamReadException, DatabindException, IOException {
		try {
			InputStream resourceJSON = DataUtil.class.getResourceAsStream("/favourites.json");
			
			if (resourceJSON == null ) {
                throw new RuntimeException("❌ Favourites.json not found!");
            }else {
            		System.out.println("📂Fav Resource path: " + resourceJSON);
            }
			String resourceString = new Scanner(resourceJSON, StandardCharsets.UTF_8)
                    .useDelimiter("\\A")
                    .next();

			JSONArray resourceJsonArray = new JSONArray(resourceString);
			for (int i = 0; i < resourceJsonArray.length(); i++) {
	            JSONObject obj = resourceJsonArray.getJSONObject(i);
	            int userid = obj.getInt("name");
	            JSONArray bookArr = obj.getJSONArray("books");
	            Set<BookEntity> books = databaseService.mapJsonToEntities(
	                    bookArr, "bookId", BookEntity.class, em);	            
	            UserEntity user = databaseService.getEntityById(UserEntity.class, userid);
	            FavoriteEntity fav = new FavoriteEntity(books, user);
//	            System.out.println("user : "+ user.toString() + "books : " + books.toString());
	            System.out.println("Fav created in util");
	            databaseService.add(fav);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void addingGenres() throws StreamReadException, DatabindException, IOException {
		Map<String, String> genreMap = mapper.readValue(
			App.class.getResourceAsStream("/genre.json"), 
			new TypeReference<Map<String, String>>() {}
			);
		for (Map.Entry<String,String> entry: genreMap.entrySet()) {
			String genre_en = entry.getKey();
			String gnnre_jp = entry.getValue();
			
			GenreEntity genre = new GenreEntity();
			genre.setGenre_en(genre_en);
			genre.setGenre_jp(gnnre_jp);
//			System.out.println("Gener Entity before adding in utils : " + genre);
			databaseService.add(genre);
		}
	}
	public static void addingTags() throws StreamReadException, DatabindException, IOException { 
		Map<String, String> tagMap = mapper.readValue(
		    App.class.getResourceAsStream("/Tags.json"),
		    new TypeReference<Map<String, String>>() {}
		);
		
		for (Map.Entry<String, String> entry : tagMap.entrySet()) {
		    String tag_en = entry.getKey();
		    String tag_jp = entry.getValue();

		    TagEntity tag = new TagEntity();
		    tag.setTag_en(tag_en);
		    tag.setTag_jp(tag_jp);
//			System.out.println("tag Entity before adding in utils" + tag);
		    databaseService.add(tag);
		}
	}
	public static void addingUsers() throws StreamReadException, DatabindException, IOException {
		try {
			InputStream resourceJSON = DataUtil.class.getResourceAsStream("/UserData.json");
			
			System.out.println("📂 Resource path: " + resourceJSON);
			if (resourceJSON == null ) {
                throw new RuntimeException("❌ UserData.json not found!");
            }
			String resourceString = new Scanner(resourceJSON, StandardCharsets.UTF_8)
                    .useDelimiter("\\A")
                    .next();

			JSONArray resourceJsonArray = new JSONArray(resourceString);
			for (int i = 0; i < resourceJsonArray.length(); i++) {
	            JSONObject obj = resourceJsonArray.getJSONObject(i);
	            String name = obj.getString("name");
	            String email = obj.getString("email");
	            String password = BCrypt.hashpw(obj.getString("password"), BCrypt.gensalt());
	            
	            UserEntity userEntity = new UserEntity(name, email, password);
	            if("ニンタンダールイン".equalsIgnoreCase(name)) {
	            		userEntity.setRole("ADMIN");
	            }
	            databaseService.add(userEntity);
	            
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}


















//package org.Util;
//
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.Scanner;
//
//import org.json.JSONArray;
//
//public class GenreTagsUtil {
//	
//	public static JSONArray resourcesToStrings(String path) {
//		JSONArray resourceJsonArray = null;
//		try {
//			InputStream resourceJSON = GenreTagsUtil.class.getResourceAsStream("/"+path);
//			
//			System.out.println("📂 Resource path: " + resourceJSON);
//			if (resourceJSON == null ) {
//                throw new RuntimeException("❌ genre.json not found!");
//            }
//			
//			String resourceString = new Scanner(resourceJSON, StandardCharsets.UTF_8)
//	                    .useDelimiter("\\A")
//	                    .next();
//
//            resourceJsonArray = new JSONArray(resourceString);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return resourceJsonArray;
//	}

//public static void clsoeEmf() {
//	 if (emf != null && emf.isOpen()) {
//         emf.close();
//         System.out.println("🛑 EMF closed on shutdown.");
//     }
//}
//
//}
