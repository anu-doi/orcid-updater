
package au.edu.anu.orcid.db.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T, PK extends Serializable>{
	T create(T t);
	
	T getSingleById(PK id);

	List<T> getAll();

	T update(T t);

	void delete(PK id);
}
