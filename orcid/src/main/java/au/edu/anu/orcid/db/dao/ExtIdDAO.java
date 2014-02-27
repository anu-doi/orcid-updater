package au.edu.anu.orcid.db.dao;


public interface ExtIdDAO<T> {
	public T getByExternalIdentifier(String identifier);
}
