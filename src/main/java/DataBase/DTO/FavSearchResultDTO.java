package DataBase.DTO;

import java.util.Set;

import DataBase.Entity.BookEntity;
import DataBase.Util.BookStatus;

public class FavSearchResultDTO {
	public int userId;
	public String userName;
	public BookStatus readingStatus;
	public Set<BookEntity> books;

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public BookStatus getReadingStatus() {
		return readingStatus;
	}

	public Set<BookEntity> getBooks() {
		return books;
	}
}
