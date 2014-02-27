package au.edu.anu.orcid.db.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.GenericDAO;
import au.edu.anu.orcid.db.dao.impl.GenericDAOImpl;

public class PublicationTest {
	static final Logger LOGGER = LoggerFactory.getLogger(PublicationTest.class);
	
	@Ignore
	@Test
	public void createTest() {
		LOGGER.info("Begin Test");
		Publication pub = new Publication();
		pub.setAriesId("123458");
		pub.setIssn("1234-5678");
		pub.setPublicationName("The Journal of Testing");
		pub.setTitle("The Theory of Everything");
		pub.setWorkType("journal-article");
		Author auth1 = new Author();
		auth1.setName("Person, T");
		auth1.setPublication(pub);
		pub.getAuthors().add(auth1);
		//pub.set
		GenericDAO<Publication, Long> publicationDAO = new GenericDAOImpl<Publication, Long>(Publication.class);
		pub = publicationDAO.create(pub);
		LOGGER.info("Publication Created");
		pub.setPublicationYear("2013");
		Author auth2 = new Author();
		auth2.setName("Body, S");
		auth2.setPublication(pub);
		pub.getAuthors().add(auth2);
		pub = publicationDAO.update(pub);
		LOGGER.info("Post Update there are {} authors", pub.getAuthors().size());
		LOGGER.info("Test Complete");
	}

	@Ignore
	@Test
	public void updateTest() {
		GenericDAO<Publication, Long> publicationDAO = new GenericDAOImpl<Publication, Long>(Publication.class);
		Publication pub = publicationDAO.getSingleById(new Long(1));
		LOGGER.info("Aries Id: {}, Title: {}, Publication Name: {}, ISBN: {}, ISSN: {}, Type: {}, Year: {}", pub.getAriesId(), pub.getTitle(), pub.getPublicationName(), pub.getIsbn(), pub.getIssn(), pub.getWorkType(), pub.getPublicationYear());
		pub.setPublicationYear("2011");
		publicationDAO.update(pub);
	}

	//@Ignore
	@Test
	public void getPublication() {
		List<Author> authors1 = new ArrayList<Author>();
		Author auth = new Author();
		auth.setExtId("123451");
		auth.setId(new Long(1));
		auth.setName("Name 1");
		authors1.add(auth);

		auth = new Author();
		auth.setExtId("123451");
		auth.setId(new Long(2));
		auth.setName("Name 2");
		authors1.add(auth);

		auth = new Author();
		auth.setExtId("123453");
		auth.setId(new Long(3));
		auth.setName("Name 3");
		authors1.add(auth);

		List<Author> authors2 = new ArrayList<Author>();
		auth = new Author();
		auth.setExtId("123451");
		//auth.setId(new Long(1));
		auth.setName("Name 1");
		authors2.add(auth);

		auth = new Author();
		auth.setExtId("123453");
		//auth.setId(new Long(1));
		auth.setName("Name 3");
		authors2.add(auth);

		auth = new Author();
		auth.setExtId("123454");
		//auth.setId(new Long(1));
		auth.setName("Name 4");
		authors2.add(auth);
		
		List<Author> retain1 = new ArrayList<Author>(authors1);
		LOGGER.info("Number of rows in retain1, {}", retain1.size());
		
		retain1.retainAll(authors2);
		LOGGER.info("Number of rows in retain1, {}", retain1.size());
		
		Set<Author> addAll1 = new HashSet<Author>(authors1);
		//List<Author> addAll1 = new ArrayList<Author>(authors1);
		LOGGER.info("Number of rows in retain1, {}", addAll1.size());
		
		addAll1.addAll(authors2);
		LOGGER.info("Number of rows in retain1, {}", addAll1.size());
		
		/*List<String> stuff1 = new ArrayList<String>(Arrays.asList("A", "B", "C", "D"));
		List<String> stuff2 = new ArrayList<String>(Arrays.asList("B", "C", "E"));
		//LOGGER.info("Contains all: {}", stuff1.containsAll(stuff2));
		stuff1.retainAll(stuff2);
		LOGGER.info("{}", stuff1.toString());*/
	}
}
