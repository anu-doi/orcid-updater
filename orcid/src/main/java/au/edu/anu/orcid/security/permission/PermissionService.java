package au.edu.anu.orcid.security.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import au.edu.anu.orcid.db.model.Person;

/**
 * <p>PermissionService</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Checks if the user has permission to view the object</p>
 *
 * @author Genevieve Turner
 *
 */
public interface PermissionService {
	/**
	 * Check if the user has permission to view the object
	 * 
	 * @param person The person to check
	 */
	@PreAuthorize("#person.uid == authentication.name")
	public void checkPerson(Person person);
}
