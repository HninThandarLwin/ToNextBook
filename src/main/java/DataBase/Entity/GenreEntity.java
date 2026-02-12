package DataBase.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import DataBase.interfaces.HasId;

@Entity
@Table(name = "genre")
public class GenreEntity implements HasId{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;

    private String genre_en;
    
    private String genre_jp;

    @ManyToMany(mappedBy = "genres",fetch = FetchType.LAZY)
    private Set<BookEntity> books = new HashSet<>();
    
    @Override
    public int getId() {
        return genreId;
    }

    // Getters and Setters
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

	public String getGenre_en() {
		return genre_en;
	}

	public void setGenre_en(String genre_en) {
		this.genre_en = genre_en;
	}

	public String getGenre_jp() {
		return genre_jp;
	}

	public void setGenre_jp(String genre_jp) {
		this.genre_jp = genre_jp;
	}

	public Set<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<BookEntity> books) {
		this.books = books;
	}
}
