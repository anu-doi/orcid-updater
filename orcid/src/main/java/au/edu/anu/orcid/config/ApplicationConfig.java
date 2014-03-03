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

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import au.edu.anu.orcid.display.RecordIdResource;
import au.edu.anu.orcid.display.RecordUidResource;

/**
 * <p>ApplicationConfig</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Configures information for jersey start up</p>
 *
 * @author Genevieve Turner
 *
 */
@ApplicationPath("/rest")
public class ApplicationConfig extends ResourceConfig {
	public ApplicationConfig() {
		//  Resources
		packages(RecordIdResource.class.getPackage().getName(), RecordUidResource.class.getPackage().getName());
		
		// MVC
		register(JspMvcFeature.class);
		register(new ApplicationBinder());
		// Logging
		//register(LoggingFilter.class);
	}
}
