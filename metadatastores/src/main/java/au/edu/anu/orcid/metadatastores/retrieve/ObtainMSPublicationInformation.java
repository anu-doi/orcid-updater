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
package au.edu.anu.orcid.metadatastores.retrieve;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.orcid.db.model.Publication;
import au.edu.anu.orcid.metadatastores.publication.MSPublication;
import au.edu.anu.orcid.process.retrieve.ObtainPublicationInformation;
import au.edu.anu.orcid.util.PropertyLoader;

/**
 * <p>ObtainMSPublicationInformation</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Obtain information about publications from Metadata Stores</p>
 *
 * @author Genevieve Turner
 *
 */
public class ObtainMSPublicationInformation extends
		ObtainPublicationInformation {
	static final Logger LOGGER = LoggerFactory.getLogger(ObtainPublicationInformation.class);
	private static final Properties metadataStoresProperties = PropertyLoader.loadProperties("metadatastores.properties");

	@Override
	public List<Publication> fetch(String uid) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(metadataStoresProperties.getProperty("app.uri")).path(metadataStoresProperties.getProperty("extension.publication")).path(uid);
		//WebTarget target = client.target("http://localhost:8180/services/rest/person/publications").path(uid);
		LOGGER.info("Target URL: {}", target.getUri().toString());
		
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		List<MSPublication> msPublications = response.readEntity(new GenericType<List<MSPublication>>(){});
		List<Publication> publications = new ArrayList<Publication>();
		for (MSPublication msPub : msPublications) {
			publications.add(msPub.getPublication());
		}
		save(publications);
		return publications;
	}
}
