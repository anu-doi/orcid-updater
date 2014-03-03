/*******************************************************************************
 * Australian National University Orcid Updater
 * Copyright (C) 2013  The Australian National University
 * 
 * This file is part of Australian National University Orcid Updater.
 * 
 * Australian National University Orcid Updater is free software: you
 * can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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

/**
 * <p>ApplicationBinder</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Binds classes for injection</p>
 *
 * @author Genevieve Turner
 *
 */
public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(IdObtainer.class).to(IdObtainer.class);
		bind(UidObtainer.class).to(UidObtainer.class);
		bind(PersonDAOImpl.class).to(PersonDAO.class);
		bind(ObtainMSPersonInformation.class).to(ObtainPersonInformation.class);
		bind(ObtainMSPublicationInformation.class).to(ObtainPublicationInformation.class);
		bind(PersonDAOImpl.class).to(PersonDAO.class);
		bind(PublicationDAOImpl.class).to(PublicationDAO.class);
	}

}
