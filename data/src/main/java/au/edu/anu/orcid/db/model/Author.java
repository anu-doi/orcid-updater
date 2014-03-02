package au.edu.anu.orcid.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="publication_author")
public class Author {
	static final Logger LOGGER = LoggerFactory.getLogger(Author.class);
	
	private Long id;
	private String extId;
	private Publication publication;
	private String name;
	
	public Author() {
		
	}
	
	public Author(String name) {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="external_id")
	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publication_id", referencedColumnName="id", nullable = false)
	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	@Column(name="author_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		if (extId != null) {
			hashCode = 17 * hashCode + extId.hashCode();
		}
		if (publication != null) {
			hashCode = 17 * hashCode + publication.hashCode();
		}
		if (name != null) {
			hashCode = 17 * hashCode + name.hashCode();
		}
		//LOGGER.info("hash Code: {}", hashCode);
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Author)) {
			return false;
		}
		Author other = (Author) obj;
		return (
				((this.getExtId() == other.getExtId()) || (this.getExtId() != null && this.getExtId().equals(other.getExtId()))) &&
				//TODO potentially fix this up, would prefer to directly reference the publication however this causes an infinite loop and stackoverflow exception
				((this.getPublication() == other.getPublication()) 
						|| (this.getPublication() != null && this.getPublication().getAriesId() != null
						&& other.getPublication() != null && other.getPublication().getAriesId() != null
						&& this.getPublication().getAriesId().equals(other.getPublication().getAriesId()))) &&
				((this.getName() == other.getName()) || (this.getName() != null && this.getName().equals(other.getName())))
				);
	}
}
