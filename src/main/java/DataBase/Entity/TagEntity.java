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
@Table(name = "Tags")
public class TagEntity implements HasId{

    @Id
    	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    private String tag_en;
    
    private String tag_jp;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<BookEntity> books = new HashSet<>();

    @Override
	public int getId() {
		return tagId;
	}
    // Getters and Setters
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTag_en() {
		return tag_en;
	}

	public void setTag_en(String tag_en) {
		this.tag_en = tag_en;
	}

	public String getTag_jp() {
		return tag_jp;
	}

	public void setTag_jp(String tag_jp) {
		this.tag_jp = tag_jp;
	}

	public Set<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<BookEntity> books) {
		this.books = books;
	}
 }
