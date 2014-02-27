package au.edu.anu.orcid.metadatastores.publication;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.orcid.ns.orcid.Citation;
import org.orcid.ns.orcid.Contributor;
import org.orcid.ns.orcid.Country;
import org.orcid.ns.orcid.CreditName;
import org.orcid.ns.orcid.JournalTitle;
import org.orcid.ns.orcid.OrcidId;
import org.orcid.ns.orcid.OrcidWork;
import org.orcid.ns.orcid.PublicationDate;
import org.orcid.ns.orcid.Url;
import org.orcid.ns.orcid.Visibility;
import org.orcid.ns.orcid.WorkContributors;
import org.orcid.ns.orcid.WorkExternalIdentifier;
import org.orcid.ns.orcid.WorkExternalIdentifiers;
import org.orcid.ns.orcid.WorkTitle;
import org.orcid.ns.orcid.Year;

import au.edu.anu.orcid.metadatastores.MSFORSubject;

@XmlRootElement(name="publication")
public class MSPublication {
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
}
