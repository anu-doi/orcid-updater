package au.edu.anu.orcid.metadatastores.publication;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Publication;
import au.edu.anu.orcid.metadatastores.misc.MSFORSubject;

/**
 * <p>MSPublication</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Object for publications from Metadata Stores</p>
 *
 * @author Genevieve Turner
 *
 */
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

	/**
	 * Get the field of research subjects
	 * 
	 * @return The  subjects
	 */
	@XmlElement(name="for-subject")
	public List<MSFORSubject> getSubjects() {
		return subjects;
	}

	/**
	 * Set the field of resarch subjects
	 * 
	 * @param subjects The subjects
	 */
	public void setSubjects(List<MSFORSubject> subjects) {
		this.subjects = subjects;
	}

	/**
	 * Get the authors
	 * 
	 * @return The authors
	 */
	@XmlElement(name="author")
	public List<MSAuthor> getAuthors() {
		return authors;
	}

	/**
	 * Set the authors
	 * 
	 * @param authors The authors
	 */
	public void setAuthors(List<MSAuthor> authors) {
		this.authors = authors;
	}

	/**
	 * Get the aries id
	 * 
	 * @return The aries id
	 */
	@XmlElement(name="aries-id")
	public String getAriesId() {
		return ariesId;
	}

	/**
	 * Set the aries id
	 * 
	 * @param ariesId the aries id
	 */
	public void setAriesId(String ariesId) {
		this.ariesId = ariesId;
	}

	/**
	 * Get the category
	 * 
	 * @return The category
	 */
	@XmlElement(name="category")
	public String getCategory() {
		return category;
	}

	/**
	 * Set the category
	 * 
	 * @param category The category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Get the ISSN
	 * 
	 * @return The ISSN
	 */
	@XmlElement(name="issn")
	public String getISSN() {
		return ISSN;
	}

	/**
	 * Set the ISSN
	 * 
	 * @param iSSN The ISSN
	 */
	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	/**
	 * Get the ISBN
	 * 
	 * @return The ISBN
	 */
	@XmlElement(name="isbn")
	public String getISBN() {
		return ISBN;
	}

	/**
	 * Set the ISBN
	 * @param iSBN The ISBN
	 */
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	/**
	 * Get the name of the publication that it was published in.
	 * 
	 * @return The publication name
	 */
	@XmlElement(name="publication-name")
	public String getPublicationName() {
		return publicationName;
	}

	/**
	 * Set the name of the publication that it was published in.
	 * 
	 * @param publicationName The publication name
	 */
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	/**
	 * Get the title of the publication
	 * 
	 * @return The title
	 */
	@XmlElement(name="title")
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the publication
	 * 
	 * @param title The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the type of publication
	 * 
	 * @return The type
	 */
	@XmlElement(name="type")
	public String getType() {
		return type;
	}

	/**
	 * Set the type of publication
	 * 
	 * @param type The type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the year of publication
	 * 
	 * @return The year of publication
	 */
	@XmlElement(name="publication-year")
	public String getPublicationYear() {
		return publicationYear;
	}

	/**
	 * Set the year of publication
	 * 
	 * @param publicationYear The year of publication
	 */
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	/**
	 * Generate the Publication object
	 * 
	 * @return The publication
	 */
	public Publication getPublication() {
		Publication publication = new Publication();
		
		publication.setExternalIdentifier(this.getAriesId());
		publication.setTitle(this.getTitle());
		publication.setPublicationName(this.getPublicationName());
		publication.setPublicationYear(this.getPublicationYear());
		publication.setIssn(this.getISSN());
		publication.setIsbn(this.getISBN());
		publication.setWorkType(generateWorkType());
		
		for (MSAuthor msAuthor : this.getAuthors()) {
			publication.getAuthors().add(msAuthor.getAuthor(publication));
		}
		
		return publication;
	}
	
	/**
	 * Generate the work type for the publication
	 * 
	 * @return The work type
	 */
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
