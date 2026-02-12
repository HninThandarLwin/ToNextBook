package DataBase.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import DataBase.Util.ListStatus;
import DataBase.interfaces.HasId;

@Entity
@Table(name = "Lists")
public class ListTemplate implements HasId{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(nullable = false, length = 100)
    private String name;

    private LocalDate addedDate = LocalDate.now();	
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PUBLISH', 'PRIVATE') DEFAULT 'PRIVATE'")
    private ListStatus status = ListStatus.PRIVATE;
    
    @ManyToMany
    @JoinTable(
        name = "Book_lists",
        joinColumns = @JoinColumn(name = "list_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookEntity> books = new ArrayList<>(); // first set it empty so the list can be nullable before setting book
    
    
    public ListTemplate() {
    	
    }
    
    public ListTemplate(UserEntity user,String name) {
    		this.user = user;
    		this.name = name;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getters and Setters
	@Override
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public ListStatus getStatus() {
		return status;
	}

	public void setStatus(ListStatus status) {
		this.status = status;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}
}
