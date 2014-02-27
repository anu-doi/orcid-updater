package au.edu.anu.orcid.db.dao;

import java.util.List;

import au.edu.anu.orcid.db.model.Publication;

public interface PublicationDAO extends GenericDAO<Publication, Long>, ExtIdDAO<Publication> {
	public List<Publication> getPublicationsWithPersonExternalId(String externalIdentifier);
}
