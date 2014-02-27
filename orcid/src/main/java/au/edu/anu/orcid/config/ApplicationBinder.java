package au.edu.anu.orcid.config;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.orcid.ns.orcid.OrcidBio;

import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.dao.impl.PersonDAOImpl;
import au.edu.anu.orcid.db.model.Person;
import au.edu.anu.orcid.metadatastores.ObtainerStuff;
import au.edu.anu.orcid.retrieve.IdObtainer;
import au.edu.anu.orcid.retrieve.ObtainInformation;
import au.edu.anu.orcid.retrieve.ObtainPersonInformation;
import au.edu.anu.orcid.retrieve.UidObtainer;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ObtainerStuff.class).to(ObtainerStuff.class);
		bind(IdObtainer.class).to(IdObtainer.class);
		bind(UidObtainer.class).to(UidObtainer.class);
		//bind(PersonDAOImpl.class).to(PersonDAO.class);
		bind(PersonDAOImpl.class).to(PersonDAO.class);
		//bind(ObtainPersonInformation.class).to(ObtainInformation<Person, OrcidBio>);//.to(ObtainInformation<Person, OrcidBio>.class);
		//bind(ObtainPersonInformation.class).;
		//bind(ObtainPersonInformation.class).to
	}

}
