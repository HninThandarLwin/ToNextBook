package DataBase.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import DataBase.Util.BookStatus;
import DataBase.interfaces.HasId;

@Entity
@Table(name = "Favorite")
public class FavoriteEntity implements HasId{

    @Id
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserEntity id;	//user_id as favorite_id

   

	@ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "Fav_book",
		    joinColumns = @JoinColumn(name = "user_id"),
		    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<BookEntity> books = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private BookStatus readingStatus = BookStatus.ONGOING;
    
    public FavoriteEntity() {
    	
    }
    public FavoriteEntity(Set<BookEntity> book, UserEntity user) {
    		this.books = book;
    		this.id = user;
    }
    
    @Override
   	public String toString() {
   		return "FavoriteEntity [id=" + id + ", books=" + books + ", readingStatus=" + readingStatus + "]";
   	}

 // Getters and Setters
    @Override
    public int getId() {
        return id.getId();
    }
    
    public void setId(UserEntity id) {
		this.id = id;
	}
	
	public Set<BookEntity> getBooks() {
		return books;
	}
	public void setBook(Set<BookEntity> book) {
		this.books = book;
	}
	
	public BookStatus getReadingStatus() {
		return readingStatus;
	}
	
	public void setReadingStatus(BookStatus readingStatus) {
		this.readingStatus = readingStatus;
	}
}
