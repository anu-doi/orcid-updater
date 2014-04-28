package au.edu.anu.orcid.security.permission;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.model.Person;

/**
 * <p>PermissionServiceImpl</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Implementation class for the permission service</p>
 *
 * @author Genevieve Turner
 *
 */
public class PermissionServiceImpl implements PermissionService {
	static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Inject
	PersonDAO personDAO;

	@Override
	public void checkPerson(Person person) {
		LOGGER.debug("Checked Whether the person had permissions");
	}

}
