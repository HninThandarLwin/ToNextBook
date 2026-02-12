package org.NextBook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import DataBase.Service.DatabaseService;
import DataBase.Service.UserService;
import DataBase.Util.DataUtil;
import DataBase.Util.EntityManagerFactoryProvider;

public class App{
	
	static DatabaseService service = new DatabaseService();
	static EntityManager em = EntityManagerFactoryProvider.createEntityManager();

	static UserService userService = new UserService();
	
	public static void main(String[] args) {
//		setPrimaryData();	// 初期化データ入れるメソッド
		
		//here you still need to to some cutions of what user input (datas that come from frontEnd)!!!
//		List<String> bookNames = List.of("Spice and Wolf","That Time I Got Reincarnated as a Slime", "Overlord");
//		System.out.println("Books by fav " + list_toString(service.searchBookByFav(bookNames)));
		
//		service.gener_tags_Search("Isekai", null);
//		System.out.println("Select Name method resluts : "+ service.searchBook("Sword Art Online").toString());	//String here is book title or author name 
//		System.out.println("Id selected using getEntity(getEntityId) structure : " + service.getEntity(BookEntity.class, service.getEntityId(service.searchBook("Sword Art Online"))));
//		System.out.println("Search by genre : " + service.toString());
		System.out.println("loggin: "+ userService.login("founder@admin.com","Admin").getId());

		EntityManagerFactoryProvider.close();		//closing EntityManagerFactory 
    }
	
	//初期化データ入れます。
	public static void setPrimaryData() {	
		
//		DataUtil.addingBooks_API();
		try {
			alterTableSequences();
			DataUtil.addingGenres();
			DataUtil.addingTags();
			DataUtil.addingBook_json();
			DataUtil.addingUsers();
			DataUtil.adding_fav();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Primary Datas have been set!");
	}
	
	public static void alterTableSequences() {
		EntityTransaction tx = em.getTransaction(); 		// Insert,Update,Delete queries must be inside a transaction
		tx.begin();
		em.createNativeQuery("ALTER TABLE book AUTO_INCREMENT = 101001").executeUpdate();
//		em.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 0001").executeUpdate();
//		em.createNativeQuery("ALTER TABLE tags AUTO_INCREMENT = 001").executeUpdate();		
//		em.createNativeQuery("ALTER TABLE genre AUTO_INCREMENT = 001").executeUpdate();
		tx.commit();
	}
	
	public static <T> List<String> list_toString(List<T> entities) {
		List<String> result = new ArrayList<String>();
		for(T entity : entities) {
			result.add(entity.toString());
		}
		return result;
	}
}







