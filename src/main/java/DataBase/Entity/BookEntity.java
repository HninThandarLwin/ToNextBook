package DataBase.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import DataBase.Util.BookStatus;
import DataBase.interfaces.HasId;

@Entity
@Table(name = "Book")
public class BookEntity implements HasId{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    
    private String bookName_en;
    
    @Column(nullable = false)
    private String bookName_jp;
    
    @Column(nullable = false)
    private String authorName;

    private String bookCover;			 // nullable
    
    private String redirect;

	@Enumerated(EnumType.STRING)
    private BookStatus bookStatus = BookStatus.ONGOING;

    @Column(columnDefinition = "TEXT")
    private String description_en;
    
    @Column(columnDefinition = "TEXT")
    private String description_jp;

    // Relations
    @ManyToMany(cascade = { CascadeType.MERGE })
    @JsonIgnore
    @JoinTable(name = "Book_Genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genreId"))
    private Set<GenreEntity> genres = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JsonIgnore
    @JoinTable(name = "Book_Tags",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<TagEntity> tags = new HashSet<>();
    
//    @ManyToMany(mappedBy="book_fav")
//    private Set<UserEntity> fav;
    
    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private Set<FavoriteEntity> favorite = new HashSet<>();
    
    public BookEntity() {
    	
    }
    public BookEntity(
    		String name_en, String name_jp, String author,
    		String cover, String link, BookStatus status, 
    		String desc_jp) {
    		this.bookName_en = name_en;
    		this.bookName_jp = name_jp;
    		this.authorName = author;
    		this.bookCover = cover;
    		this.redirect = link;
    		this.bookStatus = status;
    		this.description_jp = desc_jp;
    }
    
    
    @Transient
    public String getLocalizedName(String lang) {
        return lang.equalsIgnoreCase("jp") ? bookName_jp : bookName_en;
    }

    @Transient
    public String getLocalizedDescription(String lang) {
        return lang.equalsIgnoreCase("jp") ? description_jp : description_en;
    }

    @Transient
    public String getLocalizedStatus(String lang) {
        return lang.equalsIgnoreCase("jp") ? bookStatus.getJp() : bookStatus.getEn();
    }

    @Override
    public int getId() {
        return bookId;
    }
    
    // Getters and Setters
    public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getBookName_en() {
		return bookName_en;
	}

	public String getBookName_jp() {
		return bookName_jp;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getBookCover() {
		return bookCover;
	}

	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public void setBookName_en(String bookName_en) {
		this.bookName_en = bookName_en;
	}

	public void setBookName_jp(String bookName_jp) {
		this.bookName_jp = bookName_jp;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setBookCover(String bookCover) {
		this.bookCover = bookCover;
	}

	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	public void setDescription_jp(String description_jp) {
		this.description_jp = description_jp;
	}

	public void setGenres(Set<GenreEntity> genres) {
//		System.out.println("Genre passed from DataUtil"+genres.toString());
		System.out.println("Genre Set!");
		this.genres = genres;
	}

	public void setTags(Set<TagEntity> tags) {
//		System.out.println("tag passed from DataUtil"+genres.toString());
		System.out.println("tag Set!");
		System.out.println(tags);
		this.tags = tags;
	}

	public String getDescription_en() {
		return description_en;
	}

	public String getDescription_jp() {
		return description_jp;
	}

	public Set<GenreEntity> getGenres() {
		return genres;
	}

	public Set<TagEntity> getTags() {
		return tags;
	}
	
    
	@Override
	public String toString() {
		return "BookEntity [bookId=" + bookId + ", bookName_en=" + bookName_en + ", bookName_jp=" + bookName_jp
				+ ", authorName=" + authorName + ", bookCover=" + bookCover + ", redirect=" + redirect + ", bookStatus(jp_fornow_testing)="
				+ bookStatus.getJp() + ", description_en=" + description_en + ", description_jp=" + description_jp + ", genres="
				+ genres.toString() + ", tags=" + tags + "]";
	}
}
