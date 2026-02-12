package DataBase.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.mindrot.jbcrypt.BCrypt;

import DataBase.Entity.UserEntity;
import DataBase.Util.EntityManagerFactoryProvider;

public class UserService {
	
	static DatabaseService databaseService = new DatabaseService();
	static EntityManager em = EntityManagerFactoryProvider.createEntityManager();
	
	//Register
	public static void createUser(String name, String email, String pws) {
		try {
			em.getTransaction().begin();
			
	        TypedQuery<Long> query = em.createQuery(
	            "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class
	        );
	        query.setParameter("email", email);

	        Long count = query.getSingleResult();
	        if (count > 0) {
	        		System.out.println("Email already registered");
	        }

            String hashPWS = BCrypt.hashpw(pws, BCrypt.gensalt());

            UserEntity user = new UserEntity(name, email, hashPWS);
            em.persist(user);
            System.out.println("User Created");
            em.getTransaction().commit();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//login
	public static UserEntity login(String email, String password) {
		
		 try {
	            TypedQuery<UserEntity> q = em.createQuery(
	                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class
	            );

	            q.setParameter("email", email);

	            UserEntity user = q.getSingleResult(); // throws NoResultException if not found

	            boolean correct = BCrypt.checkpw(password, user.getPassword());
//	            BCrypt.hashpw(obj.getString("password"), BCrypt.gensalt()) in create user and addUsers also

	            if (correct) {
	                return user;
	            } else {
	                return null;
	            }

        } catch (Exception e) {
        	System.err.println(e.getMessage());
            return null;
        } 
    }
}
