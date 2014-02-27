package au.edu.anu.orcid.retrieve;

import org.orcid.ns.orcid.OrcidMessage;

import au.edu.anu.orcid.db.model.Person;

public interface Obtainer<T> {
	public OrcidMessage getFullOrcidProfile(T id);
	public OrcidMessage getOrcidWorks(T id);
	public Person getPerson(T id);
	public Person fetchPerson(T id);
	public void updatePerson(Person person);
}
