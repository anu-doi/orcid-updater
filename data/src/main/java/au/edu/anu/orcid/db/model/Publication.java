package au.edu.anu.orcid.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="publication")
public class Publication {
	static final Logger LOGGER = LoggerFactory.getLogger(Publication.class);
	
	private Long id;
	private String ariesId;
	private String title;
	private String publicationName;
	private String publicationYear;
	private String workType;
	private String issn;
	private String isbn;
	private List<Author> authors = new ArrayList<Author>();
	
	public Publication() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="aries_id")
	public String getAriesId() {
		return ariesId;
	}

	public void setAriesId(String ariesId) {
		this.ariesId = ariesId;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="publication_name")
	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	@Column(name="publication_year")
	public String getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Column(name="work_type")
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	@Column(name="issn")
	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	@Column(name="isbn")
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "publication")
	@Cascade(CascadeType.ALL)
	@NotFound(action=NotFoundAction.IGNORE)
	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Transient
	public OrcidWork getWork() {
		OrcidWork work = new OrcidWork();

		work.setCountry(getCountry());
		work.setJournalTitle(getJournalTitle());
		work.setLanguageCode(getLanguageCode());
		work.setPublicationDate(getPublicationDate());
		work.setShortDescription(getShortDescription());
		work.setUrl(getUrl());
		work.setVisibility(getVisibility());
		work.setWorkCitation(getCitation());
		work.setWorkContributors(getContributors());
		work.setWorkExternalIdentifiers(getExternalIdentifiers());
		work.setWorkSource(getSource());
		work.setWorkTitle(getWorkTitle());
		work.setWorkType(getOrcidWorkType());
		
		return work;
	}

	@Transient
	private Country getCountry() {
		return null;
	}

	@Transient
	private JournalTitle getJournalTitle() {
		if (publicationName != null) {
			JournalTitle journalTitle = new JournalTitle();
			journalTitle.setContent(publicationName);
			return journalTitle;
		}
		return null;
	}

	@Transient
	private String getLanguageCode() {
		return null;
	}

	@Transient
	private PublicationDate getPublicationDate() {
		if (publicationYear != null) {
			PublicationDate publicationDate = new PublicationDate();
			Year year = new Year();
			year.setValue(publicationYear);
			publicationDate.setYear(year);
			return publicationDate;
		}
		return null;
	}

	@Transient
	private String getShortDescription() {
		return null;
	}

	@Transient
	private Url getUrl() {
		return null;
	}

	@Transient
	private Visibility getVisibility() {
		return null;
	}

	@Transient
	private Citation getCitation() {
		return null;
	}

	@Transient
	private WorkContributors getContributors() {
		WorkContributors contributors = new WorkContributors();
		for (Author author : authors) {
			Contributor contributor = new Contributor();
			CreditName creditName = new CreditName();
			String authorName = author.getName();
			creditName.setValue(authorName);
			contributor.setCreditName(creditName);
			contributors.getContributor().add(contributor);
		}
		if (contributors.getContributor().size() > 0) {
			return contributors;
		}
		return null;
	}

	@Transient
	private WorkExternalIdentifiers getExternalIdentifiers() {
		WorkExternalIdentifiers identifiers = new WorkExternalIdentifiers();
		//identifiers.
		if (isbn != null) {
			WorkExternalIdentifier identifier = new WorkExternalIdentifier();
			identifier.setWorkExternalIdentifierId(isbn);
			identifier.setWorkExternalIdentifierType("isbn");
			identifiers.getWorkExternalIdentifier().add(identifier);
		}
		if (issn != null) {
			WorkExternalIdentifier identifier = new WorkExternalIdentifier();
			identifier.setWorkExternalIdentifierId(issn);
			identifier.setWorkExternalIdentifierType("issn");
			identifiers.getWorkExternalIdentifier().add(identifier);
		}
		if (identifiers.getWorkExternalIdentifier().size() > 0) {
			return identifiers;
		}
		return null;
	}

	@Transient
	private OrcidId getSource() {
		return null;
	}

	@Transient
	private WorkTitle getWorkTitle() {
		WorkTitle workTitle = new WorkTitle();
		workTitle.setTitle(title);
		return workTitle;
	}

	@Transient
	private String getOrcidWorkType() {
		return workType;
	}
	
	public int hashCode() {
		int hashCode = 0;
		if (ariesId != null) {
			hashCode = 17 * hashCode + ariesId.hashCode();
		}
		if (title != null) {
			hashCode = 17 * hashCode + title.hashCode();
		}
		if (publicationName != null) {
			hashCode = 17 * hashCode + publicationName.hashCode();
		}
		if (publicationYear != null) {
			hashCode = 17 * hashCode + publicationYear.hashCode();
		}
		if (workType != null) {
			hashCode = 17 * hashCode + workType.hashCode();
		}
		if (issn != null) {
			hashCode = 17 * hashCode + issn.hashCode();
		}
		if (isbn != null) {
			hashCode = 17 * hashCode + isbn.hashCode();
		}
		return hashCode;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Publication)) {
			return false;
		}
		Publication other = (Publication) obj;
		boolean isEqual = true;
		// For some reason comparing the lists doesn't appear to return a true when they should.  The containsAll method also has the same issue...
		isEqual = this.getAuthors().size() == other.getAuthors().size();
		Author author = null;
		for (int i = 0; isEqual && i < other.getAuthors().size(); i++) {
			author = other.getAuthors().get(i);
			isEqual = this.getAuthors().contains(author);
		}
		
		return (
				isEqual &&
				((this.getAriesId() == other.getAriesId()) || (this.getAriesId() != null && this.getAriesId().equals(other.getAriesId()))) &&
				((this.getTitle() == other.getTitle()) || (this.getTitle() != null && this.getTitle().equals(other.getTitle()))) &&
				((this.getPublicationName() == other.getTitle()) || (this.getTitle() != null && this.getTitle().equals(other.getTitle()))) &&
				((this.getPublicationYear() == other.getPublicationYear()) || (this.getPublicationYear() != null && this.getPublicationYear().equals(other.getPublicationYear()))) &&
				((this.getWorkType() == other.getWorkType()) || (this.getWorkType() != null && this.getWorkType().equals(other.getWorkType()))) &&
				((this.getIssn() == other.getIssn()) || (this.getIssn() != null && this.getIssn().equals(other.getIssn()))) &&
				((this.getIsbn() == other.getIsbn()) || (this.getIsbn() != null && this.getIsbn().equals(other.getIsbn())))
				);
	}
	
	public void updateDetails(Publication publication, List<Author> authorsToRemove) {
		this.ariesId = publication.getAriesId();
		this.publicationName = publication.getPublicationName();
		this.publicationYear =publication.getPublicationYear();
		this.workType = publication.getWorkType();
		this.title = publication.getTitle();
		this.isbn = publication.getIsbn();
		this.issn = publication.getIssn();
		//do special stuff with authors
		//publication.getAuthors();
		List<Author> publicationAuthors = publication.getAuthors();
		List<Author> thisAuthors = this.getAuthors();
		
		authorsToRemove.addAll(thisAuthors);
		authorsToRemove.removeAll(publicationAuthors);
		//authorsToRemove.retainAll(publication.getAuthors());
	}
}
