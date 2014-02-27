
package au.edu.anu.orcid.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PersistenceManager {
	static final Logger LOGGER = LoggerFactory.getLogger(PersistenceManager.class);
	
	private static final PersistenceManager singleton_ = new PersistenceManager();
	
	protected EntityManagerFactory emf;
	
	public static PersistenceManager getInstance() {
		return singleton_;
	}
	
	private PersistenceManager() {
		
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			createEntityManagerFactory();
		}
		return emf;
	}
	
	public void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			LOGGER.info("Persistence finished at " + new java.util.Date());
		}
	}
	
	protected void createEntityManagerFactory() {
		this.emf = Persistence.createEntityManagerFactory("orcid");
		LOGGER.info("Persistence started at " + new java.util.Date());
	}
}
