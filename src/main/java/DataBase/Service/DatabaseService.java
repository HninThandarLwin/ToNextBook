package DataBase.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import jakarta.persistence.EntityManager;

import org.json.JSONArray;

import com.fasterxml.jackson.databind.ObjectMapper;

import DataBase.DTO.GenreTagDTO;
import DataBase.Entity.BookEntity;
import DataBase.Entity.FavoriteEntity;
import DataBase.Entity.GenreEntity;
import DataBase.Entity.ListTemplate;
import DataBase.Entity.TagEntity;
import DataBase.Entity.UserEntity;
import DataBase.Util.EntityManagerFactoryProvider;
import DataBase.interfaces.HasId;

public class DatabaseService {
	private EntityManager em = EntityManagerFactoryProvider.createEntityManager();
	ObjectMapper mapper = new ObjectMapper();

	private EntityManager getEm() {
		return EntityManagerFactoryProvider.createEntityManager();
	}

	public <T> void add(T entity) {
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	public <T> T update(T entity) {
		try {
			em.getTransaction().begin();
			T merged = em.merge(entity);
			em.getTransaction().commit();
			return merged;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	public List<BookEntity> searchBook(String name) { //name here is book title or author name 
		List<BookEntity> results = new ArrayList<>();
		boolean isJp = isJapanese(name);
		try {
			String querry = isJp
					? "SELECT b FROM BookEntity b WHERE b.bookName_jp LIKE :keyword OR b.authorName LIKE :keyword"
					: "SELECT b FROM BookEntity b WHERE b.bookName_en LIKE :keyword OR b.authorName LIKE :keyword";

			results = em.createQuery(querry, BookEntity.class)
					.setParameter("keyword", "%" + name + "%")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<FavoriteEntity> searchBookByFav(List<String> names) {
	    List<FavoriteEntity> results = new ArrayList<>();

	    try {
	        List<Integer> bookIds = new ArrayList<>();

	        for (String name : names) {
	            bookIds.add(getEntityId(searchBook(name)));
	        }

	        System.out.println("BookIds in service.searchBookByFav(): " + bookIds);

	        String query = 
	            "SELECT F FROM FavoriteEntity F " +
	            "JOIN F.books fb " +
	            "WHERE fb.bookId IN :bookIds " +
	            "GROUP BY F.id " +
	            "HAVING COUNT(DISTINCT fb.bookId) = :bookCount";

	        results = em.createQuery(query, FavoriteEntity.class)
	                .setParameter("bookIds", bookIds)
	                .setParameter("bookCount", bookIds.size())   // 👈 auto count
	                .getResultList();

	        if (results.isEmpty()) {
	            System.out.println("There is no users with these books as fav.");
	        }else {
	        	System.out.println("Fav result: "+ results);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return results;
	}

	public Set<BookEntity> gener_Search(int id) {
		Set<BookEntity> results = new HashSet<>();
		try {
			GenreEntity genre = em.find(GenreEntity.class, id);
			results = genre.getBooks();
			if (results.isEmpty()) {
				System.out.println("There is no book with this genre :" + genre.getGenre_en() + "!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public Set<BookEntity> tag_Search(int id) {
		Set<BookEntity> results = new HashSet<>();
		try {
			TagEntity tag = em.find(TagEntity.class, id);
			results = tag.getBooks();
			if (results.isEmpty()) {
				System.out.println("There is no book with this tag :" + tag.getTag_en() + "!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	public void createReadingBook(int userId, String name) {
		UserEntity user = getEntityById(UserEntity.class, userId);
		ListTemplate list = new ListTemplate(user, name);
		add(list);
	}

	public List<GenreTagDTO> getAllGenres(String lang) {
		EntityManager em = getEm();
		try {
			return em.createQuery("""
					    SELECT new DataBase.DTO.GenreTagDTO(
					        g.genreId,
					        CASE
					            WHEN :lang = 'jp' THEN g.genre_jp
					            ELSE g.genre_en
					        END
					    )
					    FROM GenreEntity g
					""", GenreTagDTO.class)
					.setParameter("lang", lang)
					.getResultList();
		} finally {
			em.close(); // ⭐ VERY IMPORTANT
		}
	}

	public List<GenreTagDTO> getAllTags(String lang) {
		EntityManager em = getEm();
		try {
			return em.createQuery("""
					    SELECT new DataBase.DTO.GenreTagDTO(
					        t.tagId,
					        CASE
					            WHEN :lang = 'jp' THEN t.tag_jp
					            ELSE t.tag_en
					        END
					    )
					    FROM TagEntity t
					""", GenreTagDTO.class)
					.setParameter("lang", lang)
					.getResultList();
		} finally {
			em.close(); // ⭐ VERY IMPORTANT
		}
	}
	public BookEntity getRandomBook() {

	    Long count = em.createQuery(
	        "SELECT COUNT(b) FROM BookEntity b", Long.class
	    ).getSingleResult();

	    int randomIndex = new Random().nextInt(count.intValue());

	    return em.createQuery(
	            "SELECT b FROM BookEntity b", BookEntity.class
	        )
	        .setFirstResult(randomIndex)
	        .setMaxResults(1)
	        .getSingleResult();
	}

	
	public List<BookEntity> searchBooksByGenresAndTags(
			List<Integer> genreIds,
			List<Integer> tagIds) {
		String jpql = """
				    SELECT DISTINCT b
				    FROM BookEntity b
				    LEFT JOIN b.genres g
				    LEFT JOIN b.tags t
				    WHERE
				        (:genreEmpty = true OR g.genreId IN :genreIds)
				    AND (:tagEmpty = true OR t.tagId IN :tagIds)
				""";

		return em.createQuery(jpql, BookEntity.class)
				.setParameter("genreEmpty", genreIds == null || genreIds.isEmpty())
				.setParameter("tagEmpty", tagIds == null || tagIds.isEmpty())
				.setParameter("genreIds", genreIds == null ? List.of(-1) : genreIds)
				.setParameter("tagIds", tagIds == null ? List.of(-1) : tagIds)
				.getResultList();
	}

	public <T> Set<T> mapJsonToEntities(JSONArray arr, String fieldName,
			Class<T> clazz, EntityManager em) {
		Set<String> names = new HashSet<>();
		for (int i = 0; i < arr.length(); i++) {
			names.add(arr.getString(i));
		}
		System.out.println("NamesSet in service" + names.toString());

		String jpql = "";
		if (names.isEmpty()) {
			System.out.println("Names is empty and returned in service");
			return Collections.emptySet();
		} else if (!names.isEmpty()) {
			jpql = "SELECT e FROM " + clazz.getSimpleName() +
					" e WHERE e." + fieldName + " IN :names";
			//	    		System.out.println("Names is NOT empty and returned in service");
		}

		return new HashSet<>(
				em.createQuery(jpql, clazz)
						.setParameter("names", names)
						.getResultList());
	}

	public static boolean isJapanese(String text) {
		for (char c : text.toCharArray()) {
			if (c >= '\u3040' && c <= '\u309F')
				return true;// Hiragana range
			if (c >= '\u30A0' && c <= '\u30FF')
				return true;// Katakana range
			if (c >= '\u4E00' && c <= '\u9FFF')
				return true;// Kanji (CJK Unified Ideographs)
		}
		return false;
	}

	public <T> T getEntityById(Class<T> clazz, int entityId) {
		return em.find(clazz, entityId);
	}

	//for Listed entities
	public <T extends HasId> int getEntityId(List<T> entities) {
		for (T entity : entities) {
			return entity.getId();
		}
		return 0;
	}

}
