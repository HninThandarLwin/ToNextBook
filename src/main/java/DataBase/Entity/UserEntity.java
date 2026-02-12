package DataBase.Entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import DataBase.interfaces.HasId;

@Entity
@Table(name = "User")
public class UserEntity implements HasId{

    @Id
    	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;  // store encrypted version, not plain text!

    @Column
    private String role = "USER"; 

    private String coverPhoto;
    private String profilePhoto;

    private LocalDateTime registeredDate = LocalDateTime.now();

    private String profileMessage;
    
    @OneToOne(mappedBy = "id", cascade = CascadeType.ALL)
    private FavoriteEntity favorite;

    public FavoriteEntity getFavorite() {
		return favorite;
	}
	public void setFavorite(FavoriteEntity favorite) {
		this.favorite = favorite;
	}

	@OneToMany
	@JoinColumn(name = "user")
    private Set<ListTemplate> readingLists;
    
    public UserEntity() {
    	
    }
    public UserEntity(String name, String email, String pws) {
    		this.userName = name;
    		this.email = email;
    		this.password = pws;
    }
    
    @Override
	public String toString() {
		return "UserEntity [userName=" + userName + ", role=" + role + ", registeredDate="
				+ registeredDate + "]";
	}
    
    // Getters and Setters    
    @Override
	public int getId() {
		return userId;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public LocalDateTime  getRegisteredDate() {
		return registeredDate;
	}

	public String getProfileMessage() {
		return profileMessage;
	}

	public void setProfileMessage(String profileMessage) {
		this.profileMessage = profileMessage;
	}
	
//	public Set<BookEntity> getBook_fav() {
//		return book_fav;
//	}
//	public void setBook_fav(Set<BookEntity> book_fav) {
//		this.book_fav = book_fav;
//	}
	public Set<ListTemplate> getReadingLists() {
		return readingLists;
	}

	public void setReadingLists(Set<ListTemplate> readingLists) {
		this.readingLists = readingLists;
	}

}
