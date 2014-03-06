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
package au.edu.anu.orcid.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * <p>OrcidAuthenticationSuccessHandler</p>
 *
 * <p>The Australian National University</p>
 *
 * <p>Success Handler that redirects to import the logged in users profile.</p>
 *
 * @author Genevieve Turner
 *
 */
public class OrcidAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	static final Logger LOGGER = LoggerFactory.getLogger(OrcidAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOGGER.info("Authentication Name: {}", authentication.getName());
		
		if (authentication.getName().startsWith("u")) {
			String newPath = request.getContextPath() + "/rest/uid/" + authentication.getName() + "/import";
			LOGGER.info("New Path: {}", newPath);
			response.sendRedirect(newPath);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
