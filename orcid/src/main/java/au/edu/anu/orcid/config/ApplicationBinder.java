package au.edu.anu.orcid.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import au.edu.anu.orcid.db.dao.PersonDAO;
import au.edu.anu.orcid.db.dao.PublicationDAO;
import au.edu.anu.orcid.db.dao.impl.PersonDAOImpl;
import au.edu.anu.orcid.db.dao.impl.PublicationDAOImpl;
import au.edu.anu.orcid.metadatastores.retrieve.ObtainMSPersonInformation;
import au.edu.anu.orcid.metadatastores.retrieve.ObtainMSPublicationInformation;
import au.edu.anu.orcid.process.retrieve.IdObtainer;
import au.edu.anu.orcid.process.retrieve.ObtainPersonInformation;
import au.edu.anu.orcid.process.retrieve.ObtainPublicationInformation;
import au.edu.anu.orcid.process.retrieve.UidObtainer;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(IdObtainer.class).to(IdObtainer.class);
		bind(UidObtainer.class).to(UidObtainer.class);
		//bind(PersonDAOImpl.class).to(PersonDAO.class);
		bind(PersonDAOImpl.class).to(PersonDAO.class);
		bind(ObtainMSPersonInformation.class).to(ObtainPersonInformation.class);
		bind(ObtainMSPublicationInformation.class).to(ObtainPublicationInformation.class);
		bind(PersonDAOImpl.class).to(PersonDAO.class);
		bind(PublicationDAOImpl.class).to(PublicationDAO.class);
		//bind(ObtainPersonInformation.class).to(ObtainInformation<Person, OrcidBio>);//.to(ObtainInformation<Person, OrcidBio>.class);
		//bind(ObtainPersonInformation.class).;
		//bind(ObtainPersonInformation.class).to
	}

}
