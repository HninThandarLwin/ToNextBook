package DataBase.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryProvider {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("NextBookEMF");
	
    public static EntityManager createEntityManager() {  
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf.isOpen()) {
            emf.close();
            System.out.println("🛑 EntityManagerFactory Closed!");
        }
    }
}
