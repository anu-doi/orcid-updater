package au.edu.anu.orcid.metadatastores.publication;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Author;
import au.edu.anu.orcid.db.model.Publication;
import au.edu.anu.orcid.metadatastores.misc.MSFORSubject;
import au.edu.anu.orcid.process.retrieve.UidObtainer;

@XmlRootElement(name="publication")
public class MSPublication {
	static final Logger LOGGER = LoggerFactory.getLogger(MSPublication.class);
	
	private List<MSFORSubject> subjects = new ArrayList<MSFORSubject>();
	private List<MSAuthor> authors = new ArrayList<MSAuthor>();
	private String ariesId;
	private String category;
	private String ISSN;
	private String ISBN;
	private String publicationName;
	private String title;
	private String type;
	private String publicationYear;

	@XmlElement(name="for-subject")
	public List<MSFORSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<MSFORSubject> subjects) {
		this.subjects = subjects;
	}

	@XmlElement(name="author")
	public List<MSAuthor> getAuthors() {
		return authors;
	}

	public void setAuthors(List<MSAuthor> authors) {
		this.authors = authors;
	}

	@XmlElement(name="aries-id")
	public String getAriesId() {
		return ariesId;
	}

	public void setAriesId(String ariesId) {
		this.ariesId = ariesId;
	}

	@XmlElement(name="category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlElement(name="issn")
	public String getISSN() {
		return ISSN;
	}

	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	@XmlElement(name="isbn")
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@XmlElement(name="publication-name")
	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	@XmlElement(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name="publication-year")
	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	public Publication getPublication() {
		Publication publication = new Publication();
		
		publication.setExternalIdentifier(this.getAriesId());
		publication.setTitle(this.getTitle());
		publication.setPublicationName(this.getPublicationName());
		publication.setPublicationYear(this.getPublicationYear());
		publication.setIssn(this.getISSN());
		publication.setIsbn(this.getISBN());
		publication.setWorkType(generateWorkType());
		
		//List<Author> authors = new ArrayList<Author>();
		
		for (MSAuthor msAuthor : this.getAuthors()) {
			publication.getAuthors().add(msAuthor.getAuthor(publication));
		}
		//publication.setAuthors(authors);
		
		return publication;
	}
	
	private String generateWorkType() {
		//TODO logic around work types.... Need to map the category fields perhaps a properties file? for now just use type field
		if ("Conference".equals(this.getType())) {
			return "conference-paper";
		}
		else if ("Journal".equals(this.getType())) {
			return "journal-article";
		}
		else if ("Book Chapter".equals(this.getType())) {
			return "book-chapter";
		}
		return null;
	}
}
