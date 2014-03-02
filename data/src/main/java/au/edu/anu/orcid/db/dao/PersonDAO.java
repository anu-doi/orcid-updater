package au.edu.anu.orcid.db.dao;

import au.edu.anu.orcid.db.model.Person;

public interface PersonDAO extends GenericDAO<Person, Long>, ExtIdDAO<Person> {
	public Person getPersonByUniId(String uniId);
	
	public Person getPersonByUniIdWithOrcidPublications(String uniId);
}
