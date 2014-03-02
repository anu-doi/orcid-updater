package au.edu.anu.orcid.process.retrieve;

public interface ObtainInformation<T, U> {
	/**
	 * 
	 * @param id The id of the person the information is about
	 * @return The associated object
	 */
	public T get(Long id);
	
	public T get(String uid);
	
	public T fetch(String uid);
	
	public void save(T t);
	
	public U getOrcidObject(Long id);
	
	public U getOrcidObject(String uid);
}
